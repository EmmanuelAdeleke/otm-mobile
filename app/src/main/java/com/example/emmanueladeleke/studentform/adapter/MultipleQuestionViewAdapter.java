package com.example.emmanueladeleke.studentform.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emmanueladeleke.studentform.R;
import com.example.emmanueladeleke.studentform.question.ClosedQuestion;

import java.util.List;

public class MultipleQuestionViewAdapter extends RecyclerView.Adapter<MultipleQuestionViewAdapter.MultipleViewHolder> {

    List<ClosedQuestion> questionList;
    private ClickListener clickListener;

    public MultipleQuestionViewAdapter(List<ClosedQuestion> questionList) {
        this.questionList = questionList;
    }


    public static class MultipleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ClickListener clickListener;
        CardView cv;
        TextView topic;
        TextView quantity;

        public MultipleViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            topic = (TextView) itemView.findViewById(R.id.tvTopic);
            quantity = (TextView) itemView.findViewById(R.id.tvQuantity);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }


    @Override
    public MultipleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mcardview, viewGroup, false);
        MultipleViewHolder questionViewHolder = new MultipleViewHolder(view);
        return questionViewHolder;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder holder, final int position) {

        holder.topic.setText(questionList.get(position).topic);
        holder.quantity.setText("- " + questionList.get(position).questionList.size() + " questions to answer");
//        holder.question.setText(questionList.get(position).topic);
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
