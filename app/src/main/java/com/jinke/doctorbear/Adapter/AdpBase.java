package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器的基类,使用时只需要传入数据模型,并且实现自己的getview方法即可
 * Created by Max on 2016/5/18.
 */
public abstract class AdpBase<T> extends BaseAdapter {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> list = new ArrayList<T>();

    public AdpBase(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public AdpBase(Context context,List<T> list){
        this.context = context;
        this.list = list;
    }

    /**
     * 判断数据是否为空
     *
     * @return 为空返回true，不为空返回false
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 在原有的数据上添加新数据
     *
     * @param itemList
     */
    public void addItems(List<T> itemList) {
        this.list.addAll(itemList);
        notifyDataSetChanged();
    }

    /**
     * 设置为新的数据，旧数据会被清空
     *
     * @param itemList
     */
    public void setItems(List<T> itemList) {
        this.list.clear();
        this.list = itemList;
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearItems() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);
}

