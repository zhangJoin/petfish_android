package com.gage.petfish_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.component.ImageLoader;
import com.gage.petfish_android.view.ViewHolder;

import java.util.List;

/**
 * ---日期----------维护人-----------
 * 2017/11/17       zuoyouming
 */

public class ShowDetailAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Object> fishShows;

    public ShowDetailAdapter(List<Object> images, Context context){
        fishShows = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fishShows.size();
    }

    @Override
    public Object getItem(int position) {
        return fishShows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_show_gv, null);
        }
        ImageView image_show = ViewHolder.get(convertView, R.id.iv_grid_show);
        Object o = fishShows.get(position);
        ImageLoader.loadImage(inflater.getContext(), Contact.HOST+o,image_show);
        return convertView;
    }
}
