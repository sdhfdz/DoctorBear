package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.doctorbear.Model.AnswerDetailModel;
import com.jinke.doctorbear.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Max on 2016/6/12.
 */
public class AdpAnswerDetailComment extends AdpBase<AnswerDetailModel> {
    public AdpAnswerDetailComment(Context context) {
        super(context);
    }

    public AdpAnswerDetailComment(Context context, List<AnswerDetailModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_answer_detail_listitem, null);
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

    private void initView(ViewHolder holder, View convertView, int position) {
        holder.answerContent = (TextView) convertView.findViewById(R.id.activity_answer_detail_listitem_tv_answerContent);
        holder.iv_head = (ImageView) convertView.findViewById(R.id.activity_answer_detail_listitem_iv_head);
        holder.nickName = (TextView) convertView.findViewById(R.id.activity_answer_detail_listitem_tv_nickname);
        holder.time = (TextView) convertView.findViewById(R.id.activity_answer_detail_listitem_tv_time);

    }

    private void initData(ViewHolder holder, int position) {
        AnswerDetailModel answerDetailModel = list.get(position);
        if (answerDetailModel == null){
            return;
        }
        holder.time.setText(answerDetailModel.getTime());
        holder.nickName.setText(answerDetailModel.getNickName());
        Picasso.with(context).load(answerDetailModel.getIv_head()).error(R.mipmap.logo).into(holder.iv_head);
        holder.answerContent.setText(answerDetailModel.getAnswerContent());
    }

    public class ViewHolder{
        ImageView iv_head;
        TextView nickName;
        TextView time;
        TextView answerContent;
    }
}
