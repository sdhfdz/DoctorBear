package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jinke.doctorbear.R;

/**
 * 搜索页面,疾病,药品,医院,莆田集成
 * Created by Max on 2016/5/19.
 */
public class SearchAllActivity extends Activity implements View.OnClickListener{
    private TextView tv_Search1;
    private TextView tv_Search2;
    private TextView tv_Search3;
    private TextView tv_Search4;
    private TextView tv_Search5;
    private TextView tv_cancel;
    private EditText et_Search;

    public int S_FLAG = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fg_search_all);

        init();
    }

    private void init() {
        initView();
        initListener();
        initData();
    }
    /**
     * 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        String kind = intent.getStringExtra("kind");
        if (kind.equals("disease")){
            et_Search.setHint("疾病搜索");
            //从服务器获取数据
            tv_Search1.setText("糖尿病");
            tv_Search2.setText("通风");
            tv_Search3.setText("骨质疏松");
            tv_Search4.setText("月经不调");
            tv_Search5.setText("乙肝");
            S_FLAG = 0;

        }else if (kind.equals("capsule")){
            et_Search.setHint("药品搜索");
            //从服务器获取数据
            tv_Search1.setText("布洛芬");
            tv_Search2.setText("芬必得");
            tv_Search3.setText("百服宁");
            tv_Search4.setText("新康泰克");
            tv_Search5.setText("阿司匹林");
            S_FLAG = 1;//药品

        }else if (kind.equals("hospital")){
            et_Search.setHint("医院搜索");
            //从服务器获取数据
            tv_Search1.setText("北京协和医院");
            tv_Search2.setText("浙江大学附属第二医院");
            tv_Search3.setText("江苏省第一人民医院");
            tv_Search4.setText("南京军区总医院");
            tv_Search5.setText("江苏省口腔医院");

        }else {
            et_Search.setHint("输入地区或者医院名称");
            //从服务器获取数据
            tv_Search1.setText("上海玛丽女子医院");
            tv_Search2.setText("武汉百佳医院");
            tv_Search3.setText("上海心脏病医院");
            tv_Search4.setText("青岛妇婴医院");
            tv_Search5.setText("天津丽人女子医院");
        }

    }
    /**
     * 初始化监听
     */
    private void initListener() {
        tv_cancel.setOnClickListener(this);
        et_Search.setOnClickListener(this);
        et_Search.setOnKeyListener(onKeyListener);

    }
    /**
     * 初始化控件
     */
    private void initView() {
        tv_Search1 = (TextView) findViewById(R.id.activity_fg_search_disease_tv1);
        tv_Search2 = (TextView) findViewById(R.id.activity_fg_search_disease_tv2);
        tv_Search3 = (TextView) findViewById(R.id.activity_fg_search_disease_tv3);
        tv_Search4 = (TextView) findViewById(R.id.activity_fg_search_disease_tv4);
        tv_Search5 = (TextView) findViewById(R.id.activity_fg_search_disease_tv5);
        tv_cancel = (TextView) findViewById(R.id.title_search_tv_cancel);
        et_Search = (EditText) findViewById(R.id.title_search_et_search);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_search_tv_cancel:
                finish();
//                overridePendingTransition(R.anim.alphaout,R.anim.alphain);
                break;
            case R.id.title_search_et_search:
                et_Search.setCursorVisible(true);
                break;
        }
    }
    /**
     * 输入法按下搜索的监听
     */
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                /*隐藏软键盘*/
                /**
                 * 本来应该从服务器获取数据,但是由于尚未与服务器进行连接,所以这里做的操作是显示listview.
                 */
                InputMethodManager inputMethodManager = (InputMethodManager) et_Search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }

                String name = et_Search.getText().toString();
                System.out.println("DATADATA:"+name);
                Intent intent_s = new Intent(SearchAllActivity.this, SearchResultLayout.class);
                intent_s.putExtra("kind",S_FLAG);//默认疾病
                intent_s.putExtra("name",name);
                startActivity(intent_s);
                return true;
            }
            return false;
        }
    };
}
