package com.example.prm392myquizapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prm392myquizapplication.R;
import com.example.prm392myquizapplication.data.StudySet;

import java.util.List;

public class BoHocTapAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final List<StudySet> studySetList;

    public BoHocTapAdapter(Context context, int layout, List<StudySet> studySetList) {
        this.context = context;
        this.layout = layout;
        this.studySetList = studySetList;
    }

    @Override
    public int getCount() {
        return studySetList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenBo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txtTenBo = convertView.findViewById(R.id.tvTenBo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudySet BoHT = studySetList.get(position);
        holder.txtTenBo.setText(BoHT.getTenBo());
        return convertView;
    }
}
