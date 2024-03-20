package com.example.prm392myquizapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.adapter.BoHocTapAdapter;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.StudySet;

import java.util.ArrayList;



public class ManaVemoQuizSetActivity extends AppCompatActivity {
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgBack;
    ArrayList<StudySet> listBoTracNghiem;
    BoHocTapAdapter adapter;
    ListView lvBoTracNghiem;
    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_vemo_quiz_set);
        lvBoTracNghiem = findViewById(R.id.listviewAdminBTN);
        imgBack = findViewById(R.id.imgBackAdminBTN);
        listBoTracNghiem = new ArrayList<>();
        getBoTracNghiem();
        adapter = new BoHocTapAdapter(ManaVemoQuizSetActivity.this, R.layout.row_vemo_quiz_set, listBoTracNghiem);
        lvBoTracNghiem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManaVemoQuizSetActivity.this, VEMOHomeActivity.class));
            }
        });
        lvBoTracNghiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idbo = listBoTracNghiem.get(position).getIdBo();
                Intent intent = new Intent(ManaVemoQuizSetActivity.this, ManageVemoQuizActivity.class);
                intent.putExtra("idBoTracNghiem", idbo);
                startActivity(intent);
            }
        });
    }

    private void getBoTracNghiem() {
        database = Database.initDatabase(ManaVemoQuizSetActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        listBoTracNghiem.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            int stt = cursor.getInt(1);
            String ten = cursor.getString(2);
            listBoTracNghiem.add(new StudySet(id, stt, ten));
        }
    }
}
