package com.example.prm392myquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseApplicationType extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_application_type);




    }


    public void onStartQuizzApp(View view) {
        Intent intent = new Intent(ChooseApplicationType.this, LoginActivity.class);
        startActivity(intent);
    }
    public void onStartVemoApp(View view) {
        Intent intent = new Intent(ChooseApplicationType.this, OnlineLoginActivity.class);
        startActivity(intent);
    }
}