package com.example.quiz.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quiz.QuizTemplate;
import com.example.quiz.R;
import com.example.quiz.attempt_quiz.QuizListAdapter;
import com.example.quiz.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DatabaseReference mDatabase;
    List<List<QuizTemplate>> quizTemplateList;
    List<Pair<String,String>> authorName; //TopicName authorName

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        quizTemplateList = new ArrayList<>();
        authorName = new ArrayList<>();

        RecyclerView quiz_list_recycler_view = root.findViewById(R.id.quiz_list_in_fragment_home);
        QuizListAdapter quizListAdapter = new QuizListAdapter(quizTemplateList, authorName);
        quiz_list_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        quiz_list_recycler_view.setAdapter(quizListAdapter);
        updateQuizzes(quizListAdapter);

        SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipe_refresh);
        setSwipeRefreshLayout(swipeRefreshLayout, quizListAdapter);

        return root;
    }

    private void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout, QuizListAdapter quizListAdapter) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                quizTemplateList.clear();
                authorName.clear();
                quizListAdapter.notifyDataSetChanged();
                updateQuizzes(quizListAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateQuizzes(QuizListAdapter quizListAdapter){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot topic: dataSnapshot.getChildren()){
                    for(DataSnapshot emailAddress: topic.getChildren()){
                        long i=0;
                        long size = emailAddress.getChildrenCount()-1;
                        for(DataSnapshot quizNo: emailAddress.getChildren()){
                            if(i==size) break;
                            i++;
                            List<QuizTemplate> temp = new ArrayList<>();
                            for(DataSnapshot questionNumber: quizNo.getChildren()){
                                temp.add(questionNumber.getValue(QuizTemplate.class));
                            }
                            quizTemplateList.add(temp);
                            authorName.add(new Pair<String, String>(topic.getKey(), emailAddress.getKey()));
                            quizListAdapter.notifyItemInserted(authorName.size());
                            display(temp);
                        }
                    }
                }
                displayAuthor();
                Log.d("quizz", "total quiz size got is " +authorName.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayAuthor() {
        for(Pair<String, String> author: authorName){
            Log.d("displayquiz", author.first + " " + author.second);
        }
    }

    private void display(List<QuizTemplate> quizTemplateList1) {
        for(QuizTemplate quizTemplate: quizTemplateList1){
            Log.d("displayQuiz", quizTemplate.getQuestion());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}