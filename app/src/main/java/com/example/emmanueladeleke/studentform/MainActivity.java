package com.example.emmanueladeleke.studentform;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.emmanueladeleke.studentform.entity.User;
import com.example.emmanueladeleke.studentform.tabs.MultipleFragment;
import com.example.emmanueladeleke.studentform.tabs.OpenFragment;
import com.example.emmanueladeleke.studentform.tabs.ViewPagerAdapter;
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


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvName;
    TextView tvEmail;
    public static User user;
    //    RecyclerView rv;
    public static int position;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        jsonToObjectList();
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


//        Log.e("wada", jsonToObjectList().toString());


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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MultipleFragment(), "Multiple Choice");
        adapter.addFragment(new OpenFragment(), "Written Answer");
        viewPager.setAdapter(adapter);
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

//    public static List<QuestionRefactor> jsonToObjectList() {
//        String strJson = "";
//        File file = new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json");
//        try {
//            strJson = FileUtils.readFileToString(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        JSONObject jsonObject = null;
//        JSONArray jsonArray = null;
//
//        try {
//            jsonArray = new JSONArray(strJson);
//            jsonObject = new JSONObject(jsonArray.get(0).toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Gson gson = new Gson();
//
//        Lecturer[] lecturer = gson.fromJson(strJson, Lecturer[].class);
//
//        List<QuestionRefactor> questionList = new ArrayList<>();
//
////        for (int i = 0; i < lecturer.length; i++) {
////            for (int j = 0; j < lecturer[i].questions.size(); j++) {
////                questionList.add(new QuestionRefactor(lecturer[i].,
////                        lecturer[i].firstName, lecturer[i].lastName, lecturer[i].questions.get(j)._id,
////                        lecturer[i].questions.get(j).topic, lecturer[i].questions.get(j).question));
////            }
////        }
//
//        Log.d("questionListCheck", questionList.toString() + "\n");
//        return questionList;
//    }

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
                url = new URL("http://emmanueladeleke.ddns.net:3000/OtMC/lecturer");
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


            try {
                // Does not run on Marshmallow
                FileUtils.deleteQuietly(new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json"));
                FileUtils.writeStringToFile(new File(Environment.getExternalStorageDirectory().toString() + "/lecturerQuestions.json"), strJson, false);


                // file.close();
            } catch (IOException e) {
                System.out.println("Cannot create file");

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

}


