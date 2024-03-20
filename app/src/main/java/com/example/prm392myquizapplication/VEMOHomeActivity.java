package com.example.prm392myquizapplication;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.dao.DatabaseAccess;
import com.example.prm392myquizapplication.data.OnlineUser;
import com.example.prm392myquizapplication.other.MessageObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


public class VEMOHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final MessageObject messageObject = MessageObject.getInstance();
    final  String DATABASE_NAME = "HocNgonNgu.db";
    Boolean doubleBack = false;
    SQLiteDatabase database;
    DatabaseAccess DB;
    OnlineUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vemo_home);

//        DB = DatabaseAccess.getInstance(getApplicationContext());
//        LayUser();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }



    @Override
    public void onBackPressed() {
        if (doubleBack) {
            super.onBackPressed();
            finish();
            //thoát ra Login
            moveTaskToBack(true);
        }
        this.doubleBack = true;
        Toast.makeText(getApplicationContext(), "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        //thời gian chờ là 2s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void LayUser()
    {
        database = Database.initDatabase(VEMOHomeActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        String Email = cursor.getString(2);
        String SDT = cursor.getString(3);
        user = new OnlineUser(Iduser,HoTen,Email,SDT);
        cursor.close();
    }


}