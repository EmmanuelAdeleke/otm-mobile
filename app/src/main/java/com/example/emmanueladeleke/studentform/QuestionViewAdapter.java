package com.example.emmanueladeleke.studentform;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QuestionViewAdapter extends RecyclerView.Adapter<QuestionViewAdapter.QuestionViewHolder> {

    List<QuestionRefactor> questionList;
    private ClickListener clickListener;

    public QuestionViewAdapter(List<QuestionRefactor> questionList) {
        this.questionList = questionList;
    }


    public static class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ClickListener clickListener;
        CardView cv;
        TextView firstName;
        TextView lastName;
        TextView topic;
        TextView question;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            firstName = (TextView) itemView.findViewById(R.id.tvFirstName);
            lastName = (TextView) itemView.findViewById(R.id.tvLastName);
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
    public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(view);
        return questionViewHolder;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.firstName.setText(questionList.get(position).firstName);
        holder.lastName.setText(questionList.get(position).lastName);
        holder.topic.setText(questionList.get(position).topic);
        holder.question.setText(questionList.get(position).question);
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
