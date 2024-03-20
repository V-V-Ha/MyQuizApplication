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

import com.example.prm392myquizapplication.EditVemoQuizActivity;
import com.example.prm392myquizapplication.R;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.VemoQuiz;

import java.util.ArrayList;


public class AdminTracNghiemAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<VemoQuiz> list;
    SQLiteDatabase database;

    public AdminTracNghiemAdapter(Context context, ArrayList<VemoQuiz> list) {
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
        View view = inflater.inflate(R.layout.row_quiz, null);
        TextView txtTenTracNghiem = view.findViewById(R.id.tvTenTracNghiem);
        ImageView imgEdit = view.findViewById(R.id.imgEditTN);
        ImageView imgDelete = view.findViewById(R.id.imgDeleteTN);
        VemoQuiz tn = list.get(position);
        txtTenTracNghiem.setText(tn.getNoidung());
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditVemoQuizActivity.class);
                intent.putExtra("ID_TN", tn.getIdcau());
                context.startActivity(intent);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn chắc chắn muốn xóa câu trắc nghiệm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean result = deleteTracNghiem(tn.getIdcau());
                        list.clear();
                        if (result) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        list = getTracNghiem(tn.getIdbo());
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

    private Boolean deleteTracNghiem(int id) {
        database = Database.initDatabase((Activity)context, "HocNgonNgu.db");
        long result = database.delete("TracNghiem", "ID_Cau = ?", new String[]{String.valueOf(id)});
        return result != 0;
    }
    private ArrayList<VemoQuiz> getTracNghiem(int id) {
        ArrayList<VemoQuiz> listTN = new ArrayList<>();
        database = Database.initDatabase((Activity)context, "HocNgonNgu.db");
        Cursor cursor = database.rawQuery("SELECT * FROM TracNghiem WHERE ID_Bo = ?", new String[]{String.valueOf(id)});
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
            listTN.add(new VemoQuiz(idcau, idbo, noidung, dapanA, dapanB, dapanC, dapanD, dapanTrue));
        }
        return listTN;
    }
}
