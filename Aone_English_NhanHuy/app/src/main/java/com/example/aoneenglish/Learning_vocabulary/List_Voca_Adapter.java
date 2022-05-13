package com.example.aoneenglish.Learning_vocabulary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aoneenglish.R;

import java.util.List;

public class List_Voca_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Vocabulary> dstuvungs;

    public List_Voca_Adapter(Context context, int layout, List<Vocabulary> dstuvungs) {
        this.context = context;
        this.layout = layout;
        this.dstuvungs = dstuvungs;
    }

    @Override
    public int getCount() {
        return dstuvungs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgHinh;
        TextView twDichNghia;
        TextView twTuVung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgHinh = (ImageView) convertView.findViewById(R.id.imgHinh);
            holder.twDichNghia = (TextView) convertView.findViewById(R.id.twDichNghia);
            holder.twTuVung = (TextView) convertView.findViewById(R.id.twTuVung);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Vocabulary vocabulary = dstuvungs.get(position);
        holder.twDichNghia.setText(vocabulary.getDichnghia());
        holder.twTuVung.setText(vocabulary.getDapan() + "(" + vocabulary.getLoaitu() + "):");
        Bitmap img = BitmapFactory.decodeByteArray(vocabulary.getAnh(), 0, vocabulary.getAnh().length);
        holder.imgHinh.setImageBitmap(img);
        return convertView;


    }
}

