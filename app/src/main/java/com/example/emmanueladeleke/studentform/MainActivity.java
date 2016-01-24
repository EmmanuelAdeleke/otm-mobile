package com.example.emmanueladeleke.studentform;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvName;
    TextView tvEmail;
    User user;
    //    RecyclerView rv;
    public static int position;
    PlaceholderFragment fragment;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionTask questionTask = new QuestionTask();
        questionTask.execute();

        // Call init user method
        initUser();

        // Bind toolbar and set as ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bind the textViews and set to user name and email
        tvName = (TextView) findViewById(R.id.tvFullName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvEmail.setText(user.getEmailAddress());

        jsonToObjectList();
        // TODO - Display in RecyclerView
//        rv = (RecyclerView) findViewById(R.id.rv);
//        rv.setHasFixedSize(true);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rv.setLayoutManager(linearLayoutManager);
//
//        QuestionViewAdapter adapter = new QuestionViewAdapter(jsonToObjectList());
//        rv.setAdapter(adapter);
//        rv.setItemAnimator(new DefaultItemAnimator());

        fragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        Log.e("wada", jsonToObjectList().toString());


        // TODO - Add touch listener to RecyclerView
//        ItemClickSupport.addTo(fragment.rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                // do it
//                Log.d("position_r", position + "");
//                Log.d("position_x", jsonToObjectList().get(position).toString());
//            }
//        });
    }

    public void initUser() {

        String strJson = "";
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/user.json");
        try {
            strJson = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(strJson);
            jsonObject = new JSONObject(jsonArray.get(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonObject", jsonObject.toString());

        Gson gson = new Gson();

        user = gson.fromJson(jsonObject.toString(), User.class);
        Log.d("user", user.toString());
    }

    public static List<QuestionRefactor> jsonToObjectList() {
        String strJson = "";
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json");
        try {
            strJson = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(strJson);
            jsonObject = new JSONObject(jsonArray.get(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonObject2", jsonObject.toString());
        Log.d("strJsonFile", strJson);

        Gson gson = new Gson();

        Lecturer[] lecturer = gson.fromJson(strJson, Lecturer[].class);

        List<QuestionRefactor> questionList = new ArrayList<>();

        for (int i = 0; i < lecturer.length; i++) {
            for (int j = 0; j < lecturer[i].questions.size(); j++) {
                questionList.add(new QuestionRefactor(lecturer[i]._id,
                        lecturer[i].firstName, lecturer[i].lastName, lecturer[i].questions.get(j)._id,
                        lecturer[i].questions.get(j).topic, lecturer[i].questions.get(j).question));
            }
        }

        Log.d("questionListCheck", questionList.toString() + "\n");
        return questionList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            UserDialog.showMessageToUser(this, "asdasd");
        }
        return false;
    }

    private class QuestionTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading questions");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Assign url and in
            String strJson;
            URL url;
            BufferedReader in = null;
            try {
                // Get query (username & password)
                url = new URL("http://emmanueladeleke.ddns.net:3000/otm/lecturer");
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Get input from in and store in builder
            String inputLine;
            StringBuilder builder = new StringBuilder();
            try {
                while ((inputLine = in.readLine()) != null)
                    builder.append(inputLine);
            } catch (IOException e) {
                Log.d("IOException", "ioexception");
                e.printStackTrace();
            } catch (NullPointerException e) {
                Log.d("NullPointer", "Here is a nullpointer exception");

            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Remove all white spaces in strJson
            strJson = builder.toString().replaceAll("\\s+", " ");

            // Check if JSON file is empty
            if (strJson.equals("[]")) {
                Log.d("AuthFail", strJson);
            } else {
                Log.d("AuthSuccess", strJson);
            }

            try {
                // Does not run on Marshmallow
                FileUtils.deleteQuietly(new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json"));
                FileUtils.writeStringToFile(new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json"), strJson, false);
                Log.d("success", strJson + "right here");

                // file.close();
            } catch (IOException e) {
                System.out.println("Cannot create file");
                Log.d("fail", "fail");
                Log.d("FilePath?", Environment.getExternalStorageDirectory().toString());
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
//
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PlaceholderFragment extends Fragment {

        public RecyclerView rv;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.open_question_fragment, container, false);

            rv = (RecyclerView) rootView.findViewById(R.id.feedRecyclerView);
            rv.setHasFixedSize(true);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(linearLayoutManager);

            QuestionViewAdapter adapter = new QuestionViewAdapter(jsonToObjectList());

            ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    OQuestion question = new OQuestion();
                    Log.d("position_r", position + "");
                    Log.d("position_x", jsonToObjectList().get(position).toString());
                    replaceFragment();
                    MainActivity.position = position;
                }
            });

            rv.setAdapter(adapter);
            rv.setItemAnimator(new DefaultItemAnimator());


            return rootView;
        }

        public void replaceFragment() {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new OQuestion())
                    .commit();
        }

        public int getPosition() {
            return position;
        }


//        public void recyclerPosition() {
//            ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                // do it
//                Log.d("position_r", position + "");
//                Log.d("position_x", jsonToObjectList().get(position).toString());
//            }
//        });
//        }


    }
}
