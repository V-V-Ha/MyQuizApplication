package com.example.prm392myquizapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.adapter.AdminTracNghiemAdapter;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.VemoQuiz;

import java.util.ArrayList;



public class ManageVemoQuizActivity extends AppCompatActivity {
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgBack, imgAdd;
    ArrayList<VemoQuiz> listTracNghiem;
    AdminTracNghiemAdapter adapter;
    ListView lvTracNghiem;
    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vemo_quiz);
        Intent idIntent = getIntent();
        idbo = idIntent.getIntExtra("idBoTracNghiem", 0);
        lvTracNghiem = findViewById(R.id.listviewAdminTN);
        imgBack = findViewById(R.id.imgBackAdminTN);
        imgAdd = findViewById(R.id.imgAddAdminTN);
        listTracNghiem = new ArrayList<>();
        getTracNghiem();
        adapter = new AdminTracNghiemAdapter(ManageVemoQuizActivity.this, listTracNghiem);
        lvTracNghiem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageVemoQuizActivity.this, ManaVemoQuizSetActivity.class));
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageVemoQuizActivity.this, AddVemoQuizActivity.class);
                intent.putExtra("idBoTracNghiem", idbo);
                startActivity(intent);
            }
        });
    }

    private void getTracNghiem() {
        database = Database.initDatabase(ManageVemoQuizActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TracNghiem WHERE ID_Bo = ?", new String[]{String.valueOf(idbo)});
        listTracNghiem.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idcau = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String noidung = cursor.getString(2);
            String dapanA = cursor.getString(3);
            String dapanB = cursor.getString(4);
            String dapanC = cursor.getString(5);
            String dapanD = cursor.getString(6);
            String dapanTrue = cursor.getString(7);
            listTracNghiem.add(new VemoQuiz(idcau, idbo, noidung, dapanA, dapanB, dapanC, dapanD, dapanTrue));
        }
    }
}
