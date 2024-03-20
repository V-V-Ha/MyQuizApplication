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


public class EditStudySetActivity extends AppCompatActivity {
    ImageView imgBack, imgEdit;
    EditText edtBoHocTap;
    final  String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ArrayList<StudySet> listBHT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_study_set);
        imgBack = findViewById(R.id.imgBackEditBHT);
        imgEdit = findViewById(R.id.imgEditBHT);
        edtBoHocTap = findViewById(R.id.edtEditBoHocTap);
        listBHT = new ArrayList<>();
        int idBHT = getIntent().getIntExtra("ID_BHT", -1);
        StudySet studySet = getBoHocTapByID(idBHT);
        edtBoHocTap.setText(studySet.getTenBo());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditStudySetActivity.this, ManageStudySetActivity.class));
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtBoHocTap.getText().toString();
                if (ten == "") {
                    Toast.makeText(EditStudySetActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean result = updateBoHocTap(studySet.getIdBo(), studySet.getStt(), ten);
                    if (result) {
                        Toast.makeText(EditStudySetActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditStudySetActivity.this, ManageStudySetActivity.class));
                    }
                    else {
                        Toast.makeText(EditStudySetActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private StudySet getBoHocTapByID(int id) {
        database = Database.initDatabase(EditStudySetActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi WHERE ID_Bo = ?", new String[]{String.valueOf(id)});
        listBHT.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int stt = cursor.getInt(1);
            String ten = cursor.getString(2);
            listBHT.add(new StudySet(idbo, stt, ten));
        }
        return listBHT.get(0);
    }

    private Boolean updateBoHocTap(int id, int stt, String ten) {
        database = Database.initDatabase(EditStudySetActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("ID_Bo", id);
        values.put("STT", stt);
        values.put("TenBoCauHoi", ten);
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi WHERE ID_Bo = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            long result = database.update("BoCauHoi", values, "ID_Bo = ?", new String[]{String.valueOf(id)});
            return result != -1;
        }
        else {
            return false;
        }
    }
}
