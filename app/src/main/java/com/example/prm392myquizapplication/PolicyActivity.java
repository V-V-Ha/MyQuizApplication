package com.example.prm392myquizapplication;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;



public class PolicyActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.policy);

            ImageView back =findViewById(R.id.imageViewHistory);
            back.setOnClickListener(v -> finish());
        }
    }

