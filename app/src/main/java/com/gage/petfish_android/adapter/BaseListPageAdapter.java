package com.gage.petfish_android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 上拉刷新下拉加载的适配器。通常与RefreshLoadListView控件使用
 */
public abstract class BaseListPageAdapter<L> extends BaseAdapter{

    private LayoutInflater inflater;
    protected Context context;
    public BaseListPageAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    protected LayoutInflater getInflater(){
        return inflater;
    }


    protected int totalPage;
    protected int page;
    protected List<L> list = new ArrayList<>();



    /**
     * 向ListView中添加数据
     */
    public void addList(List<L> list){
        if(!this.list.addAll(list)){
            Log.d("BaseListPageAdapter","Add list error");
        }
        notifyDataSetChanged();
    }

    /**
     * 清空ListView中的所有数据
     */
    public void clearList(){
        this.list.clear();
        notifyDataSetInvalidated();
    }

    /**
     * 设置ListView中的数据。原先ListView中的数据会删除
     */
    public void setList(List<L> list){
        this.list = list;
        notifyDataSetInvalidated();
    }

    public List<L> getList(){
        return this.list;
    }

    /**
     * 获取ListView中数据的总页数
     */
    public int getTotalPage(){
        return this.totalPage;
    }

    /**
     * 设置当前数据的总页数
     */
    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }

    /**
     * 获取当前ListView的最后一组数据是第几页
     */
    public int getPage(){
        return this.page;
    }

    /**
     * 设置当前ListView显示的最后一组数据是总数据的第几页
     */
    public void setPage(int page){
        this.page = page;
    }

    @Override
    public L getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    protected final String getString(int id){
        return context.getString(id);
    }

    protected final int getColor(int id){
        return context.getResources().getColor(id);
    }
}
