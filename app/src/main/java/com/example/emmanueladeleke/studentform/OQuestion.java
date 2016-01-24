package com.example.emmanueladeleke.studentform;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OQuestion extends Fragment {

    TextView tvOpenQuestion;
    TextView tvTitle;
    Typeface titleFont;

    public OQuestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_oquestion, container, false);

        tvOpenQuestion = (TextView) view.findViewById(R.id.tvOpenQuestion);
        tvOpenQuestion.setText(MainActivity.jsonToObjectList().get(MainActivity.position).question);

        tvTitle = (TextView) view.findViewById(R.id.tvOpenQuestion);
        titleFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/titleFont.ttf");
        tvTitle.setTypeface(titleFont);

        Button button = (Button) view.findViewById(R.id.bSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDialog.showMessageToUser(getActivity(), "Message sent");
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
