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



public class ManageBlankSentenceSetActivity extends AppCompatActivity {
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgBack;
    ArrayList<StudySet> listBoDienKhuyet;
    BoHocTapAdapter adapter;
    ListView lvBoDienKhuyet;
    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_blanksentenceset);
        lvBoDienKhuyet = findViewById(R.id.listviewAdminBDK);
        imgBack = findViewById(R.id.imgBackAdminBDK);
        listBoDienKhuyet = new ArrayList<>();
        getBoDienKhuyet();
        adapter = new BoHocTapAdapter(ManageBlankSentenceSetActivity.this, R.layout.row_blank_sentence_set, listBoDienKhuyet);
        lvBoDienKhuyet.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageBlankSentenceSetActivity.this, VEMOHomeActivity.class));
            }
        });
        lvBoDienKhuyet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idbo = listBoDienKhuyet.get(position).getIdBo();
                Intent intent = new Intent(ManageBlankSentenceSetActivity.this, ManageBlankSentenceActivity.class);
                intent.putExtra("idBoDienKhuyet", idbo);
                startActivity(intent);
            }
        });
    }

    private void getBoDienKhuyet() {
        database = Database.initDatabase(ManageBlankSentenceSetActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        listBoDienKhuyet.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            int stt = cursor.getInt(1);
            String ten = cursor.getString(2);
            listBoDienKhuyet.add(new StudySet(id, stt, ten));
        }
    }
}
