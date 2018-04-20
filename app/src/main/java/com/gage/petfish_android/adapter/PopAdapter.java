package com.gage.petfish_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.model.bean.VarieInfo;
import com.gage.petfish_android.view.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class PopAdapter extends DefaultAdapter<VarieInfo.TypeListBean> {
    private List<VarieInfo.TypeListBean> list;
    private Context ctx;
    private int seclectTemp = -1;

    public PopAdapter(List<VarieInfo.TypeListBean> datas, Context context) {
        super(datas, context);
        this.ctx=context;
        this.list=datas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(ctx).inflate(R.layout.item_log_select,null);
        }
        TextView tvShow = ViewHolder.get(convertView, R.id.tv_display_item_pop);
        final String name=list.get(position).name;
        tvShow.setText(name);
        // 点击改变选中listItem的背景色
        if (seclectTemp == position) {
            tvShow.setBackgroundColor(Color.parseColor("#909090"));
        } else {
            tvShow.setBackgroundColor(Color.parseColor("#F2F2F2"));
        };
        return convertView;
    }
    //标识选择的Item
    public void setSeclection(int position) {
        seclectTemp = position;
    }
}
