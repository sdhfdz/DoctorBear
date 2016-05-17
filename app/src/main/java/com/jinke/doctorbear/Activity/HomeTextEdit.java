package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.NiceSpinner;

/**
 * Created by wyf on 2016/5/17.
 * 连接home_textedit.xml即主页跳转提问页面
 */
public class HomeTextEdit extends Activity {
    private static final String[] diseasesType={"精神","普外科","骨科","眼科","耳鼻喉科"};
    private TextView cancel_textV ;
    private TextView  submit_textV;
    private TextView  count_textV;
    private EditText title_editT;
    private EditText main_editT;
    private NiceSpinner spinner;
    private ImageView add_imageV;
    private ImageView set_imageV;
    private ImageView keyboard_imageV;

    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_edit);

        spinner = (NiceSpinner) findViewById(R.id.home_edit_spinner);
        cancel_textV = (TextView)findViewById(R.id.home_edit_cancel_tv) ;
        submit_textV = (TextView)findViewById(R.id.home_edit_submit_tv);
        count_textV = (TextView)findViewById(R.id.home_edit_titleBar_count_tv);

        title_editT = (EditText)findViewById(R.id.home_edit_title_editText);
        main_editT = (EditText)findViewById(R.id.home_edit_main_editText);

        add_imageV =(ImageView)findViewById(R.id.home_edit_add_iV) ;
        set_imageV = (ImageView)findViewById(R.id.home_edit_set_iV);
        keyboard_imageV = (ImageView)findViewById(R.id.home_edit_keybroad_iV);

        //将可选内容与ArrayAdapter连接起来
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, diseasesType);

        //设置下拉列表的风格
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(arrayAdapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            title_editT.setText("你的血型是："+diseasesType[arg2]);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }



}
