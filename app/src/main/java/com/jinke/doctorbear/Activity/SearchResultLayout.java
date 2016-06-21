package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.jinke.doctorbear.Bean.GeneralSearchBean;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.GetDataServer;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.jinke.doctorbear.Utils.ScrollListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wyf on 2016/5/19.
 */
public class SearchResultLayout  extends Activity{

    //GetDataServer getDataServer = new GetDataServer();
    int kind;

    private EditText et_Search;

    private LinearLayout mainLayout;
    private ScrollListView diseaseListView;
    private ScrollListView medicineListView;
    private ScrollListView scienceListView ;
    private ScrollListView questionListView ;
    private TextView diseaseTextView;
    private TextView medicineTextView;
    private TextView scienceTextView;
    private TextView questionTextView;

    private TextView  graybackgroud_tv0;
    private TextView  graybackgroud_tv1;
    private TextView  graybackgroud_tv2;


//从函数中提取数据定义
    private GeneralSearchBean generalSearchBean;
    private List<GeneralSearchBean.GeneralSearchPathema> generalSearchPathemas;
    private List<GeneralSearchBean.GeneralSearchMedicine> generalSearchMedicines;
    private List<GeneralSearchBean.GeneralSearchHospital> generalSearchHospitals;
    private List<GeneralSearchBean.GeneralSearchCommunity> generalSearchCommunities;
    private List<GeneralSearchBean.GeneralSearchArticle> generalSearchArticles;

    private List<Map<String, Object>> pathemaList = new ArrayList<Map<String, Object>>();
    //private List<Map<String, Object>> ArticleList = new ArrayList<Map<String, Object>>();
    //private List<Map<String, Object>> medicineList = new ArrayList<Map<String, Object>>();
    //private List<Map<String, Object>> communityList = new ArrayList<Map<String, Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_result);

        //初始化控件
        initView();

        // /初始化监听
        initSearchListener();

        //初始化数据
        setData();

