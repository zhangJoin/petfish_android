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
import com.gage.petfish_android.model.bean.FishShow;
import com.gage.petfish_android.view.ViewHolder;

/**
 * ---日期----------维护人-----------
 * 2017/11/15       zuoyouming
 */

public class EnterpriseAdapter extends BaseListPageAdapter<FishShow> {
    public EnterpriseAdapter(Context context) {
        super(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_parent_enterprise,null);
        }
        FishShow fishShow = list.get(position);
        ImageView iv_company = ViewHolder.get(convertView,R.id.iv_img_parent);
        TextView tv_title = ViewHolder.get(convertView,R.id.tv_title_parent);
        TextView tv_phone = ViewHolder.get(convertView,R.id.tv_phone_parent);
        TextView tv_address = ViewHolder.get(convertView,R.id.tv_address_company);

        tv_title.setText(fishShow.getName());
        tv_phone.setText("电话:"+fishShow.getPhone());
        tv_address.setText("地址:"+fishShow.getAddress());
        ImageLoader.loadImage(context, Contact.HOST + fishShow.getImageUrl(),iv_company);
        return convertView;
    }
}
