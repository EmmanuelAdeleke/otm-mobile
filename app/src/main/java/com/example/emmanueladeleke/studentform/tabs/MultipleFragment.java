package com.example.emmanueladeleke.studentform.tabs;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emmanueladeleke.studentform.ItemClickSupport;
import com.example.emmanueladeleke.studentform.MainActivity;
import com.example.emmanueladeleke.studentform.MQuestion;
import com.example.emmanueladeleke.studentform.R;
import com.example.emmanueladeleke.studentform.UserDialog;
import com.example.emmanueladeleke.studentform.adapter.MultipleQuestionViewAdapter;
import com.example.emmanueladeleke.studentform.question.ClosedQuestion;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleFragment extends Fragment {

    public RecyclerView rv;
    public static List<ClosedQuestion> multipleList;
    public MultipleRecyclerViewFragment fragment;
    public static int position;

    public MultipleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_multiple, container, false);
        // Inflate the layout for this fragment
        QuestionTask questionTask = new QuestionTask();
        questionTask.execute();

        fragment = new MultipleRecyclerViewFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.multipleContainer, fragment)
                    .commit();
        }
//        rv = (RecyclerView) rootView.findViewById(R.id.rv);
//        rv.setHasFixedSize(true);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        rv.setLayoutManager(linearLayoutManager);
//
//        QuestionViewAdapter adapter = new QuestionViewAdapter(multipleList);
//        rv.setAdapter(adapter);
//        rv.setItemAnimator(new DefaultItemAnimator());

        parseJson();
        return rootView;
    }

    public void parseJson() {

        String strJson = "";
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/multiple.json");
        try {
            strJson = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        ClosedQuestion[] multiple = gson.fromJson(strJson, ClosedQuestion[].class);

        for (int i = 0; i < multiple.length; i++) {
//            Log.e("wowow", multiple[i].toString());
        }

        multipleList = Arrays.asList(multiple);

//        for (int i = 0; i < lecturer.length; i++) {
//            for (int j = 0; j < lecturer[i].questions.size(); j++) {
//                questionList.add(new QuestionRefactor(lecturer[i].,
//                        lecturer[i].firstName, lecturer[i].lastName, lecturer[i].questions.get(j)._id,
//                        lecturer[i].questions.get(j).topic, lecturer[i].questions.get(j).question));
//            }
//        }
    }

    private class QuestionTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getContext());
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
//                url = new URL("http://emmanueladeleke.ddns.net:3000/OtMC/closedquestion?query={\"studentList\":{\"_id\":\""+ MainActivity.user.getId() + "\"}}");
                url = new URL("http://emmanueladeleke.ddns.net:3000/OtMC/closedquestion");
                Log.e("CHECKURL", url.toString());
                in = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                FileUtils.deleteQuietly(new File(Environment.getExternalStorageDirectory().toString() + "/multiple.json"));
                FileUtils.writeStringToFile(new File(Environment.getExternalStorageDirectory().toString() + "/multiple.json"), strJson, false);
                Log.e("showJsonContent", strJson + "right here");

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
//            UserDialog.showMessageToUser(getContext(), MainActivity.user.getId());
            dialog.dismiss();
        }
    }

    //    @TargetApi(Build.VERSION_CODES.HONEYCOMB§
    public static class MultipleRecyclerViewFragment extends Fragment {

        public RecyclerView rv;


        public MultipleRecyclerViewFragment() {

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.open_question_fragment, container, false);

            rv = (RecyclerView) rootView.findViewById(R.id.feedRecyclerView);
            rv.setHasFixedSize(true);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(linearLayoutManager);

            MultipleQuestionViewAdapter adapter = new MultipleQuestionViewAdapter(multipleList);

            ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                    OQuestion question = new OQuestion();
                    Log.d("position_r", position + "");
//                    Log.d("position_x", jsonToObjectList().get(position).toString());
                    replaceFragment();
                    MultipleFragment.position = position;
                }
            });

            rv.setAdapter(adapter);
            rv.setItemAnimator(new DefaultItemAnimator());


            return rootView;
        }

        public void replaceFragment() {
            getFragmentManager().beginTransaction()
                    .replace(R.id.multipleContainer, new MQuestion())
                    .commit();
        }
//
//        public int getPosition() {
//            return position;
//        }


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
