package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jinke.doctorbear.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 综合搜索页面
 * Created by wyf on 2016/5/19.
 */
public class SearchResultActivity extends Activity{


    private LinearLayout mainLayout;
    private ListView deseaseListView ;
    private ListView medicineListView;
    private ListView scienceListView ;
    private ListView questionListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_result);
        initView();
    }

    private void initView(){
        mainLayout = (LinearLayout) this.findViewById(R.id.search_result_layout);

         deseaseListView = new ListView(this);
         medicineListView = new ListView(this);
          scienceListView = new ListView(this);
         questionListView = new ListView(this);
        deseaseListView.setAdapter(new SimpleAdapter(this,getDeseaseData(),R.layout.search_result_disease_item,
                new String[] { "info"},
                new int[] { R.id.search_result_d_item_tv}));
        medicineListView.setAdapter(new SimpleAdapter(this,getMedicineData(),R.layout.search_result_medicine_item,
                new String[] { "info"},
                new int[] { R.id.search_result_m_item_tv}));
        scienceListView.setAdapter(new SimpleAdapter(this,getScienceData(),R.layout.search_result_science_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_s_item_tv,R.id.search_result_s_item_tag_tv}));
        questionListView.setAdapter(new SimpleAdapter(this,getQuestionData(),R.layout.search_result_question_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_q_item_tv,R.id.search_result_q_item_tag_tv}));

        setLayoutSort();

    }
    private void setLayoutSort() {
        Intent intent = getIntent();
        int kind = intent.getIntExtra("kind",0);
        if (kind == 1) {
            mainLayout.addView(medicineListView);
            mainLayout.addView(deseaseListView);
            mainLayout.addView(scienceListView);
            mainLayout.addView(questionListView);
        }else{
                mainLayout.addView(deseaseListView);
                mainLayout.addView(medicineListView);
                mainLayout.addView(scienceListView);
                mainLayout.addView(questionListView);
        }
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
