package com.example.emmanueladeleke.studentform;


import android.annotation.TargetApi;

import android.graphics.Typeface;
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

    public MQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oquestion, container, false);


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
                Log.e("asd", "asdasdasd");
                break;
        }
        Log.e("asd", "asdasdasd");
        nextQuestion();
    }

    public void nextQuestion() {
        if (closedQuestionIndex < closedQuestionLimit - 1) {
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

            UserDialog.showMessageToUser(getActivity(), "Answers sent");
        }
    }
}
