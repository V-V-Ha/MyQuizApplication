package com.example.prm392myquizapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392myquizapplication.EditStudySetActivity;
import com.example.prm392myquizapplication.R;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.StudySet;

import java.util.ArrayList;


public class AdminBoHocTapAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<StudySet> list;
    SQLiteDatabase database;

    public AdminBoHocTapAdapter(Context context, ArrayList<StudySet> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_study_set, null);
        TextView txtTenBo = view.findViewById(R.id.tvTenBo);
        ImageView imgEdit = view.findViewById(R.id.imgEditBHT);
        ImageView imgDelete = view.findViewById(R.id.imgDeleteBHT);
        StudySet bht = list.get(position);
        txtTenBo.setText(bht.getTenBo());
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditStudySetActivity.class);
                intent.putExtra("ID_BHT", bht.getIdBo());
                context.startActivity(intent);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn chắc chắn muốn xóa bộ học tập này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean result = deleteBoHocTap(bht.getIdBo());
                        list.clear();
                        if (result) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        list = getBoHocTap();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private Boolean deleteBoHocTap(int id) {
        database = Database.initDatabase((Activity)context, "HocNgonNgu.db");



        long result = database.delete("BoCauHoi", "ID_Bo = ?", new String[]{String.valueOf(id)});
        return result != 0;
    }

    private ArrayList<StudySet> getBoHocTap() {
        ArrayList<StudySet> listBHT = new ArrayList<>();
        database = Database.initDatabase((Activity)context, "HocNgonNgu.db");
        Cursor cursor = database.rawQuery("SELECT * FROM BoCauHoi", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idbo = cursor.getInt(0);
            int  stt = cursor.getInt(1);
            String tenbo = cursor.getString(2);
            listBHT.add(new StudySet(idbo, stt, tenbo));
        }
        return listBHT;
    }
}
