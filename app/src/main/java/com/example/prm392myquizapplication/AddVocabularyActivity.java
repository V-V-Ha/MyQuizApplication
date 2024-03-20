package com.example.prm392myquizapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.Database;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;



public class AddVocabularyActivity extends AppCompatActivity {

    ImageView imgBack, imgHinh, imgAdd;
    Button btnChonHinh;
    EditText edtTuVung, edtNghia;
    Spinner spnTuLoai;
    final String DATABASE_NAME = "HocNgonNgu.db";
    final int REQUEST_CHOOSE_PHOTO = 321;
    SQLiteDatabase database;
    int idBTV = 0;
    ArrayList<String> listTuLoai = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocabulary);

        imgBack = findViewById(R.id.imgBackAddTV);
        imgHinh = findViewById(R.id.imgHinhAddTV);
        imgAdd = findViewById(R.id.imgAddTV);
        btnChonHinh = findViewById(R.id.btnChonHinhAddTV);
        edtTuVung = findViewById(R.id.edtTuVungAddTV);
        edtNghia = findViewById(R.id.edtNghiaAddTV);
        spnTuLoai = findViewById(R.id.spnLoaiTuAddTV);

        listTuLoai.add("Danh từ");
        listTuLoai.add("Động từ");
        listTuLoai.add("Tính từ");
        listTuLoai.add("Trạng từ");
        listTuLoai.add("Giới từ");
        ArrayAdapter tuLoaiAdapter = new ArrayAdapter(AddVocabularyActivity.this,
                R.layout.support_simple_spinner_dropdown_item, listTuLoai);
        spnTuLoai.setAdapter(tuLoaiAdapter);
        idBTV = getIntent().getIntExtra("idBoTuVung", -1);
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddVocabularyActivity.this, ManageVocabularyActivity.class);
                intent.putExtra("idBoTuVung", idBTV);
                finishAffinity();
                startActivity(intent);
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dapan = edtTuVung.getText().toString().trim();
                String nghia = edtNghia.getText().toString().trim();

                Integer indexValue = spnTuLoai.getSelectedItemPosition();
                String loaitu = listTuLoai.get(indexValue);

                if (dapan.equals("") || nghia.equals("") || loaitu.equals("") || imgHinh.getDrawable() == null) {
                    Toast.makeText(AddVocabularyActivity.this, "PLEASE INSERT ENOUGH INFORMATION", Toast.LENGTH_SHORT).show();
                }
                else {
                    byte[] anh = getByteArrayFromImageView();

                    Boolean result=false;
                    if (anh != null && anh.length > 0 ){
                        result = addTuVung(idBTV, dapan, nghia, loaitu, anh);
                    }

                    if (result) {
                        Toast.makeText(AddVocabularyActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddVocabularyActivity.this, ManageVocabularyActivity.class);
                        intent.putExtra("idBoTuVung", idBTV);
                        finishAffinity();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddVocabularyActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private Boolean addTuVung(int idbo, String dapan, String nghia, String loaitu, byte[] anh) {
        database = Database.initDatabase(AddVocabularyActivity.this, DATABASE_NAME);
        ContentValues values = new ContentValues();
        values.put("ID_Bo", idbo);
        values.put("DapAn", dapan);
        values.put("DichNghia", nghia);
        values.put("LoaiTu", loaitu);
        values.put("HinhAnh", anh);
        long result = database.insert("TuVung", null, values);
        return result != -1;
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }
    private byte[] getByteArrayFromImageView() {
        BitmapDrawable drawable = (BitmapDrawable) imgHinh.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    imgHinh.setImageBitmap(bmp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
