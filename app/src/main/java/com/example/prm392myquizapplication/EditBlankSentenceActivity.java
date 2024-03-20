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
import com.example.prm392myquizapplication.data.BlankSentence;

import java.util.ArrayList;
import java.util.Arrays;


public class EditBlankSentenceActivity extends AppCompatActivity {
    ImageView imgBack, imgEdit;
    EditText edtNoiDung, edtGoiY, edtDapAn;
    final String DATABASE_NAME = "HocNgonNgu.db";
    SQLiteDatabase database;
    ArrayList<BlankSentence> listDienKhuyet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blank_sentence);
        imgBack = findViewById(R.id.imgBackEditDK);
        imgEdit = findViewById(R.id.imgEditDK);
        edtNoiDung = findViewById(R.id.edtCauHoiEditDK);
        edtGoiY = findViewById(R.id.edtGoiYEditDK);
        edtDapAn = findViewById(R.id.edtDapAnEditDK);
        listDienKhuyet = new ArrayList<>();
        int idDK = getIntent().getIntExtra("ID_DK", -1);
        BlankSentence dienKhuyet = getDienKhuyetByID(idDK);
        String[] goiyArr = dienKhuyet.getGoiy().split(" ");
        edtNoiDung.setText(dienKhuyet.getNoidung());
        edtGoiY.setText(dienKhuyet.getGoiy());
        edtDapAn.setText(dienKhuyet.getDapan());
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBlankSentenceActivity.this, ManageBlankSentenceActivity.class);
                intent.putExtra("idBoDienKhuyet", dienKhuyet.getIdbo());
                startActivity(intent);
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtNoiDung.getText().toString();
                String dapan = edtDapAn.getText().toString();
                String goiy = edtGoiY.getText().toString();
                if (noidung == "" || dapan == "" || goiy == ""){
                    Toast.makeText(EditBlankSentenceActivity.this, "Chưa điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean check = checkDapAnInGoiY(dapan, goiy);
                    if (check) {
                        Boolean result = updateDienKhuyet(dienKhuyet.getIdcau(), dienKhuyet.getIdbo(), noidung, dapan, goiy);
                        if (result) {
                            Toast.makeText(EditBlankSentenceActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditBlankSentenceActivity.this, ManageBlankSentenceActivity.class);
                            intent.putExtra("idBoDienKhuyet", dienKhuyet.getIdbo());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(EditBlankSentenceActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(EditBlankSentenceActivity.this, "Vui lòng nhập đúng đáp án", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private BlankSentence getDienKhuyetByID(int id){
        database = Database.initDatabase(EditBlankSentenceActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Cau = ?", new String[]{String.valueOf(id)});
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
        return listDienKhuyet.get(0);
    }

    private Boolean updateDienKhuyet(int idcau, int idbo, String noidung, String dapan, String goiy) {
        database = Database.initDatabase(EditBlankSentenceActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("ID_Cau", idcau);
        values.put("ID_Bo", idbo);
        values.put("NoiDung", noidung);
        values.put("DapAn", dapan);
        values.put("GoiY", goiy);
        Cursor cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Cau = ?", new String[]{String.valueOf(idcau)});
        if (cursor.getCount() > 0) {
            long result = database.update("DienKhuyet", values, "ID_Cau = ?", new String[]{String.valueOf(idcau)});
            return result != -1;
        }
        else {
            return true;
        }
    }

    private Boolean checkDapAnInGoiY(String dapan, String goiy) {
        goiy = goiy.replaceAll("\\W", " ");
        goiy = goiy.trim().replaceAll("\\s{2,}", " ");
        String[] dapAn = goiy.split(" ");
        return Arrays.asList(dapAn).contains(dapan);
    }
}
