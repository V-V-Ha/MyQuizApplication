package com.example.prm392myquizapplication.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392myquizapplication.ManageBlankSentenceSetActivity;
import com.example.prm392myquizapplication.ManageStudySetActivity;
import com.example.prm392myquizapplication.ManaVemoQuizSetActivity;
import com.example.prm392myquizapplication.ManageVocabularySetActivity;
import com.example.prm392myquizapplication.BlankSentenceActivity;
import com.example.prm392myquizapplication.StudyVocabularyActivity;
import com.example.prm392myquizapplication.R;
import com.example.prm392myquizapplication.VemoQuizActivity;
import com.example.prm392myquizapplication.other.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CardView cardLearnVocabulary, cardAddVocabulary, cardPlayFillGame, cardAddFillGame,cardDoQuiz,cardAddQuiz,cardStudySet;
    ImageView imgview;
    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardLearnVocabulary = root.findViewById(R.id.cv_learn_vocab);
        cardAddVocabulary = root.findViewById(R.id.cv_add_vocab);
        cardPlayFillGame= root.findViewById(R.id.cv_play_fill_game);
        cardAddFillGame= root.findViewById(R.id.cv_add_fill_game);
        cardDoQuiz = root.findViewById(R.id.cv_do_quiz);
        cardAddQuiz = root.findViewById(R.id.cv_add_quiz);
        cardStudySet = root.findViewById(R.id.cv_study_set);
        cardLearnVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), StudyVocabularyActivity.class);
                startActivity(intent);
            }
        });
        cardAddVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ManageVocabularySetActivity.class);
                startActivity(intent);
            }
        });
        cardPlayFillGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BlankSentenceActivity.class);
                startActivity(intent);
            }
        });
        cardAddFillGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ManageBlankSentenceSetActivity.class);
                startActivity(intent);
            }
        });
        cardDoQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), VemoQuizActivity.class);
                startActivity(intent);
            }
        });
        cardAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ManaVemoQuizSetActivity.class);
                startActivity(intent);
            }
        });
        cardStudySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ManageStudySetActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }
}