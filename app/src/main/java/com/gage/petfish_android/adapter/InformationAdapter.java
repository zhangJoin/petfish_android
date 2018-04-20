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
import com.gage.petfish_android.model.bean.local.InformationInfo;
import com.gage.petfish_android.view.ViewHolder;

/**
 * Created by zhanglonglong on 2017/11/14.
 */
public class InformationAdapter extends BaseListPageAdapter<InformationInfo.ListBean>{
    public InformationAdapter(Context context) {
        super(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_fish_info,null);
        }
        InformationInfo.ListBean fishBean = list.get(position);
        ImageView iv_show = ViewHolder.get(convertView, R.id.iv_img_info);
        TextView tv_date = ViewHolder.get(convertView, R.id.tv_date_info);
        TextView tv_title = ViewHolder.get(convertView, R.id.tv_title_info);
        tv_date.setText(fishBean.updatedate.substring(0,16));
        tv_title.setText(fishBean.title);
        ImageLoader.loadImage(context, Contact.HOST+fishBean.sarverFileName,iv_show);
        return convertView;
    }
}
