package com.example.prm392myquizapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DatabaseAccess {
    private final SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    final  String DATABASE_NAME = "HocNgonNgu.db";
    FirebaseDatabase rootNode;
    DatabaseReference userref;
    private static DatabaseAccess instance;
    Cursor c= null;
    public String iduser;
    Map<String,String> user;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);

    }

    public  static  DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }
    public  void close(){
        if(db!=null){
            this.db.close();
        }
    }
    public Boolean insertData(String iduser,String hoten, String email,String sdt)
    {
        db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_User",iduser);
        contentValues.put("HoTen",hoten);
        contentValues.put("Email",email);
        contentValues.put("SDT",sdt);
        long result = db.insert("User",null,contentValues);
        return result != -1;

    }
    public Boolean checktaikhoan(String email)
    {
        db = openHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where Email = ?", new String[]{email});
        return cursor.getCount() > 0;

    }

    public void CapNhatUser(String iduser) {

        //Kiểm tra xem dữ liệu đã có trong SQLite chưa
        db = openHelper.getWritableDatabase();
        //Lấy dữ liệu từ Firebase xuống
        rootNode = FirebaseDatabase.getInstance();
        userref = rootNode.getReference("User").child(iduser);
        Cursor cursor = db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
        if(cursor.getCount() >0)
        {
            //Cập Nhật User từ FireBase
            //TH1: Đã có dữ liệu ở SQLite
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = (Map<String, String>) dataSnapshot.getValue();

                    db = openHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("HoTen",user.get("hoTen"));
                    contentValues.put("SDT",user.get("sdt"));
                    //db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
                    db.update("User",contentValues,"ID_User = ?", new String[]{iduser});
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
        else
        {
            //Cập Nhật User từ FireBase
            //TH2: Chưa có dữ liệu ở SQLite
            rootNode = FirebaseDatabase.getInstance();
            userref = rootNode.getReference("User").child(iduser);

            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = (Map<String, String>) dataSnapshot.getValue();
                    db = openHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ID_User",iduser);
                    contentValues.put("HoTen",user.get("hoTen"));
                    contentValues.put("Email",user.get("email"));
                    contentValues.put("SDT",user.get("sdt"));
                    db.insert("User",null,contentValues);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }

    public Boolean capnhatthongtin(String iduser, String hoten,String sdt){
        rootNode = FirebaseDatabase.getInstance();
        userref = rootNode.getReference("User").child(iduser);

        userref.child("hoTen").setValue(hoten);
        userref.child("sdt").setValue(sdt);


        db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HoTen",hoten);
        contentValues.put("SDT",sdt);
        Cursor cursor = db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
        if(cursor.getCount()>0) {
            long result = db.update("User",contentValues,"ID_User = ?", new String[]{iduser});
            return result != -1;
        }
        else {
            return  false;
        }



    }






}
