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

import com.example.prm392myquizapplication.EditBlankSentenceActivity;
import com.example.prm392myquizapplication.R;
import com.example.prm392myquizapplication.dao.Database;
import com.example.prm392myquizapplication.data.BlankSentence;

import java.util.ArrayList;


public class AdminDienKhuyetAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<BlankSentence> list;
    SQLiteDatabase database;

    public AdminDienKhuyetAdapter(Context context, ArrayList<BlankSentence> list) {
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
        View view = inflater.inflate(R.layout.row_blank_sentence, null);
        TextView txtTenDienKhuyet = view.findViewById(R.id.tvTenDienKhuyet);
        ImageView imgEdit = view.findViewById(R.id.imgEditDK);
        ImageView imgDelete = view.findViewById(R.id.imgDeleteDK);
        BlankSentence dk = list.get(position);
        txtTenDienKhuyet.setText(dk.getNoidung());
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditBlankSentenceActivity.class);
                intent.putExtra("ID_DK", dk.getIdcau());
                context.startActivity(intent);
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn chắc chắn muốn xóa câu điền khuyết này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean result = deleteDienKhuyet(dk.getIdcau());
                        list.clear();
                        if (result) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                        list = getDienKhuyet(dk.getIdbo());
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

    private Boolean deleteDienKhuyet(int id) {
        database = Database.initDatabase((Activity)context, "HocNgonNgu.db");
        long result = database.delete("DienKhuyet", "ID_Cau = ?", new String[]{String.valueOf(id)});
        return result != 0;
    }

    private ArrayList<BlankSentence> getDienKhuyet(int id) {
        ArrayList<BlankSentence> listDK = new ArrayList<>();
        database = Database.initDatabase((Activity)context, "HocNgonNgu.db");
        Cursor cursor = database.rawQuery("SELECT * FROM DienKhuyet WHERE ID_Bo = ?", new String[]{String.valueOf(id)});
        for (int i = 0; i < cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int idcau = cursor.getInt(0);
            int idbo = cursor.getInt(1);
            String noidung = cursor.getString(2);
            String dapan = cursor.getString(3);
            String goiy = cursor.getString(4);
            listDK.add(new BlankSentence(idcau, idbo, noidung, dapan, goiy));
        }
        return listDK;
    }
}
