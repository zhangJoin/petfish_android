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

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/17.
 */

public class SearchAdapter extends DefaultAdapter<VarieInfo.MaterialListBean> {
    private LayoutInflater inflater;
    private List<VarieInfo.MaterialListBean>dates;
    public HashMap<Integer, String> map = new HashMap<Integer, String>();
    static HashMap<Integer, Boolean> isSelected;
    public SearchAdapter(List datas, Context context) {
        super(datas, context);
        dates=datas;
        inflater=LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < dates.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= inflater.inflate(R.layout.item_pop_material,null);
        }
        TextView tv_item_material = ViewHolder.get(convertView, R.id.tv_item_material1);
        tv_item_material.setText(dates.get(position).name);
        if(getIsSelected().get(position)==true){
            tv_item_material.setTextColor(Color.parseColor("#0076ca"));
        }else{
            tv_item_material.setTextColor(Color.parseColor("#646464"));
        }
        return convertView;
    }
    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        SearchAdapter.isSelected = isSelected;
    }
}
