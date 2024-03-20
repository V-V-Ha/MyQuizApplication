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


public class VemoQuizActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<StudySet> studySetArrayList;
    BoHocTapAdapter boHocTapAdapter;
    ImageView imgback;
    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    int idbocauhoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list_fix);

        listView= findViewById(R.id.lvtracnghiem);
        imgback = findViewById(R.id.imgVBackTN);
        studySetArrayList =new ArrayList<>();
        AddArrayBTN();
        boHocTapAdapter =new BoHocTapAdapter(VemoQuizActivity.this,R.layout.row_vemo_quiz_set, studySetArrayList);
        listView.setAdapter(boHocTapAdapter);
        boHocTapAdapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                database= Database.initDatabase(VemoQuizActivity.this,DATABASE_NAME);
                String a=null;
                Cursor cursor=database.rawQuery("SELECT * FROM BoCauHoi",null);
                for(int i=position;i<cursor.getCount();i++){
                    cursor.moveToPosition(i);
                    int idbo=cursor.getInt(0);
                    int stt=cursor.getInt(1);
                    String tenbo=cursor.getString(2);
                    a=tenbo;
                    idbocauhoi=idbo;
                    break;
                }
                Intent quiz= new Intent(VemoQuizActivity.this,QuizActivity.class);
                quiz.putExtra("Bo",idbocauhoi);

                startActivity(quiz);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(VemoQuizActivity.this,
                        VEMOHomeActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
                finish();
            }
        });
    }
    private void AddArrayBTN(){
        database= Database.initDatabase(VemoQuizActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT * FROM BoCauHoi",null);
        studySetArrayList.clear();
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            studySetArrayList.add(new StudySet(idbo,stt,tenbo));
        }
    }
}