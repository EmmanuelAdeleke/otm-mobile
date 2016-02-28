package com.example.emmanueladeleke.studentform;


import android.annotation.TargetApi;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.emmanueladeleke.studentform.tabs.MultipleFragment;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * A simple {@link Fragment} subclass.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MQuestion extends Fragment implements View.OnClickListener {

    TextView tvOpenQuestion;
    TextView tvTitle;
    Typeface titleFont;
    Button bSubmitA;
    Button bSubmitB;
    Button bSubmitC;
    Button bSubmitD;
    int questionIndex;
    int closedQuestionIndex;
    int closedQuestionLimit;
    int correctCount = 0;
    String selected = "";
    int i = 0;

    public MQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mquestion, container, false);


        questionIndex = MultipleFragment.position;
        closedQuestionLimit = MultipleFragment.multipleList.get(questionIndex).questionList.size();
        closedQuestionIndex = 0;

        tvOpenQuestion = (TextView) view.findViewById(R.id.tvOpenQuestion);
//        tvOpenQuestion.setText(MainActivity.jsonToObjectList().get(MainActivity.position).question);
        tvOpenQuestion.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).question);

        tvTitle = (TextView) view.findViewById(R.id.tvOpenQuestion);
        titleFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/titleFont5.ttf");
        tvTitle.setTypeface(titleFont);

        bSubmitA = (Button) view.findViewById(R.id.bSubmitA);
        bSubmitB = (Button) view.findViewById(R.id.bSubmitB);
        bSubmitC = (Button) view.findViewById(R.id.bSubmitC);
        bSubmitD = (Button) view.findViewById(R.id.bSubmitD);

        bSubmitA.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[0]);
        bSubmitB.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[1]);
        bSubmitC.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[2]);
        bSubmitD.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[3]);

        bSubmitA.setOnClickListener(this);
        bSubmitB.setOnClickListener(this);
        bSubmitC.setOnClickListener(this);
        bSubmitD.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSubmitA:
                isCorrect(bSubmitA.getText().toString(), MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).correctAnswer);
                selected = "A";
                break;
            case R.id.bSubmitB:
                isCorrect(bSubmitB.getText().toString(), MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).correctAnswer);
                selected = "B";
                break;
            case R.id.bSubmitC:
                isCorrect(bSubmitC.getText().toString(), MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).correctAnswer);
                selected = "C";
                break;
            case R.id.bSubmitD:
                isCorrect(bSubmitD.getText().toString(), MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).correctAnswer);
                selected = "D";
                break;
        }
        IncrementValueTask incrementValueTask = new IncrementValueTask();
        incrementValueTask.execute();

    }

    public void nextQuestion() {
        if (closedQuestionIndex != closedQuestionLimit - 1) {
            closedQuestionIndex++;

            tvOpenQuestion.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).question);
            bSubmitA.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[0]);
            bSubmitB.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[1]);
            bSubmitC.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[2]);
            bSubmitD.setText(MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).ansOption[3]);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.multipleContainer, new MultipleFragment.MultipleRecyclerViewFragment())
                    .commit();
            UserDialog.showMessageToUser(getContext(), "Your score is... " + correctCount + "/" + MultipleFragment.multipleList.get(questionIndex).questionList.size());
            correctCount = 0;
        }
    }

    public void isCorrect(String correctAns, String userAns) {

        if (correctAns.equals(userAns)) {
            correctCount++;
            UserDialog.showMessageToUser(getContext(), "Correct!");
        } else {
            UserDialog.showMessageToUser(getContext(), "...The correct answer is: " + MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).correctAnswer);
        }

    }

    private class IncrementValueTask extends AsyncTask<Void, Void, Void> {

        String SERVER_ADDRESS = "emmanueladeleke.ddns.net";
        String DATABASE = "OtMC";
        String COLLECTION = "closedquestion";

        MongoClient client;
        MongoDatabase database;
        MongoCollection<Document> collection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new MongoClient(SERVER_ADDRESS);
            database = client.getDatabase(DATABASE);
            collection = database.getCollection(COLLECTION);

        }

        @Override
        protected Void doInBackground(Void... params) {

            Document find = new Document("_id", new ObjectId(MultipleFragment.multipleList.get(questionIndex)._id))
                    .append("questionList.question", MultipleFragment.multipleList.get(questionIndex).questionList.get(closedQuestionIndex).question);

            Document listItem = new Document("questionList.$." + selected, 1);

            Document updateQuery = new Document("$inc", listItem);

            collection.updateOne(find, updateQuery);

            Log.e("i", i + "");
            i++;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            nextQuestion();
        }
    }
}
