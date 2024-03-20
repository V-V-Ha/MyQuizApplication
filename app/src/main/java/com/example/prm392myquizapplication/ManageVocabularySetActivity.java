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


public class ManageVocabularySetActivity extends AppCompatActivity {
    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgBack;
    ArrayList<StudySet> listBoTuVung;
    BoHocTapAdapter adapter;
    ListView lvBoTuVung;
    int idbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vocabulary_set);
        lvBoTuVung = findViewById(R.id.listviewAdminBTV);
        imgBack = findViewById(R.id.imgBackAdminBTV);
        listBoTuVung = new ArrayList<>();
        getBoTuVung();
        adapter = new BoHocTapAdapter(ManageVocabularySetActivity.this, R.layout.row_vocabulary_set, listBoTuVung);
        lvBoTuVung.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                startActivity(new Intent(ManageVocabularySetActivity.this, VEMOHomeActivity.class));
            }
        });
        lvBoTuVung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idbo = listBoTuVung.get(position).getIdBo();
                Intent intent = new Intent(ManageVocabularySetActivity.this, ManageVocabularyActivity.class);
                intent.putExtra("idBoTuVung", idbo);
                finishAffinity();
                startActivity(intent);
            }
        });
    }

    private void getBoTuVung() {
        database = Database.initDatabase(ManageVocabularySetActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        listBoTuVung.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            int stt = cursor.getInt(1);
            String ten = cursor.getString(2);
            listBoTuVung.add(new StudySet(id, stt, ten));
        }
        cursor.close();
    }
}
