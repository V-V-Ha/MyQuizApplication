package com.example.prm392myquizapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.adapter.DSTuVungAdapter;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.TuVung;

import java.util.ArrayList;


public class VocabularyListActivity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ImageView imgback;

    GridView dstuvungs;
    Button Ontap;
    ArrayList<TuVung> DStuvung;
    DSTuVungAdapter adapter;

    int idbo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary_list);

        Intent intent = getIntent();
        idbo = intent.getIntExtra("idbo",0);

        dstuvungs = findViewById(R.id.lgvTuVung);
        imgback = findViewById(R.id.imgVBackDSTV);
        DStuvung = new ArrayList<>();
        AddArrayTV();

        if(DStuvung.size() <= 0) {
            Toast.makeText(VocabularyListActivity.this, "Nội dung sẽ cập nhật cập nhật trong thời gian sớm nhất! Mong mọi người thông càm!!", Toast.LENGTH_LONG).show();
            Intent error = new Intent(VocabularyListActivity.this, StudyVocabularyActivity.class);
            finish();
            startActivity(error);
        }
        else {
            adapter = new DSTuVungAdapter(VocabularyListActivity.this,R.layout.row_vocabulary_list, DStuvung);
            dstuvungs.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            imgback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent
                            = new Intent(VocabularyListActivity.this,
                            StudyVocabularyActivity.class);
                    startActivity(intent);
                }
            });


        }
    }

    private void AddArrayTV(){
        database = Database.initDatabase(VocabularyListActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TuVung WHERE ID_Bo = ?",new String[]{String.valueOf(idbo)});
        DStuvung.clear();

        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idtu = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String dapan = cursor.getString(2);
            String dichnghia = cursor.getString(3);
            String loaitu = cursor.getString(4);
            byte[] anh = cursor.getBlob(5);

            DStuvung.add(new TuVung(idtu,idbo,dapan,dichnghia,loaitu,anh));
        }
    }
}