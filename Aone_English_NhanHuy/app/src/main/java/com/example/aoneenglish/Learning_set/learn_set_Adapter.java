package com.example.aoneenglish.Learning_set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aoneenglish.R;

import java.util.List;

public class learn_set_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<learn_Set> learnSetList;

    public learn_set_Adapter(Context context, int layout, List<learn_Set> learnSetList) {
        this.context = context;
        this.layout = layout;
        this.learnSetList = learnSetList;
    }

    @Override
    public int getCount() {
        return learnSetList.size();
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
            holder.txtTenBo = (TextView) convertView.findViewById(R.id.tvTenBo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        learn_Set BoHT = learnSetList.get(position);
        holder.txtTenBo.setText(BoHT.getTenBo());
        return convertView;
    }
}
