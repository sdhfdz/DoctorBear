package com.jinke.doctorbear.Adapter;

/**
 * 问答界面ListView的适配器
 * Created by Max on 2016/5/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.CircleImageView;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.lidroid.xutils.BitmapUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdpHomeFgAnswer extends AdpBase<FgHomeAnswerModel> {
    boolean data;
    public String communityID;
    public AdpHomeFgAnswer(Context context, List<FgHomeAnswerModel> listAnswer) {
        super(context,listAnswer);
    }

    public AdpHomeFgAnswer(Context context,List<FgHomeAnswerModel> list, boolean data ) {
        super(context,list);
        this.data = data;

    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.fg_home_answer_lv_item, null);
            //存对象到view里。
            holder = new ViewHolder();
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        init(holder, convertView, position);
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

            FgHomeAnswerModel fgHomeAnswerModel = list.get(position);
            if (fgHomeAnswerModel == null) {
                return;
            }
            holder.comment.setText(fgHomeAnswerModel.getComment());
            holder.nickName.setText(fgHomeAnswerModel.getNickName());
            holder.answerTitle.setText(fgHomeAnswerModel.getAnswerTitle());
            holder.illness.setText(fgHomeAnswerModel.getIllness());
            holder.answerContent.setText(fgHomeAnswerModel.getAnswerContent());
            holder.time.setText(fgHomeAnswerModel.getTime());
            Picasso.with(context).load(fgHomeAnswerModel.getIv_headImage()).error(R.mipmap.logo).fit().into(holder.iv_headImage);
            if (fgHomeAnswerModel.getPicture().equals( GlobalAddress.SERVER)){
                holder.picture.setVisibility(View.GONE);
            }else {
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                Picasso.with(context).load(fgHomeAnswerModel.getPicture()).resize(width-28,300)
                        .centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(holder.picture);

            }

    }

    /**
     * 初始化所有控件
     * @param holder
     * @param convertView
     * @param position
     */
    private void initView(ViewHolder holder, View convertView, int position) {

        holder.iv_headImage = (CircleImageView) convertView.findViewById(R.id.fg_home_answer_lv_iv_head);
        holder.answerContent = (TextView) convertView.findViewById(R.id.fg_home_answer_lv_tv_answerContent);
        holder.answerTitle = (TextView) convertView.findViewById(R.id.fg_home_answer_lv_tv_answerTitle);
        holder.nickName = (TextView) convertView.findViewById(R.id.fg_home_answer_lv_tv_nickname);
        holder.time = (TextView) convertView.findViewById(R.id.fg_home_answer_lv_tv_time);
        holder.illness = (TextView) convertView.findViewById(R.id.fg_home_answer_lv_tv_illness);
        holder.comment = (TextView) convertView.findViewById(R.id.fg_home_answer_lv_tv_comment);
        holder.picture = (ImageView) convertView.findViewById(R.id.fg_home_answer_lv_iv_picture);
    }

    public class ViewHolder{
        CircleImageView iv_headImage;
        TextView nickName;
        TextView time;
        TextView answerTitle;
        TextView answerContent;
        TextView illness;
        TextView comment;
        ImageView picture;
    }
}
