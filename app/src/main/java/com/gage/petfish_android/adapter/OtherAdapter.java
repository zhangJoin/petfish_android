package com.gage.petfish_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.component.ImageLoader;
import com.gage.petfish_android.model.bean.OtherInfo;
import com.gage.petfish_android.view.ViewHolder;


/**
 * Created by zhanglonglong on 2017/11/16.
 */

public class OtherAdapter extends BaseListPageAdapter<OtherInfo.OtherBean> {

    public OtherAdapter(Context context) {
        super(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_other, null);
        }
        ImageView ivItemOther= ViewHolder.get(convertView,R.id.iv_item_other);
        TextView tvItemTitle= ViewHolder.get(convertView,R.id.tv_item_title);
        TextView tvItemMaterial= ViewHolder.get(convertView,R.id.tv_item_material);
        TextView tvItemPrice= ViewHolder.get(convertView,R.id.tv_item_price);
        OtherInfo.OtherBean otherBean = list.get(position);
        tvItemTitle.setText(otherBean.name);
        tvItemPrice.setText("￥"+otherBean.price+"");
        tvItemMaterial.setText(otherBean.cName);
        ImageLoader.loadImage(context, Contact.HOST+otherBean.sarverFileName,ivItemOther);
        return convertView;
    }
}
