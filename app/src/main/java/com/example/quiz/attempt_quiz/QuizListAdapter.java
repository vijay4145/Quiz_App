package com.example.quiz.attempt_quiz;

import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.QuizTemplate;
import com.example.quiz.R;

import java.util.ArrayList;
import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.ViewHolder>{


    public static List<List<QuizTemplate>> quizTemplateList;
    List<Pair<String,String>> authorName; //authorName TopicName

    public QuizListAdapter(List<List<QuizTemplate>> quizTemplateList, List<Pair<String, String>> authorName) {
        QuizListAdapter.quizTemplateList = quizTemplateList;
        this.authorName = authorName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.topicName.setText(authorName.get(position).first);
        holder.authorName.setText("created by: "+authorName.get(position).second);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),question_list.class);
                intent.putExtra("POSITION", String.valueOf(holder.getAdapterPosition()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return authorName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView topicName;
        TextView authorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topic_name_in_quiz_list);
            authorName = itemView.findViewById(R.id.author_name_in_card_view);
        }

    }
}
