package com.example.prm392myquizapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.prm392myquizapplication.fragment.QuizListFragment;
import com.example.prm392myquizapplication.fragment.UserFragment;

public class ViewHomePagerAdapter extends FragmentStatePagerAdapter {
    public ViewHomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QuizListFragment();
            case 1:
                return new UserFragment();
            default:
                return new QuizListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
