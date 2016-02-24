package com.example.emmanueladeleke.studentform.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emmanueladeleke.studentform.R;
import com.example.emmanueladeleke.studentform.question.QuestionRefactor;

import java.util.List;

public class OpenQuestionViewAdapter extends RecyclerView.Adapter<OpenQuestionViewAdapter.OpenQuestionViewHolder> {

    List<QuestionRefactor> questionList;
    private ClickListener clickListener;

    public OpenQuestionViewAdapter(List<QuestionRefactor> questionList) {
        this.questionList = questionList;
    }


    public static class OpenQuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ClickListener clickListener;
        CardView cv;
        TextView fullName;
        TextView topic;
        TextView question;

        public OpenQuestionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            fullName = (TextView) itemView.findViewById(R.id.tvFullName);
            topic = (TextView) itemView.findViewById(R.id.tvTopic);
            question = (TextView) itemView.findViewById(R.id.tvQuestion);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }


    @Override
    public OpenQuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ocardview, viewGroup, false);
        OpenQuestionViewHolder questionViewHolder = new OpenQuestionViewHolder(view);
        return questionViewHolder;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(OpenQuestionViewHolder holder, final int position) {
        holder.fullName.setText("Set by: " + questionList.get(position).firstName + " " + questionList.get(position).lastName);
        holder.topic.setText(questionList.get(position).topic);
        holder.question.setText(questionList.get(position).questions);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }

}
