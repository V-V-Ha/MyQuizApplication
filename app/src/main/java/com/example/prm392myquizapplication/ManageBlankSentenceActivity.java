package com.example.prm392myquizapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.adapter.AdminDienKhuyetAdapter;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.BlankSentence;

import java.util.ArrayList;

public class ManageBlankSentenceActivity extends AppCompatActivity {
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgBack, imgAdd;
    ArrayList<BlankSentence> listDienKhuyet;
    AdminDienKhuyetAdapter adapter;
    ListView lvDienKhuyet;
    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_blank_sentence);
        Intent idIntent = getIntent();
        idbo = idIntent.getIntExtra("idBoDienKhuyet", 0);
        lvDienKhuyet = findViewById(R.id.listviewAdminDK);
        imgBack = findViewById(R.id.imgBackAdminDK);
        imgAdd = findViewById(R.id.imgAddAdminDK);
        listDienKhuyet = new ArrayList<>();
        getDienKhuyet();
        adapter = new AdminDienKhuyetAdapter(ManageBlankSentenceActivity.this, listDienKhuyet);
        lvDienKhuyet.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageBlankSentenceActivity.this, ManageBlankSentenceSetActivity.class));
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageBlankSentenceActivity.this, AddBlankSentenceActivity.class);
                intent.putExtra("idBoDienKhuyet", idbo);
                startActivity(intent);
            }
        });
    }

    private void getDienKhuyet() {
        database = Database.initDatabase(ManageBlankSentenceActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Bo = ?", new String[]{String.valueOf(idbo)});
        listDienKhuyet.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idcau = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String noidung = cursor.getString(2);
            String dapan = cursor.getString(3);
            String goiy = cursor.getString(4);
            listDienKhuyet.add(new BlankSentence(idcau, idbo, noidung, dapan, goiy));
        }
    }
}
