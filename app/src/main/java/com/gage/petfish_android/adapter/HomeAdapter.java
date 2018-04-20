package com.gage.petfish_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gage.petfish_android.R;
import com.gage.petfish_android.app.Contact;
import com.gage.petfish_android.component.ImageLoader;
import com.gage.petfish_android.model.bean.FishShow;

import java.util.List;

/**
 * ---日期----------维护人-----------
 * 2017/9/13       zuoyouming
 */

public class HomeAdapter extends BaseAdapter {
    private List<FishShow> listItems;
    private LayoutInflater mInflater;
    private String type;
    Context ctx;

    public HomeAdapter(Context context,
                       List<FishShow> listItem, String type) {
        this.listItems = listItem;
        this.type = type;
        this.ctx = context;
    }

    /**
     * 得到总的数量
     */
    @Override
    public int getCount() {
        return listItems.size() > 4 ? 4 : listItems.size();
    }

    /**
     * 根据listview返回view
     */
    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    /**
     * 根据listview位置得到list中的ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据位置得到view对象
     */
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_child_home, null);
            viewHolder.titleChild = (TextView) convertView.findViewById(R.id.tv_title_child);
            viewHolder.dateChild = (TextView) convertView.findViewById(R.id.tv_date_child);
            viewHolder.addresscompany = (TextView) convertView.findViewById(R.id.tv_address_company);
            viewHolder.imgchild = (ImageView) convertView.findViewById(R.id.iv_img_child);
            viewHolder.rl_list = (RelativeLayout) convertView.findViewById(R.id.rl_list);
            viewHolder.rl_company = (RelativeLayout) convertView.findViewById(R.id.rl_company);
            viewHolder.iv_img_company = (ImageView) convertView.findViewById(R.id.iv_img_company);
            viewHolder.titlecompany = (TextView) convertView.findViewById(R.id.tv_title_company);
            viewHolder.datecompany = (TextView) convertView.findViewById(R.id.tv_date_company);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FishShow info = listItems.get(i);
        if ("1".equals(type)) {
            viewHolder.rl_list.setVisibility(View.VISIBLE);
            viewHolder.titleChild.setText(info.getName());
            viewHolder.dateChild.setText(ctx.getString(R.string.habit)+info.getContent());
            ImageLoader.loadImage(ctx, Contact.HOST + info.getImageUrl(), viewHolder.imgchild);
        } else if ("5".equals(type)) {
            viewHolder.rl_company.setVisibility(View.VISIBLE);
            viewHolder.titlecompany.setText(info.getName());
            viewHolder.datecompany.setText("电话:" + info.getPhone());
            viewHolder.addresscompany.setText("地址:" + info.getAddress());
            ImageLoader.loadImage(ctx, Contact.HOST + info.getImageUrl(), viewHolder.iv_img_company);
        } else {
            viewHolder.rl_list.setVisibility(View.VISIBLE);
            if (null != info.getName()) {
                if (viewHolder.titleChild != null) {
                    viewHolder.titleChild.setText(info.getName());
                }
            }
            if (info.getDate() != null) {
                viewHolder.dateChild.setText(info.getDate().substring(0, 16));
            }
            ImageLoader.loadImage(ctx, Contact.HOST + info.getImageUrl(), viewHolder.imgchild);
        }
        return convertView;
    }

    ;

    /**
     * 静态viewHolder
     */
    static class ViewHolder {
        TextView titleChild;
        TextView dateChild;
        TextView addresscompany;
        ImageView imgchild;
        RelativeLayout rl_list;
        RelativeLayout rl_company;
        ImageView iv_img_company;
        TextView titlecompany;
        TextView datecompany;
    }
}
