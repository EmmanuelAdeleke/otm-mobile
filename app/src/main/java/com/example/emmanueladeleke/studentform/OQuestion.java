package com.example.emmanueladeleke.studentform;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emmanueladeleke.studentform.R;
import com.example.emmanueladeleke.studentform.tabs.MultipleFragment;
import com.example.emmanueladeleke.studentform.tabs.OpenFragment;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class OQuestion extends Fragment implements View.OnClickListener{

    Button bSubmitOpenQuestion;
    TextView tvOpenQuestion;
    EditText etAnswer;

    public OQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oquestion, container, false);

        tvOpenQuestion = (TextView) view.findViewById(R.id.tvOpenQuestion);
        etAnswer = (EditText) view.findViewById(R.id.etAnswer);
        tvOpenQuestion.setText(OpenFragment.questionList.get(OpenFragment.position).question);

        bSubmitOpenQuestion = (Button) view.findViewById(R.id.bSubmitOpenQuestion);
        bSubmitOpenQuestion.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bSubmitOpenQuestion:

                AddAnswerTask addAnswerTask = new AddAnswerTask();
                addAnswerTask.execute();

                getFragmentManager().beginTransaction()
                        .replace(R.id.openContainer, new OpenFragment.OpenRecyclerViewFragment())
                        .commit();

                UserDialog.showMessageToUser(getContext(), "Answer submitted");
                break;
        }
    }

    private class AddAnswerTask extends AsyncTask<Void, Void, Void> {

        String SERVER_ADDRESS = "emmanueladeleke.ddns.net";
        String DATABASE = "OtMC";
        String COLLECTION = "lecturer";

        MongoClient client;
        MongoDatabase database;
        MongoCollection<Document> collection;
        String answer;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new MongoClient(SERVER_ADDRESS);
            database = client.getDatabase(DATABASE);
            collection = database.getCollection(COLLECTION);

            answer = etAnswer.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Document find = new Document("_id", new ObjectId(OpenFragment.questionList.get(OpenFragment.position).lecturerId))
                    .append("questions.question", OpenFragment.questionList.get(OpenFragment.position).question);

            Document listItem = new Document("questions.$.answer", answer);

            Document updateQuery = new Document("$push", listItem);
            collection.updateOne(find, updateQuery);

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
}
