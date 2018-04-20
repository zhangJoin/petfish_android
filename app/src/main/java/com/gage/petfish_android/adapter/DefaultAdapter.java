package com.gage.petfish_android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/17.
 */

public abstract class DefaultAdapter <T> extends BaseAdapter{
    private List<T> datas;
    private Context mContext;
    public DefaultAdapter(List<T> datas,Context context){
        this.datas=datas;
        this.mContext=context;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
