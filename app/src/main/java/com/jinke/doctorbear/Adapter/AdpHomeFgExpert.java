package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.jinke.doctorbear.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 科普界面ListView的适配器
 * Created by Max on 2016/5/16.
 */
public class AdpHomeFgExpert extends BaseAdapter {

    public List<FgHomeExpertModel> list;
    Context context;
    boolean data;

    public AdpHomeFgExpert(Context context,List<FgHomeExpertModel> list) {
        this.list = list;
        this.context = context;
    }

    public AdpHomeFgExpert( Context context, List<FgHomeExpertModel> list, boolean data) {
        this.list = list;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fg_home_expert_lv_item, null);
            //存对象到view里。
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        init(holder,convertView,position);
        return convertView;
    }


    private void init(ViewHolder holder, View convertView, int position) {
        initView(holder,convertView,position);
        initData(holder,position);
    }
    /**
     * 初始化listview数据
     * @param holder
     * @param position
     */
    private void initData(ViewHolder holder,  int position) {
        if (data) {
            FgHomeExpertModel fgHomeExpertModel = list.get(position);
            if (fgHomeExpertModel == null) {
                return;
            }
            holder.comment.setText(fgHomeExpertModel.getComment());
            holder.nickName.setText(fgHomeExpertModel.getNickName());
            holder.like.setText(fgHomeExpertModel.getLike());
            holder.expertTitle.setText(fgHomeExpertModel.getExpertTitle());
            holder.time.setText(fgHomeExpertModel.getTime());
            Picasso.with(context).load(fgHomeExpertModel.getIv_headImage()).error(R.mipmap.logo).into(holder.iv_headImage);
            Picasso.with(context).load(fgHomeExpertModel.getIv_headImage()).error(R.mipmap.logo).into(holder.expertPicture);

        }
    }
    /**
     * 初始化所有控件
     * @param holder
     * @param convertView
     * @param position
     */
    private void initView(ViewHolder holder, View convertView, int position) {
        holder.iv_headImage = (ImageView) convertView.findViewById(R.id.fg_home_expert_lv_iv_head);
        holder.expertPicture = (ImageView) convertView.findViewById(R.id.fg_home_expert_lv_iv_expertpicture);
        holder.comment = (TextView) convertView.findViewById(R.id.fg_home_expert_lv_tv_comment);
        holder.nickName = (TextView) convertView.findViewById(R.id.fg_home_expert_lv_tv_nickname);
        holder.time = (TextView) convertView.findViewById(R.id.fg_home_expert_lv_tv_time);
        holder.expertTitle = (TextView) convertView.findViewById(R.id.fg_home_expert_lv_tv_expertTitle);
        holder.like = (TextView) convertView.findViewById(R.id.fg_home_expert_lv_tv_like);

    }

    public class ViewHolder{
        ImageView iv_headImage;
        TextView nickName;
        TextView time;
        TextView expertTitle;
        ImageView expertPicture;
        TextView like;
        TextView comment;
    }
}
