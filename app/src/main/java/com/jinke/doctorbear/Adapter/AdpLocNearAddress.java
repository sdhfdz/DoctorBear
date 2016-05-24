package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.jinke.doctorbear.Model.FgSearchLocationModel;
import com.jinke.doctorbear.R;

import java.util.List;

/**
 * Created by Max on 2016/5/20.
 */
public class AdpLocNearAddress extends AdpBase<FgSearchLocationModel>{
    boolean isSelected;
    private DataListener listener;


    public AdpLocNearAddress(Context context, List<FgSearchLocationModel> nearList, boolean isSelected) {
        super(context,nearList);
        this.isSelected = isSelected;
    }

    public AdpLocNearAddress(Context context, List<FgSearchLocationModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_fg_search_location_lv_item, null);
            //存对象到view里。
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        init(holder, convertView, position);
        if (list.get(position).isIfON()) {
            holder.tvNumber.setBackgroundResource(R.mipmap.search_location_number_on);
            listener.dataUpdate(list);

        }
        return convertView;

    }
    private void init(ViewHolder holder, View convertView, int position) {
        initView(holder,convertView);
        initData(holder,position);
    }

    /**
     * 初始化listview数据
     * @param holder
     * @param position
     */
    private void initData(ViewHolder holder,  int position) {

        holder.tvNumber.setText(list.get(position).getLocationNumber());
        holder.tvLocationName.setText(list.get(position).getLocationName());
        holder.tvLocation.setText(list.get(position).getLocationDetail());
    }

    /**
     * 初始化所有控件
     * @param holder
     * @param convertView
     */
    private void initView(ViewHolder holder, View convertView) {
        holder.tvLocation = (TextView) convertView.findViewById(R.id.activity_search_location_lv_item_tv_location);
        holder.tvLocationName = (TextView) convertView.findViewById(R.id.activity_search_location_lv_item_tv_location_name);
        holder.tvNumber = (TextView) convertView.findViewById(R.id.activity_search_location_lv_item_iv_location_number);

    }
    public class ViewHolder{
        TextView tvNumber;
        TextView tvLocationName;
        TextView tvLocation;

    }
    /**
     * 设置监听对象，SearchLocation页面通过这个方法设置。
     * @param listener
     */
    public void setUpdateListener(DataListener listener) {
        this.listener = listener;
    }

    /**
     * 更新数据的接口。Confirmed页面通过实现dataUpdate完成回调。
     */
    public interface DataListener{
        void dataUpdate(List<FgSearchLocationModel> list);

    }

}
