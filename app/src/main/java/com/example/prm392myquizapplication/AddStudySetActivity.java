package com.example.prm392myquizapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.StudySet;

import java.util.ArrayList;


public class AddStudySetActivity extends AppCompatActivity {
    ImageView imgBack, imgAdd;
    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ArrayList<StudySet> listBHT;
    EditText edtBoHocTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_studyset);
        imgBack = findViewById(R.id.imgBackAddBHT);
        imgAdd = findViewById(R.id.imgAddBHT);
        edtBoHocTap = findViewById(R.id.edtAddBoHocTap);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddStudySetActivity.this, ManageStudySetActivity.class));
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtBoHocTap.getText().toString();
                if (ten == "") {
                    Toast.makeText(AddStudySetActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                else {
                    int max = getMaxSTTBoHocTap();
                    Boolean result = addBoHocTap(max + 1, ten);
                    if (result) {
                        Toast.makeText(AddStudySetActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddStudySetActivity.this, ManageStudySetActivity.class));
                    }
                    else {
                        Toast.makeText(AddStudySetActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private int getMaxSTTBoHocTap() {
        database = Database.initDatabase(AddStudySetActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        ArrayList<Integer> listSTT = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int stt = cursor.getInt(1);
            listSTT.add(stt);
        }
        int max = listSTT.get(0);
        for (int i = 1; i < listSTT.size(); i++) {
            if (listSTT.get(i) > max) {
                max = listSTT.get(i);
            }
        }
        return max;
    }
    private Boolean addBoHocTap(int stt, String ten) {
        database = Database.initDatabase(AddStudySetActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("STT", stt);
        values.put("TenBoCauHoi", ten);
        long result = database.insert("BoCauHoi", null, values);
        return result != -1;
    }
}
