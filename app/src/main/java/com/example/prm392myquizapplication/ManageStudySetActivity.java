package com.example.prm392myquizapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.adapter.AdminBoHocTapAdapter;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.StudySet;

import java.util.ArrayList;

public class ManageStudySetActivity extends AppCompatActivity {
    ArrayList<StudySet> listBHT;
    AdminBoHocTapAdapter adapter;
    ImageView imgBack, imgAdd;
    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ListView listViewBHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_study_set);
        listViewBHT = findViewById(R.id.listviewAdminBHT);
        imgBack = findViewById(R.id.imgBackAdminBHT);
        imgAdd = findViewById(R.id.imgAddBHT);
        getBoHocTap();
        adapter = new AdminBoHocTapAdapter(ManageStudySetActivity.this, listBHT);
        listViewBHT.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageStudySetActivity.this, VEMOHomeActivity.class));
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageStudySetActivity.this, AddStudySetActivity.class));
            }
        });
    }

    private void getBoHocTap() {
        database = Database.initDatabase(ManageStudySetActivity.this, DATABASE_NAME);
        listBHT = new ArrayList<>();
        listBHT.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            listBHT.add(new StudySet(idbo, stt, tenbo));
        }
    }
}
