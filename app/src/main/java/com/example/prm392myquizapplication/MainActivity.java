package com.example.prm392myquizapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.prm392myquizapplication.adapter.ViewHomePagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager homeViewPager;
    private BottomNavigationView homeBottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeViewPager = findViewById(R.id.home_view_pager);
        homeBottomNavigationView = findViewById(R.id.home_bottom_navigation);

        ViewHomePagerAdapter adapter = new ViewHomePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        homeViewPager.setAdapter(adapter);

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        homeBottomNavigationView.getMenu().findItem(R.id.home_quiz).setChecked(true);
                        break;
                    case 1:
                        homeBottomNavigationView.getMenu().findItem(R.id.home_user).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Bắt sự kiện khi chọn một mục trên thanh điều hướng
        homeBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_quiz:
                        homeViewPager.setCurrentItem(0);
                        return true;
                    case R.id.home_user:
                        homeViewPager.setCurrentItem(1);
                        return true;

                }
                return true;
            }
        });











//        TextView tvUsername = findViewById(R.id.tvUsernameHome);
//        CardView cvStartQuiz = findViewById(R.id.cvStartQuiz);
//        CardView cvRule = findViewById(R.id.cvRule);
//        CardView cvHistory = findViewById(R.id.cvHistory);
//        CardView cvEditPassword = findViewById(R.id.cvEditPassword);
//        CardView cvLogout = findViewById(R.id.cvLogout);
//        CardView cvSubjectManager = findViewById(R.id.cvSubjectManager);
//        CardView cvQuizmanager = findViewById(R.id.cvQuizmanager);
//        SharedPref sharedPref = SharedPref.getInstance();
//        User user = sharedPref.getUser(this);
//        tvUsername.setText("Hello, " + user.getUsername());
//
//        cvStartQuiz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, SelectSubjectActivity.class));
//            }
//        });
//
//
//        cvHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,HistoryActivity.class));
//            }
//        });
//
//        cvEditPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,EditPasswordActivity.class));
//            }
//        });
//        cvSubjectManager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, SubjectManagementActivity.class));
//            }
//        });
//        cvQuizmanager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, QuizManagementActivity.class));
//            }
//        });
//        cvLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sharedPref.clearSharedPref(MainActivity.this);
//                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//


    }
}