package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.doctorbear.Adapter.AdpHomeFgAnswer;
import com.jinke.doctorbear.Adapter.AdpHomeFgExpert;
import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.jinke.doctorbear.R;

import java.util.List;

/**
 * FragmentHome 里的搜索页面.
 * 未完成问题:连接后台搜索
 * Created by Max on 2016/5/17.
 */
public class FgHomeSearchActivity extends Activity implements View.OnClickListener{
    private TextView tv_cancel;
    private EditText et_search;
    private ListView lv_search;
    List <FgHomeExpertModel> listExpert;
    List <FgHomeAnswerModel> listAnswer;
    SharedPreferences sp;
    private String name;


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
     * 初始化搜索框内的文字数据,和viewpage的页面内容
     */
    private void initData() {

        sp = getSharedPreferences("info", Context.MODE_PRIVATE);
        boolean page = sp.getBoolean("page",true);
        if (page){
            et_search.setHint("搜索<问答>相关内容");
            lv_search.setAdapter(new AdpHomeFgAnswer(this,listAnswer,false));
            lv_search.setVisibility(View.INVISIBLE);

        }else {
            et_search.setHint("搜索<科普>相关内容");
            lv_search.setAdapter(new AdpHomeFgExpert(this,listExpert,false));
            lv_search.setVisibility(View.INVISIBLE);

        }

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        tv_cancel.setOnClickListener(this);
        et_search.setOnClickListener(this);
        et_search.setOnKeyListener(onKeyListener);


        //单个listviewitem的点击事件
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_cancel = (TextView) findViewById(R.id.activity_fg_home_search_tv_cancel);
        et_search = (EditText) findViewById(R.id.activity_fg_home_search_et_search);
        lv_search = (ListView) findViewById(R.id.activity_fg_home_search_lv);
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
                lv_search.setVisibility(View.VISIBLE);
                InputMethodManager inputMethodManager = (InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
//                search_name.clear();
//                search_id.clear();
//                search_price.clear();
//                search_picUrl.clear();
//                search_number.clear();
                //pb_progress.setVisibility(View.VISIBLE);
//                name = et_search.getText().toString();
//                System.out.println("DATADATA:"+name);
//                search_detail_url = DownloadUrl.search_url + name;
//                if (search_detail_url!=null){
//                    getDataFromServer();
//                }


                return true;
            }
            return false;
        }
    };

}
