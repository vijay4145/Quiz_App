package com.example.quiz.attempt_quiz;

import android.content.Context;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.QuizTemplate;
import com.example.quiz.R;

import java.util.ArrayList;
import java.util.List;


public class Attemp_question_list_adapter extends RecyclerView.Adapter<Attemp_question_list_adapter.ViewHolder>{
    private Context context;
    private List<QuizTemplate> quizTemplate;

    public Attemp_question_list_adapter(Context context, List<QuizTemplate> quizTemplate) {
        this.context = context;
        this.quizTemplate = quizTemplate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_question_list_card_view,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.quizTemplateTextView[7].setText(String.valueOf(position+1));
        holder.quizTemplateTextView[0].setText(quizTemplate.get(position).getQuestion());
        holder.quizTemplateTextView[1].setText("1)"+ quizTemplate.get(position).getOptions().get(0));
        holder.quizTemplateTextView[2].setText("2)" + quizTemplate.get(position).getOptions().get(1));
        holder.quizTemplateTextView[3].setText("3)" + quizTemplate.get(position).getOptions().get(2));
        holder.quizTemplateTextView[4].setText("4)" + quizTemplate.get(position).getOptions().get(3));
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int index = radioGroup.indexOfChild(holder.itemView.findViewById(radioGroup.getCheckedRadioButtonId())) + 1;
                Toast.makeText(context.getApplicationContext(), "selected option is "+ index, Toast.LENGTH_SHORT).show();
                question_list.answersTickedByUser[holder.getAdapterPosition()] = String.valueOf(index);
            }
        });

    }


    @Override
    public int getItemCount() {
        return quizTemplate.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        //0.question, 1.option1, 2.option2, 3.option3, 4.option4, 5.right_option, 6.support_for_answer
        TextView[] quizTemplateTextView;
        RadioGroup radioGroup;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quizTemplateTextView = new TextView[8];
            quizTemplateTextView[0] = itemView.findViewById(R.id.question);
            quizTemplateTextView[1] = itemView.findViewById(R.id.option1);
            quizTemplateTextView[2] = itemView.findViewById(R.id.option2);
            quizTemplateTextView[3] = itemView.findViewById(R.id.option3);
            quizTemplateTextView[4] = itemView.findViewById(R.id.option4);
//            quizTemplateTextView[5] = itemView.findViewById(R.id.right_option);
            quizTemplateTextView[6] = itemView.findViewById(R.id.support_for_answer);
            quizTemplateTextView[7] = itemView.findViewById(R.id.question_number);

            radioGroup = itemView.findViewById(R.id.radio_group);
        }
    }
}
