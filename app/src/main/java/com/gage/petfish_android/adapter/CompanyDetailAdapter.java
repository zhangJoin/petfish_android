package com.gage.petfish_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.component.ImageLoader;
import com.gage.petfish_android.model.bean.ProdunctInfo;
import com.gage.petfish_android.view.ViewHolder;

import java.util.List;

public class CompanyDetailAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ProdunctInfo> listP;

    public CompanyDetailAdapter(List<ProdunctInfo> images, Context context){
        listP = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listP.size();
    }

    @Override
    public Object getItem(int position) {
        return listP.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_show_varity_gv, null);
        }
        ImageView image_show = ViewHolder.get(convertView, R.id.iv_show_company_fish);
        TextView tv_show=ViewHolder.get(convertView,R.id.tv_grid_show_name);
        tv_show.setVisibility(View.VISIBLE);
        ProdunctInfo info = listP.get(position);
        tv_show.setText(info.getpName());
        ImageLoader.loadImage(inflater.getContext(), Contact.HOST+info.getSarverFileName(),image_show);
        return convertView;
    }
}
