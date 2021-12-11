package com.example.quiz.create_quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz.QuizTemplate;
import com.example.quiz.R;

import java.util.ArrayList;
import java.util.Objects;

public class CreateQuiz extends AppCompatActivity {
    ArrayList<QuizTemplate> quizTemplates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        quizTemplates = new ArrayList<>();
        setContentView(R.layout.activity_create_quiz);
        RecyclerView recyclerView = findViewById(R.id.quiz_questions);
        QuizRecyclerAdapter quizRecyclerAdapter = new QuizRecyclerAdapter(this, quizTemplates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(quizRecyclerAdapter);


        Button add_btn = findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(CreateQuiz.this);
                dialog.setContentView(R.layout.quiz_template_card_view);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                addToQuizTemplatesRecyclerView(quizRecyclerAdapter, dialog);
            }
        });
    }

    private void addToQuizTemplatesRecyclerView(QuizRecyclerAdapter quizRecyclerAdapter, Dialog dialog) {
        dialog.show();
        Button button = dialog.findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = ((EditText)dialog.findViewById(R.id.question)).getText().toString();
                String []options = new String[4];
                options[0] = ((EditText)dialog.findViewById(R.id.option1)).getText().toString();
                options[1] = ((EditText)dialog.findViewById(R.id.option2)).getText().toString();
                options[2] = ((EditText)dialog.findViewById(R.id.option3)).getText().toString();
                options[3] = ((EditText)dialog.findViewById(R.id.option4)).getText().toString();
                String right_option = ((EditText)dialog.findViewById(R.id.right_option)).getText().toString();
                String support_answer = ((EditText)dialog.findViewById(R.id.support_for_answer)).getText().toString();

                if(notNull(question, options[0], options[1], options[2], options[3], right_option)) {
                    QuizTemplate quizTemplate = new QuizTemplate(question, options, right_option);
                    if (notNull(support_answer)) quizTemplate.setReasonForAnswer(support_answer);
                    quizTemplates.add(quizTemplate);
                    quizRecyclerAdapter.notifyItemInserted(quizTemplates.size());
                    dialog.dismiss();
                }else {
                    Toast.makeText(CreateQuiz.this, "Please complete the field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean notNull(String ...toCheck) {
        for (String str :toCheck) {
            if(str.equals("")) return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}