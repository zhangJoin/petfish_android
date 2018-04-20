package com.gage.petfish_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.model.bean.QualityInfo;
import com.gage.petfish_android.view.ViewHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhanglonglong on 2017/11/20.
 * 观赏鱼名称
 */

public class QualityFishAdapter extends DefaultAdapter<QualityInfo.NameListBean>{
    private LayoutInflater inflater;
    private List<QualityInfo.NameListBean>dates;
    public HashMap<Integer, String> map = new HashMap<Integer, String>();
    static HashMap<Integer, Boolean> isSelected;
    public QualityFishAdapter(List<QualityInfo.NameListBean> datas, Context context) {
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
        TextView tv_item_name = ViewHolder.get(convertView, R.id.tv_item_material1);
        tv_item_name.setText(dates.get(position).name);
        if(getIsSelected().get(position)==true){
            tv_item_name.setTextColor(Color.parseColor("#0076ca"));
        }else{
            tv_item_name.setTextColor(Color.parseColor("#646464"));
        }

        return convertView;
    }
    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        QualityFishAdapter.isSelected = isSelected;
    }
}
