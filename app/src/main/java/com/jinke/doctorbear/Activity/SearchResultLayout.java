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
        medicineTextView = new TextView(this);;
        scienceTextView = new TextView(this);;
        questionTextView = new TextView(this);;

        setTextView(deseaseTextView,"相关疾病");
        setTextView(medicineTextView,"相关药品");
        setTextView(questionTextView,"相关问答");
        setTextView(scienceTextView,"相关科普");
        deseaseListView.setAdapter(new SimpleAdapter(this,getDeseaseData(),R.layout.search_result_disease_item,
                new String[] { "info"},
                new int[] { R.id.search_result_d_item_tv}));
        deseaseListView.setDivider(getResources().getDrawable(R.drawable.list_item_divider));
        deseaseListView.setDividerHeight(dip2px(this,(float)1.0));
        medicineListView.setAdapter(new SimpleAdapter(this,getMedicineData(),R.layout.search_result_medicine_item,
                new String[] { "info"},
                new int[] { R.id.search_result_m_item_tv}));

        scienceListView.setAdapter(new SimpleAdapter(this,getScienceData(),R.layout.search_result_science_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_s_item_tv,R.id.search_result_s_item_tag_tv}));

        questionListView.setAdapter(new SimpleAdapter(this,getQuestionData(),R.layout.search_result_question_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_q_item_tv,R.id.search_result_q_item_tag_tv}));

        //new ListViewUtil().setListViewHeightBasedOnChildren(deseaseListView);
        //Log.v(DEBUG_TAG,"listview hava ? >>>>>>>>>>>"+deseaseListView.getCount());
        setLayoutSort();

    }
    private void setLayoutSort() {
        Intent intent = getIntent();
        int kind = intent.getIntExtra("kind",0);
        if (kind == 1) {
            mainLayout.addView(medicineTextView);
            mainLayout.addView(medicineListView);
            mainLayout.addView(deseaseTextView);
            mainLayout.addView(deseaseListView);
            mainLayout.addView(questionTextView);
            mainLayout.addView(questionListView);
            mainLayout.addView(scienceTextView);
            mainLayout.addView(scienceListView);
        }else{
            mainLayout.addView(deseaseTextView);
            mainLayout.addView(deseaseListView);
            mainLayout.addView(medicineTextView);
            mainLayout.addView(medicineListView);
            mainLayout.addView(questionTextView);
            mainLayout.addView(questionListView);
            mainLayout.addView(scienceTextView);
            mainLayout.addView(scienceListView);
        }
    }

    public void setTextView(TextView textView,String context ) {
        textView.setText(context);
        textView.setTextSize(16);
        textView.setHeight(56);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dip2px(this,(float)12.00),0,0,0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
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
        map.put("info", "过敏测试测试测试");
        map.put("tag","精神科");
        scienceList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "感冒测定和");
        map.put("tag","精神科");
        scienceList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "发烧科普");
        map.put("tag","精神科");
        scienceList.add(map);

        return scienceList;
    }
    private List<Map<String, Object>> getQuestionData() {
        List<Map<String, Object>> questionList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", "过敏");
        map.put("tag","精神科");
        questionList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "感冒");
        map.put("tag","精神科");
        questionList.add(map);

        map = new HashMap<String, Object>();
        map.put("info", "发烧");
        map.put("tag","精神科");
        questionList.add(map);

        return questionList;
    }

}
