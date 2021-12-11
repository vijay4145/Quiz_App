package com.example.quiz.create_quiz;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.QuizTemplate;
import com.example.quiz.R;

import java.util.ArrayList;


public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.ViewHolder>{
    private Context context;
    private ArrayList<QuizTemplate> quizTemplate;

    public QuizRecyclerAdapter(Context context, ArrayList<QuizTemplate> quizTemplate) {
        this.context = context;
        this.quizTemplate = quizTemplate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_template_text_view,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.quizTemplateTextView[7].setText(String.valueOf(position+1));
        holder.quizTemplateTextView[0].setText(quizTemplate.get(position).getQuestion());
        holder.quizTemplateTextView[1].setText("1)"+ quizTemplate.get(position).getOptions()[0]);
        holder.quizTemplateTextView[2].setText("2)" + quizTemplate.get(position).getOptions()[1]);
        holder.quizTemplateTextView[3].setText("3)" + quizTemplate.get(position).getOptions()[2]);
        holder.quizTemplateTextView[4].setText("4)" + quizTemplate.get(position).getOptions()[3]);
        holder.quizTemplateTextView[5].setText("Correct Option: " + quizTemplate.get(position).getRightOption());
        if(quizTemplate.get(position).getReasonForAnswer() == null){
            holder.quizTemplateTextView[6].setVisibility(View.GONE);
        }else {
            holder.quizTemplateTextView[6].setText("Reason: " + quizTemplate.get(position).getReasonForAnswer());
        }
    }


    @Override
    public int getItemCount() {
        return quizTemplate.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        //0.question, 1.option1, 2.option2, 3.option3, 4.option4, 5.right_option, 6.support_for_answer
        TextView[] quizTemplateTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quizTemplateTextView = new TextView[8];
            quizTemplateTextView[0] = itemView.findViewById(R.id.question);
            quizTemplateTextView[1] = itemView.findViewById(R.id.option1);
            quizTemplateTextView[2] = itemView.findViewById(R.id.option2);
            quizTemplateTextView[3] = itemView.findViewById(R.id.option3);
            quizTemplateTextView[4] = itemView.findViewById(R.id.option4);
            quizTemplateTextView[5] = itemView.findViewById(R.id.right_option);
            quizTemplateTextView[6] = itemView.findViewById(R.id.support_for_answer);
            quizTemplateTextView[7] = itemView.findViewById(R.id.question_number);
        }
    }
}
