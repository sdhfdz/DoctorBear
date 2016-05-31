package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.ScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * Created by wyf on 2016/5/19.
 */
public class SearchResultLayout  extends Activity{


    private LinearLayout mainLayout;
    private ScrollListView deseaseListView ;
    private ScrollListView medicineListView;
    private ScrollListView scienceListView ;
    private ScrollListView questionListView ;
    private TextView deseaseTextView;
    private TextView medicineTextView;
    private TextView scienceTextView;
    private TextView questionTextView;

    private TextView  graybackgroud_tv0;
    private TextView  graybackgroud_tv1;
    private TextView  graybackgroud_tv2;

    protected static final String DEBUG_TAG="debug_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_result);
        initView();
    }

    private void initView(){
        mainLayout = (LinearLayout) this.findViewById(R.id.search_result_layout);

        deseaseListView = new ScrollListView(this);
        medicineListView = new ScrollListView(this);
        scienceListView = new ScrollListView(this);
        questionListView = new ScrollListView(this);

        deseaseTextView = new TextView(this);
        medicineTextView = new TextView(this);
        scienceTextView = new TextView(this);
        questionTextView = new TextView(this);

        setTextView(deseaseTextView,"相关疾病");
        setTextView(medicineTextView,"相关药品");
        setTextView(questionTextView,"相关问答");
        setTextView(scienceTextView,"相关科普");


        deseaseListView.setAdapter(new SimpleAdapter(this,getDeseaseData(),R.layout.search_result_disease_item,
                new String[] { "info"},
                new int[] { R.id.search_result_d_item_tv}));
        deseaseListView.setDivider(getResources().getDrawable(R.drawable.list_item_divider));
        deseaseListView.setDividerHeight(dip2px(this,(float)0.5));

        medicineListView.setAdapter(new SimpleAdapter(this,getMedicineData(),R.layout.search_result_medicine_item,
                new String[] { "info"},
                new int[] { R.id.search_result_m_item_tv}));
        medicineListView.setDivider(getResources().getDrawable(R.drawable.list_item_divider));
        medicineListView.setDividerHeight(dip2px(this,(float)0.5));
        scienceListView.setAdapter(new SimpleAdapter(this,getScienceData(),R.layout.search_result_science_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_s_item_tv,R.id.search_result_s_item_tag_tv}));

        questionListView.setAdapter(new SimpleAdapter(this,getQuestionData(),R.layout.search_result_question_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_q_item_tv,R.id.search_result_q_item_tag_tv}));

        setgrayBack();
        setLayoutSort();
    }

    /**
     * 将控件放入页面中
     */
    private void setLayoutSort() {
        Intent intent = getIntent();
        int kind = intent.getIntExtra("kind",0);
        if (kind == 1) {
            mainLayout.addView(medicineTextView);
            mainLayout.addView(medicineListView);

            mainLayout.addView(graybackgroud_tv0);
            mainLayout.addView(deseaseTextView);
            mainLayout.addView(deseaseListView);

            mainLayout.addView(graybackgroud_tv1);
            mainLayout.addView(questionTextView);
            mainLayout.addView(questionListView);

            mainLayout.addView(graybackgroud_tv2);
            mainLayout.addView(scienceTextView);
            mainLayout.addView(scienceListView);
        }else{
            mainLayout.addView(deseaseTextView);
            mainLayout.addView(deseaseListView);

            mainLayout.addView(graybackgroud_tv0);
            mainLayout.addView(medicineTextView);
            mainLayout.addView(medicineListView);

            mainLayout.addView(graybackgroud_tv1);
            mainLayout.addView(questionTextView);
            mainLayout.addView(questionListView);

            mainLayout.addView(graybackgroud_tv2);
            mainLayout.addView(scienceTextView);
            mainLayout.addView(scienceListView);
        }
    }

    /**
     * 设置每个分类的的标题内容，以及字体大小等
     * @param textView
     * @param context
     */
    private void setTextView(TextView textView,String context ) {
        textView.setText(context);
        textView.setTextSize(16);
        textView.setHeight(100);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dip2px(this,(float)12.00),0,0,0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
    }
    private void setgrayBack()
    {
        graybackgroud_tv0 = new TextView(this);
        graybackgroud_tv1 = new TextView(this);
        graybackgroud_tv2 = new TextView(this);

        graybackgroud_tv0.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        graybackgroud_tv1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        graybackgroud_tv2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        graybackgroud_tv0.setLayoutParams(layoutParams);
        graybackgroud_tv1.setLayoutParams(layoutParams);
        graybackgroud_tv2.setLayoutParams(layoutParams);

    }

    private List<Map<String, Object>> getDeseaseData() {
        List<Map<String, Object>> deseaseList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", "过敏");
        deseaseList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "感冒");
        deseaseList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "发烧");
        deseaseList.add(map);

        return deseaseList;
    }
    private List<Map<String, Object>> getMedicineData() {
        List<Map<String, Object>> medicineList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", "999牌感冒药");
        medicineList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "感康");
        medicineList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "胃泰");
        medicineList.add(map);

        return medicineList;
    }
    private List<Map<String, Object>> getScienceData() {
        List<Map<String, Object>> scienceList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", "感冒药能预防感冒吗？");
        map.put("tag","呼吸科");
        scienceList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "一篇文章教你整个孕期都睡上安稳觉");
        map.put("tag","妇产科");
        scienceList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "飞机升降时保护耳朵的简单方法");
        map.put("tag","耳鼻咽喉科");
        scienceList.add(map);

        return scienceList;
    }
    private List<Map<String, Object>> getQuestionData() {
        List<Map<String, Object>> questionList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", "请问脸上花粉过敏该怎么办？");
        map.put("tag","皮肤科");
        questionList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "挂吊瓶对感冒来说有必要吗？");
        map.put("tag","呼吸科");
        questionList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "请问，体检需要多久一次？");
        map.put("tag","综合");
        questionList.add(map);

        return questionList;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
