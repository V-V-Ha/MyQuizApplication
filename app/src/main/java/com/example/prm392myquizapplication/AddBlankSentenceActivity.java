package com.example.prm392myquizapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.Database;

import java.util.Arrays;



public class AddBlankSentenceActivity extends AppCompatActivity {
    ImageView imgBack, imgAdd;
    EditText edtNoiDung, edtGoiY, edtDapAn;
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    int idBDK = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blanksentence);
        imgBack = findViewById(R.id.imgBackAddDK);
        imgAdd = findViewById(R.id.imgAddDK);
        edtNoiDung = findViewById(R.id.edtCauHoiAddDK);
        edtGoiY = findViewById(R.id.edtGoiYAddDK);
        edtDapAn = findViewById(R.id.edtDapAnAddDK);
        idBDK = getIntent().getIntExtra("idBoDienKhuyet", -1);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBlankSentenceActivity.this, ManageBlankSentenceActivity.class);
                intent.putExtra("idBoDienKhuyet", idBDK);
                startActivity(intent);
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtNoiDung.getText().toString();
                String dapan = edtDapAn.getText().toString();
                String goiy = edtGoiY.getText().toString();
                if (noidung == "" || dapan == "" || goiy == ""){
                    Toast.makeText(AddBlankSentenceActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean check = checkDapAnInGoiY(dapan, goiy);
                    if (check) {
                        Boolean result = addDienKhuyet(idBDK, noidung, dapan, goiy);
                        if (result) {
                            Toast.makeText(AddBlankSentenceActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddBlankSentenceActivity.this, ManageBlankSentenceActivity.class);
                            intent.putExtra("idBoDienKhuyet", idBDK);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AddBlankSentenceActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(AddBlankSentenceActivity.this, "Vui lòng nhập đúng đáp án", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private Boolean addDienKhuyet(int idbo, String noidung, String dapan, String goiy) {
        database = Database.initDatabase(AddBlankSentenceActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("ID_Bo", idbo);
        values.put("NoiDung", noidung);
        values.put("DapAn", dapan);
        values.put("GoiY", goiy);
        long result = database.insert("DienKhuyet", null, values);
        return result != -1;
    }

    private Boolean checkDapAnInGoiY(String dapan, String goiy) {
        goiy = goiy.replaceAll("\\W", " ");
        goiy = goiy.trim().replaceAll("\\s{2,}", " ");
        String[] dapAn = goiy.split(" ");
        return Arrays.asList(dapAn).contains(dapan);
    }
}
