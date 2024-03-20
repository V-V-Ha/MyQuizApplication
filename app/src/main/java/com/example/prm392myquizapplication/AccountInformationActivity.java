package com.example.prm392myquizapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.dao.DatabaseAccess;
import com.example.prm392myquizapplication.data.OnlineUser;


public class AccountInformationActivity extends AppCompatActivity {

    final  String DATABASE_NAME = "HocNgonNgu.db";
    DatabaseAccess DB;
    SQLiteDatabase database;
    ImageView imghome;
    EditText tvHoten,tvEmail,tvSdt,tvUID;
    TextView tvtaikhoan, tvTen,tvPoint;
    Button btnCapNhat;
    String iduser;
    OnlineUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        DB = DatabaseAccess.getInstance(getApplicationContext());
        AnhXa();
        iduser = DB.iduser;
        LayUser();

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CapNhatThongTin();
            }
        });

        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent
                            = new Intent(AccountInformationActivity.this,
                            VEMOHomeActivity.class);
                    intent.putExtras(getIntent());
                    startActivity(intent);
                    finish();


            }
        });

    }
    private void AnhXa()
    {
        tvHoten = findViewById(R.id.textIntEdtHoten);
        tvEmail = findViewById(R.id.textIntEdtEmail);
        tvSdt = findViewById(R.id.textIntEdtSdt);
        tvUID = findViewById(R.id.textIntEdtUID);
        tvtaikhoan = findViewById(R.id.tVusername);
        tvTen = findViewById(R.id.textViewTen);
        tvPoint = findViewById(R.id.textviewPoint);
        btnCapNhat = findViewById(R.id.buttonCapNhat);
        imghome = findViewById(R.id.imgHOME);

        tvUID.setEnabled(false);
        tvEmail.setEnabled(false);

    }
    private void CapNhatThongTin()
    {
        String hoten = tvHoten.getText().toString();
        String sdt = tvSdt.getText().toString();
        if(hoten =="" || sdt=="")
        {
            Toast.makeText(this, "Không hợp lệ", Toast.LENGTH_SHORT).show();
        }
        else{
            Boolean checkupdate = DB.capnhatthongtin(DB.iduser,hoten,sdt);
            if(checkupdate)
            {
                Toast.makeText(this, "Câp nhật thành công", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void TruyenThongTin(){
        //Truyền thông tin
        tvHoten.setText(user.getHoTen());
        tvTen.setText(user.getHoTen());
        tvtaikhoan.setText(user.getEmail());
        tvEmail.setText(user.getEmail());
        tvSdt.setText(user.getSDT());
        tvUID.setText(user.getIduser());

    }

    public void LayUser()
    {
        database = Database.initDatabase(AccountInformationActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        String Email = cursor.getString(2);
        String SDT = cursor.getString(3);
        user = new OnlineUser(Iduser,HoTen,Email,SDT);

        TruyenThongTin();

    }



}