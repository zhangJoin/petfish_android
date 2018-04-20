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
import com.gage.petfish_android.model.bean.DisplayInfo;
import com.gage.petfish_android.view.ViewHolder;

/**
 *
 * Created by zhanglonglong on 2017/11/15.
 */

public class DisplayAdapter extends BaseListPageAdapter<DisplayInfo.DisplayBean>{
    public DisplayAdapter(Context context) {
        super(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_fish_display,null);
        }
        DisplayInfo.DisplayBean displayBean = list.get(position);
        ImageView iv_show = ViewHolder.get(convertView, R.id.iv_img_display);
        TextView tv_date = ViewHolder.get(convertView, R.id.tv_date_display);
        TextView tv_title = ViewHolder.get(convertView, R.id.tv_title_display);
        tv_date.setText(getString(R.string.habit)+displayBean.habit);
        tv_title.setText(displayBean.name);
        ImageLoader.loadImage(context, Contact.HOST+displayBean.sarverFileName,iv_show);
        return convertView;
    }
}
