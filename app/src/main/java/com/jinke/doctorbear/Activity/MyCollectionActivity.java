package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;


import com.jinke.doctorbear.Adapter.AdpMyCollection;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.NoScrollViewPager;

/**
 * Created by QZ on 2016/5/17.
 */
public class MyCollectionActivity extends Activity implements View.OnClickListener{
    private TextView mycoll_answer_tv;
    private TextView mycoll_answer_line_tv;
    private TextView mycoll_expert_tv;
    private TextView mycoll_expert_line_tv;
    private NoScrollViewPager mycoll_vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollection);
        initView();
        initListener();
    }

    private void initListener() {
        mycoll_answer_tv.setOnClickListener(this);
        mycoll_expert_tv.setOnClickListener(this);
    }

    private void initView() {
        mycoll_answer_tv = (TextView) findViewById(R.id.mycollection_answer_tv);
        mycoll_answer_line_tv = (TextView) findViewById(R.id.mycollection_answer_line_tv);
        mycoll_expert_tv = (TextView) findViewById(R.id.mycollection_expert_tv);
        mycoll_expert_line_tv = (TextView) findViewById(R.id.mycollection_expert_line_tv);
        mycoll_vp = (NoScrollViewPager) findViewById(R.id.mycollection_vp);
        mycoll_vp.setAdapter(new AdpMyCollection(this));
        changeColorAndPage(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mycollection_answer_tv:
                changeColorAndPage(0);
                break;
            case R.id.mycollection_expert_tv:
                changeColorAndPage(1);
                break;
            default:
                break;
        }
    }
    /**
     * 点击问答和科普改变颜色和页面
     * @param position
     */
    public void changeColorAndPage(int position){
        if (position==0){
            mycoll_answer_tv.setTextColor(this.getResources().getColor(R.color.colorPressed));
            mycoll_expert_tv.setTextColor(this.getResources().getColor(R.color.colorGrayFont));
            mycoll_answer_line_tv.setVisibility(View.VISIBLE);
            mycoll_expert_line_tv.setVisibility(View.INVISIBLE);
            mycoll_vp.setCurrentItem(0,false);
        }
        if (position==1){
            mycoll_answer_tv.setTextColor(this.getResources().getColor(R.color.colorNormal));
            mycoll_expert_tv.setTextColor(this.getResources().getColor(R.color.colorPressed));
            mycoll_answer_line_tv.setVisibility(View.INVISIBLE);
            mycoll_expert_line_tv.setVisibility(View.VISIBLE);
            mycoll_vp.setCurrentItem(1,false);
        }
    }
}
