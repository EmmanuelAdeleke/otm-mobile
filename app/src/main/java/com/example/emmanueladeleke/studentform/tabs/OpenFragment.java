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
import com.example.emmanueladeleke.studentform.adapter.OpenQuestionViewAdapter;
import com.example.emmanueladeleke.studentform.question.QuestionRefactor;
import com.example.emmanueladeleke.studentform.R;
import com.example.emmanueladeleke.studentform.entity.Lecturer;
import com.example.emmanueladeleke.studentform.question.ClosedQuestion;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import layout.OQuestion;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends Fragment {

    public static List<QuestionRefactor> questionList;
    public OpenRecyclerViewFragment fragment;

    public OpenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_open, container, false);
        QuestionTask questionTask = new QuestionTask();
        questionTask.execute();
        parseJson();

        fragment = new OpenRecyclerViewFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.openContainer, fragment)
                    .commit();
        }

        return rootView;
    }

    public void parseJson() {

        List<ClosedQuestion> multipleList;

        String strJson = "";
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/open.json");
        try {
            strJson = FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();

        Lecturer[] lecturer = gson.fromJson(strJson, Lecturer[].class);

//        Log.e("nanana", lecturer[0].toString());
//
        questionList = new ArrayList<>();
//
//        Log.e("seethishere", lecturer.toString());
//

//
        for (int i = 0; i < lecturer.length; i++) {
            for (int j = 0; j < lecturer[i].questions.size(); j++) {
                questionList.add(new QuestionRefactor(lecturer[i]._id,
                        lecturer[i].firstName, lecturer[i].lastName, lecturer[i].questions.get(j)._id,
                        lecturer[i].questions.get(j).topic, lecturer[i].questions.get(j).question));
            }
        }
//
        Log.d("questionList2", questionList.toString());
        Log.d("lecturerLength", lecturer.length + "");
//
//        for (int i = 0; i < multiple.length; i++) {
//            Log.e("wowow", multiple[i].toString());
//        }
//
//        multipleList = Arrays.asList(multiple);

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
                url = new URL("http://emmanueladeleke.ddns.net:3000/OtMC/lecturer");
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
                FileUtils.deleteQuietly(new File(Environment.getExternalStorageDirectory().toString() + "/open.json"));
                FileUtils.writeStringToFile(new File(Environment.getExternalStorageDirectory().toString() + "/open.json"), strJson, false);
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

    public static class OpenRecyclerViewFragment extends Fragment {

        public RecyclerView rv;


        public OpenRecyclerViewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.open_question_fragment, container, false);

            rv = (RecyclerView) rootView.findViewById(R.id.feedRecyclerView);
            rv.setHasFixedSize(true);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(linearLayoutManager);

            OpenQuestionViewAdapter adapter = new OpenQuestionViewAdapter(questionList);

            ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    OQuestion oQuestion = new OQuestion();
                    replaceFragment();
                }
            });

            rv.setAdapter(adapter);
            rv.setItemAnimator(new DefaultItemAnimator());


            return rootView;
        }

        public void replaceFragment() {
            getFragmentManager().beginTransaction()
                    .replace(R.id.openContainer, new OQuestion())
                    .commit();
        }

//        public int getPosition() {
//            return position;
//        }


    }
}