        //addView排序加入布局文件
        setLayoutSort();

    }

    /**
     * 画xml文件
     */
    private void initView(){
        mainLayout = (LinearLayout) this.findViewById(R.id.search_result_layout);

        et_Search = (EditText) findViewById(R.id.title_search_et_search);

        diseaseListView = new ScrollListView(this);
        medicineListView = new ScrollListView(this);
        scienceListView = new ScrollListView(this);
        questionListView = new ScrollListView(this);

        diseaseTextView = new TextView(this);
        medicineTextView = new TextView(this);
        scienceTextView = new TextView(this);
        questionTextView = new TextView(this);

        setTextView(diseaseTextView,"相关疾病");
        setTextView(medicineTextView,"相关药品");
        setTextView(questionTextView,"相关问答");
        setTextView(scienceTextView,"相关科普");

        diseaseListView.setDivider(getResources().getDrawable(R.drawable.list_item_divider));
        diseaseListView.setDividerHeight(dip2px(this,(float)0.5));

        medicineListView.setDivider(getResources().getDrawable(R.drawable.list_item_divider));
        medicineListView.setDividerHeight(dip2px(this,(float)0.5));

        setgrayBack();//设置背景色
    }

    private void setData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        kind = intent.getIntExtra("kind", 0);//?

        String url = GlobalAddress.SERVER + "/doctuser/generalsearch.php?" + "content=" + name + "&tag=" + kind;
        et_Search.setText(name);
        et_Search.setEnabled(false);
        getGeneralSearch(this, url, diseaseListView, medicineListView, scienceListView, questionListView);
    }



    /**
     *初始化监听
     */
    private void initSearchListener(){

        diseaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultLayout.this, DiseaseDetail.class);
                intent.putExtra("PathemaID",generalSearchPathemas.get(position).PathemaID);
                startActivity(intent);
            }
        });

        medicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("--------"+deseaseListView.get(position).getCommunityID());
                //Intent intent = new Intent(SearchResultLayout.this, DiseaseDetail.class);
                //传递名称 以进入数据库
                //intent.putExtra("CommunityID", );
                //   intent.putExtra("PathemaID",generalSearchPathemas.get(position).PathemaID);

              //  startActivity(intent);
            }
        });
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
            mainLayout.addView(diseaseTextView);
            mainLayout.addView(diseaseListView);

            mainLayout.addView(graybackgroud_tv1);
            mainLayout.addView(questionTextView);
            mainLayout.addView(questionListView);

            mainLayout.addView(graybackgroud_tv2);
            mainLayout.addView(scienceTextView);
            mainLayout.addView(scienceListView);
        }else{
            mainLayout.addView(diseaseTextView);
            mainLayout.addView(diseaseListView);

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

    /**
     * 设置每个listview之间的灰色背景
     */
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

    /**
     * 综合搜索
     *
     */
    public void getGeneralSearch(final Context context, String url, final ListView deseaseListView, final ListView medicineListView,
                                 final ListView scienceListView, final ListView questionListView){

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseGeneralData(result,context,deseaseListView, medicineListView, scienceListView, questionListView);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
    }


    private String[] GeneralPathemaID = new String[20];
    private String[] GeneralPathemaName = new String[20];
    private String[] GeneralPathemaTypeName = new String[20];

    private String[] GeneralMedicineID = new String[20];
    private String[] GeneralMedicineName = new String[20];

    private String[] GeneralHospitalID = new String[20];
    private String[] GeneralHospitalName = new String[20];

    private String[] GeneralCommunityID = new String[20];
    private String[] GeneralCommunityTitle = new String[20];
    private String[] GeneralCommunityPathemaTypeName = new String[20];

    private String[] GeneralArticleID = new String[20];
    private String[] GeneralArticleTitle = new String[20];
    private String[] GeneralArticlePathemaTypeName = new String[20];

    private void   parseGeneralData(String result,Context context,ListView deseaseListView,ListView medicineListView,ListView scienceListView,ListView questionListView) {
      //  List<Map<String, Object>> pathemaList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> ArticleList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> medicineList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> communityList = new ArrayList<Map<String, Object>>();

        Gson gson = new Gson();
        generalSearchBean = gson.fromJson(result,GeneralSearchBean.class);
        generalSearchPathemas = generalSearchBean.Pathema;
        generalSearchMedicines = generalSearchBean.Medicine;
        generalSearchHospitals = generalSearchBean.Hospital;
        generalSearchCommunities = generalSearchBean.Community;
        generalSearchArticles = generalSearchBean.Article;

        Map<String, Object> pathemaListmap;
        if (generalSearchPathemas.size() != 0) {
            for (int i = 0; i < generalSearchPathemas.size(); i++) {
                GeneralPathemaID[i] = generalSearchPathemas.get(i).PathemaID;
                GeneralPathemaName[i] = generalSearchPathemas.get(i).PathemaName;
                GeneralPathemaTypeName[i] = generalSearchPathemas.get(i).PathemaTypeName;
                System.out.println("GeneralPathema：" + GeneralPathemaID[i] + GeneralPathemaName[i] + GeneralPathemaTypeName[i]);
            //}

            //for (int i = 0; i <generalSearchPathemas.size(); i++) {
            //    if (GeneralPathemaName.equals(null))
            //        continue;
                pathemaListmap = new HashMap<String, Object>();
                pathemaListmap.put("info", GeneralPathemaName[i]);
                pathemaList.add(pathemaListmap);
            }
        }
        if (generalSearchMedicines.size() != 0) {

            for (int i = 0; i < generalSearchMedicines.size(); i++) {
                GeneralMedicineID[i] = generalSearchMedicines.get(i).MedicineID;
                GeneralMedicineName[i] = generalSearchMedicines.get(i).MedicineName;
                System.out.println("GeneralMedicine" + GeneralMedicineID[i] + GeneralMedicineName[i]);
            }
            Map<String, Object> medicineListmap;
            for (int i = 0; i <generalSearchMedicines.size(); i++) {
                medicineListmap = new HashMap<String, Object>();
                medicineListmap.put("info", GeneralMedicineName[i]);
                medicineList.add(medicineListmap);
            }
        }
        if (generalSearchHospitals.size() != 0) {

            for (int i = 0; i <generalSearchHospitals.size() ; i++) {
                GeneralHospitalID[i] = generalSearchHospitals.get(i).HospitalID;
                GeneralHospitalName[i] = generalSearchHospitals.get(i).HospitalName;
                System.out.println("GeneralHospital" + GeneralHospitalID[i] + GeneralHospitalName[i]);


            }
        }
        if (generalSearchCommunities.size() != 0) {

            for (int i = 0; i < generalSearchCommunities.size(); i++) {
                GeneralCommunityID[i] = generalSearchCommunities.get(i).CommunityID;
                GeneralCommunityTitle[i] = generalSearchCommunities.get(i).CommunityTitle;
                GeneralCommunityPathemaTypeName[i] = generalSearchCommunities.get(i).PathemaTypeName;
                System.out.println("GeneralCommunity" + GeneralCommunityID[i] + GeneralCommunityTitle[i] + GeneralCommunityPathemaTypeName[i]);

            }
            Map<String, Object> communityListmap;
            for (int i = 0; i < generalSearchCommunities.size(); i++) {
                communityListmap = new HashMap<String, Object>();
                communityListmap.put("info", GeneralCommunityTitle[i]);
                communityListmap.put("tag", GeneralCommunityPathemaTypeName[i]);
                communityList.add(communityListmap);

            }
        }
        if (generalSearchArticles.size() != 0) {
            for (int i = 0; i < generalSearchArticles.size(); i++) {
                GeneralArticleID[i] = generalSearchArticles.get(i).ArticleID;
                GeneralArticleTitle[i] = generalSearchArticles.get(i).ArticleTitle;
                GeneralArticlePathemaTypeName[i] = generalSearchArticles.get(i).PathemaTypeName;
                System.out.println("GeneralArticle" + GeneralArticleID[i] + GeneralArticleTitle[i] + GeneralArticlePathemaTypeName[i]);

            }
            Map<String, Object> ArticleListmap;
            for (int i = 0; i < generalSearchArticles.size(); i++) {
                ArticleListmap = new HashMap<String, Object>();
                ArticleListmap.put("info", GeneralArticleTitle[i]);
                ArticleListmap.put("tag", GeneralArticlePathemaTypeName[i]);
                ArticleList.add(ArticleListmap);
            }
        }

        deseaseListView.setAdapter(new SimpleAdapter(context,pathemaList, R.layout.search_result_disease_item,
                new String[] { "info"},
                new int[] { R.id.search_result_d_item_tv}));
        medicineListView.setAdapter(new SimpleAdapter(context,medicineList,R.layout.search_result_medicine_item,
                new String[] { "info"},
                new int[] { R.id.search_result_m_item_tv}));
        scienceListView.setAdapter(new SimpleAdapter(context,ArticleList,R.layout.search_result_science_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_s_item_tv,R.id.search_result_s_item_tag_tv}));
        questionListView.setAdapter(new SimpleAdapter(context,communityList,R.layout.search_result_question_item,
                new String[] { "info","tag"},
                new int[] { R.id.search_result_q_item_tv,R.id.search_result_q_item_tag_tv}));

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
