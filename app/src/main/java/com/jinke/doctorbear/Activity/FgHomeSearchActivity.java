package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jinke.doctorbear.R;

/**
 * Created by Max on 2016/5/17.
 */
public class FgHomeSearchActivity extends Activity implements View.OnClickListener{
    private TextView tv_cancel;
    private EditText et_search;
    SharedPreferences sp;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fg_home_search);

        init();
    }

    private void init() {
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化搜索框内的文字数据
     */
    private void initData() {
        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        boolean page = sp.getBoolean("page",true);
        if (page){
            et_search.setHint("搜索<问答>相关内容");
        }else {
            et_search.setHint("搜索<科普>相关内容");
        }

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        tv_cancel.setOnClickListener(this);
        et_search.setOnClickListener(this);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_cancel = (TextView) findViewById(R.id.activity_fg_home_search_tv_cancel);
        et_search = (EditText) findViewById(R.id.activity_fg_home_search_et_search);
    }

    /**
     * 监听事件的实现
     * 取消的监听事件,搜索框的监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_fg_home_search_tv_cancel:
                finish();
//                overridePendingTransition(R.anim.alphaout,R.anim.alphain);
                break;
            case R.id.activity_fg_home_search_et_search:
                et_search.setCursorVisible(true);
                break;
        }

    }
}
