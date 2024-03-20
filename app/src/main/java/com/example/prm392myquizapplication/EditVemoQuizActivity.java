package com.example.prm392myquizapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.VemoQuiz;

import java.util.ArrayList;



public class EditVemoQuizActivity extends AppCompatActivity {
    ImageView imgBack, imgEdit;
    EditText edtNoiDung, edtDapAnA, edtDapAnB, edtDapAnC, edtDapAnD;
    Spinner spnDapAnTrue;
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ArrayList<VemoQuiz> listTracNghiem;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vemo_quiz);
        imgBack = findViewById(R.id.imgBackEditTN);
        imgEdit = findViewById(R.id.imgEditTN);
        edtNoiDung = findViewById(R.id.edtCauHoiEditTN);
        edtDapAnA = findViewById(R.id.edtDapAnAEditTN);
        edtDapAnB = findViewById(R.id.edtDapAnBEditTN);
        edtDapAnC = findViewById(R.id.edtDapAnCEditTN);
        edtDapAnD = findViewById(R.id.edtDapAnDEditTN);
        spnDapAnTrue = findViewById(R.id.spnDapAnTrueEditTN);
        listTracNghiem = new ArrayList<>();
        ArrayList<String> listDapAn = new ArrayList<>();
        listDapAn.add("A");
        listDapAn.add("B");
        listDapAn.add("C");
        listDapAn.add("D");
        ArrayAdapter dapAnAdapter = new ArrayAdapter(EditVemoQuizActivity.this,
                R.layout.support_simple_spinner_dropdown_item, listDapAn);
        spnDapAnTrue.setAdapter(dapAnAdapter);
        int idTN = getIntent().getIntExtra("ID_TN", -1);
        VemoQuiz tracNghiem = getTracNghiemByID(idTN);
        edtNoiDung.setText(tracNghiem.getNoidung());
        edtDapAnA.setText(tracNghiem.getDapanA());
        edtDapAnB.setText(tracNghiem.getDapanB());
        edtDapAnC.setText(tracNghiem.getDapanC());
        edtDapAnD.setText(tracNghiem.getDapanD());
        switch (tracNghiem.getDapanTrue()) {
            case "1":
                spnDapAnTrue.setSelection(0);
                break;
            case "2":
                spnDapAnTrue.setSelection(1);
                break;
            case "3":
                spnDapAnTrue.setSelection(2);
                break;
            case "4":
                spnDapAnTrue.setSelection(3);
                break;
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditVemoQuizActivity.this, ManageVemoQuizActivity.class);
                intent.putExtra("idBoTracNghiem", tracNghiem.getIdbo());
                startActivity(intent);
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtNoiDung.getText().toString();
                String dapanA = edtDapAnA.getText().toString();
                String dapanB = edtDapAnB.getText().toString();
                String dapanC = edtDapAnC.getText().toString();
                String dapanD = edtDapAnD.getText().toString();
                String dapanDung = spnDapAnTrue.getSelectedItem().toString();
                String dapanTrue = "";
                if (noidung == "" || dapanA == "" || dapanB == "" || dapanC == "" || dapanD == ""){
                    Toast.makeText(EditVemoQuizActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    switch (dapanDung) {
                        case "A":
                            dapanTrue = "1";
                            break;
                        case "B":
                            dapanTrue = "2";
                            break;
                        case "C":
                            dapanTrue = "3";
                            break;
                        case "D":
                            dapanTrue = "4";
                            break;
                    }
                    Boolean result = updateTracNghiem(tracNghiem.getIdcau(), tracNghiem.getIdbo(), noidung, dapanA, dapanB, dapanC, dapanD, dapanTrue);
                    if (result) {
                        Toast.makeText(EditVemoQuizActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditVemoQuizActivity.this, ManageVemoQuizActivity.class);
                        intent.putExtra("idBoTracNghiem", tracNghiem.getIdbo());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(EditVemoQuizActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private VemoQuiz getTracNghiemByID(int id) {
        database = Database.initDatabase(EditVemoQuizActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TracNghiem WHERE ID_Cau = ?", new String[]{String.valueOf(id)});
        listTracNghiem.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
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
        return listTracNghiem.get(0);
    }

    private Boolean updateTracNghiem(int idcau, int idbo, String noidung, String dapanA, String dapanB, String dapanC, String dapanD, String dapanTrue) {
        database = Database.initDatabase(EditVemoQuizActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("ID_Cau", idcau);
        values.put("ID_Bo", idbo);
        values.put("NoiDung", noidung);
        values.put("DapAn_A", dapanA);
        values.put("DapAn_B", dapanB);
        values.put("DapAn_C", dapanC);
        values.put("DapAn_D", dapanD);
        values.put("DapAn_True", dapanTrue);
        Cursor cursor = database.rawQuery("SELECT * FROM TracNghiem WHERE ID_Cau = ?", new String[]{String.valueOf(idcau)});
        if (cursor.getCount() > 0) {
            long result = database.update("TracNghiem", values, "ID_Cau = ?", new String[]{String.valueOf(idcau)});
            return result != -1;
        }
        else {
            return true;
        }
    }

}
