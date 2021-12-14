package com.example.quiz.create_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.quiz.MainActivity;
import com.example.quiz.QuizTemplate;
import com.example.quiz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateQuiz extends AppCompatActivity {
    List<QuizTemplate> quizTemplates;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_create_quiz);

        quizTemplates = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

        EditText topicName = findViewById(R.id.topic_name);
        ImageButton saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushQuizTemplatesToFirebase(topicName.getText().toString());
            }
        });

    }

    private void pushQuizTemplatesToFirebase(String topicName) {
        mDatabase.child(topicName).child(MainActivity.USER_NAME).child("latest").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("quizz", "Error getting data", task.getException());
                }else{
                    String quizNo = String.valueOf(task.getResult().getValue());
                    int quizNumber = 0;
                    if(!quizNo.equals("null")){
                        quizNumber = Integer.parseInt(quizNo) + 1;
                    }
                    pushTemplatesToDatabaseUndeer(topicName, quizNumber);

                }
            }
        });
    }

    private void pushTemplatesToDatabaseUndeer(String topicName, int quizNumber) {
        mDatabase.child(topicName).child(MainActivity.USER_NAME).child(String.valueOf(quizNumber)).setValue(quizTemplates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "successfully pushed to database", Toast.LENGTH_SHORT).show();
                    increaseLatestValue(topicName, quizNumber);
                    onSupportNavigateUp();
                }
            }
        });
    }

    private void increaseLatestValue(String topicName, int quizNumber) {
        mDatabase.child(topicName).child(MainActivity.USER_NAME).child("latest").setValue(quizNumber);

    }

    private void addToQuizTemplatesRecyclerView(QuizRecyclerAdapter quizRecyclerAdapter, Dialog dialog) {
        dialog.show();
        Button button = dialog.findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = ((EditText)dialog.findViewById(R.id.question)).getText().toString();
                List<String> options = new ArrayList<>();
                options.add(((EditText)dialog.findViewById(R.id.option1)).getText().toString());
                options.add(((EditText)dialog.findViewById(R.id.option2)).getText().toString());
                options.add(((EditText)dialog.findViewById(R.id.option3)).getText().toString());
                options.add(((EditText)dialog.findViewById(R.id.option4)).getText().toString());
                String right_option = ((EditText)dialog.findViewById(R.id.right_option)).getText().toString();
                String support_answer = ((EditText)dialog.findViewById(R.id.support_for_answer)).getText().toString();

                if(notNull(question, options.get(0), options.get(1), options.get(2), options.get(3), right_option)) {
                    QuizTemplate quizTemplate = new QuizTemplate(question, options, right_option);
                    if (notNull(support_answer)) quizTemplate.setReasonForAnswer(support_answer);
                    else quizTemplate.setReasonForAnswer("");
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