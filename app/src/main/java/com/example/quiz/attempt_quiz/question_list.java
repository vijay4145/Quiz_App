package com.example.quiz.attempt_quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.QuizTemplate;
import com.example.quiz.R;

public class question_list extends AppCompatActivity {
    public static String []answersTickedByUser;
    public static String []correctAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        Intent intent = getIntent();
        int position = Integer.parseInt(intent.getStringExtra("POSITION"));
        question_list.answersTickedByUser = new String[QuizListAdapter.quizTemplateList.get(position).size()];
        question_list.correctAnswers = new String[QuizListAdapter.quizTemplateList.get(position).size()];
        RecyclerView question_list_recycler_view = findViewById(R.id.questions_in_question_list);


        question_list_recycler_view.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        Attemp_question_list_adapter attemp_question_list_adapter = new Attemp_question_list_adapter(this, QuizListAdapter.quizTemplateList.get(position));
        question_list_recycler_view.setAdapter(attemp_question_list_adapter);

        setCorrectAnswerList(position);
        Button submit_btn = findViewById(R.id.submit_btn_in_question_list);
        Dialog dialog = new Dialog(question_list.this);
        dialog.setContentView(R.layout.score_of_quiz_given);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button ok_btn_in_score_of_given_quiz_dialog = dialog.findViewById(R.id.ok_btn_in_score_of_quiz);
        setClickListener(ok_btn_in_score_of_given_quiz_dialog, dialog);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int score = scoreOfQuiz();
                int totalScore = answersTickedByUser.length;
                ((TextView)(dialog.findViewById(R.id.score_in_score_of_quiz_given))).setText(String.valueOf(score) + "/" + totalScore);
                displayAnswerTickerByUser();
                dialog.show();
            }
        });


    }

    private void setClickListener(Button ok_btn_in_score_of_given_quiz_dialog, Dialog dialog) {
        ok_btn_in_score_of_given_quiz_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pushScoreToDataBase();
                dialog.dismiss();
                finish();
            }
        });
    }

    private int scoreOfQuiz() {
        int score = 0;
        for(int i=0; i<question_list.correctAnswers.length; i++){
            if(question_list.correctAnswers[i].equals(question_list.answersTickedByUser[i]))
                score++;
        }
        return score;
    }

    private void setCorrectAnswerList(int position) {
        int i=0;
        for(QuizTemplate rightAns: QuizListAdapter.quizTemplateList.get(position)){
            question_list.correctAnswers[i] = rightAns.getRightOption();
        }
    }

    private void displayAnswerTickerByUser() {
        for(String ans: answersTickedByUser){
            Log.d("quizz" , "answer ticked by user is " + ans);
        }
        for(String ans: question_list.correctAnswers){
            Log.d("quizz", "correct answer is " + ans);
        }
    }
}
