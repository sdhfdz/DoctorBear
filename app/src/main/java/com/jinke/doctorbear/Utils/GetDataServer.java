package com.jinke.doctorbear.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinke.doctorbear.Adapter.AdpHomeFgAnswer;
import com.jinke.doctorbear.Adapter.AdpHomeFgExpert;
import com.jinke.doctorbear.Adapter.AdpHomeFgVpExpert;
import com.jinke.doctorbear.Bean.AnswerDetailBean;
import com.jinke.doctorbear.Bean.GeneralSearchBean;
import com.jinke.doctorbear.Bean.HomeAnswerBean;
import com.jinke.doctorbear.Bean.HomeAnswerValueBean;
import com.jinke.doctorbear.Bean.HomeExpertBean;
import com.jinke.doctorbear.Bean.HomeExpertPictureBean;
import com.jinke.doctorbear.Bean.HomeExpertValueBean;
import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.jinke.doctorbear.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Max on 2016/5/31.
 */
public class GetDataServer {

    DateUtils dateUtils = new DateUtils();
    ;
    /**
     * 从服务器获取问答数据
     */
    public void getAnswerFromServer(final Context context, final ArrayList<FgHomeAnswerModel> listAnswer, final ListView listView_answer, final AdpHomeFgAnswer adpHomeFgAnswer) {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalAddress.SERVER + "/doctuser/community_list.php?" + "sinceID=0", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseAnswerData(result, listAnswer, listView_answer, adpHomeFgAnswer);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });

    }
    /**
     * 从服务器获取问答数据
     */
    public void getAnswerFromServer(final Context context,String url, final ArrayList<FgHomeAnswerModel> listAnswer, final ListView listView_answer, final AdpHomeFgAnswer adpHomeFgAnswer) {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseAnswerData(result, listAnswer, listView_answer, adpHomeFgAnswer);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });

    }

    /**
     * 解析问答数据
     *
     * @param result
     */
    private HomeAnswerBean homeAnswerBean;
    private List<HomeAnswerValueBean> answerValue;



    private void parseAnswerData(String result, ArrayList<FgHomeAnswerModel> listAnswer, ListView listView_answer, AdpHomeFgAnswer adpHomeFgAnswer) {


//        System.out.println("---------------------" + result);
        Gson gson = new Gson();
        homeAnswerBean = gson.fromJson(result, HomeAnswerBean.class);
        answerValue = homeAnswerBean.getValue();
        String[] Picture = new String[30];
         String[] Iv_headImage = new String[30];
         String[] NickName = new String[30];
         String[] Comment = new String[30];
         String[] AnswerTitle = new String[30];
         String[] Illness = new String[30];
         String[] Time = new String[30];
         String[] AnswerContent = new String[30];
         String CommunityID[] = new String[10];
        for (int i = 0; i < answerValue.size(); i++) {
            Picture[i] = GlobalAddress.SERVER+answerValue.get(i).CommunityPic;
            Iv_headImage[i] = answerValue.get(i).User.getUserIcon();
            NickName[i] = answerValue.get(i).User.getUserName();
            Comment[i] = answerValue.get(i).getComm();
            AnswerTitle[i] = answerValue.get(i).getCommunityTitle();
            Illness[i] = answerValue.get(i).PathemaType.getPathemaTypeName();
            CommunityID[i] = answerValue.get(i).getCommunityID();
            Time[i] = dateUtils.getDateToString(Long.valueOf(answerValue.get(i).getCreateTime()).longValue());
            AnswerContent[i] = answerValue.get(i).getCommunityDesc();
            FgHomeAnswerModel fgHomeAnswerModel = new FgHomeAnswerModel(Iv_headImage[i], NickName[i], AnswerTitle[i], Time[i], AnswerContent[i],Illness[i], Comment[i], Picture[i], CommunityID[i]);
            listAnswer.add(fgHomeAnswerModel);
        }
        listView_answer.setAdapter(adpHomeFgAnswer);

        adpHomeFgAnswer.notifyDataSetChanged();
    }

    /**
     * 返回CommunityID
     */

    /**
     *  从服务器获取科普轮播图片数据
     */
    public void getExpertPictureFromServer(final Context context, final ArrayList<String> img_url, final AdpHomeFgVpExpert adpHomeFgVpExpert, final ViewPager viewPager_expert){

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalAddress.SERVER+"/doctuser/article_pagination.php", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseExpertPictureData(result,img_url,adpHomeFgVpExpert,viewPager_expert);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });


    }

    /**
     * 解析科普轮播图片数据
     * @param result
     */

    private HomeExpertPictureBean homeExpertPictureBean;
    private List<HomeExpertPictureBean.HomeExpertPictureValue> expertPictureValue;
    private void parseExpertPictureData(String result, ArrayList<String> img_url, AdpHomeFgVpExpert adpHomeFgVpExpert, ViewPager viewPager_expert) {

//        System.out.println("---------------------"+result);
        Gson gson = new Gson();
        homeExpertPictureBean = gson.fromJson(result,HomeExpertPictureBean.class);
        expertPictureValue = homeExpertPictureBean.getValues();

        for (int i = 0; i < expertPictureValue.size(); i++) {
            img_url.add (i,GlobalAddress.SERVER+expertPictureValue.get(i).getArticlePic());
//            img_id[i] =expertPictureValue.get(i).getArticleID();

        }
        adpHomeFgVpExpert.img_url = img_url;
        viewPager_expert.setAdapter(adpHomeFgVpExpert);
        adpHomeFgVpExpert.notifyDataSetChanged();

    }


    /**
     *  从服务器获取科普数据
     */
    public void getExpertFromServer(final Context context, final ArrayList<FgHomeExpertModel> listExpert, final ScrollListView listView_expert, final AdpHomeFgExpert adpHomeFgExpert){

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalAddress.SERVER+"/doctuser/article_list.php?"+"LastArticleID=0", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseExpertData(result,listExpert,listView_expert,adpHomeFgExpert);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });

    }

    /**
     *  从服务器获取科普数据
     */
    public void getExpertFromServer(final Context context, String url, final ArrayList<FgHomeExpertModel> listExpert, final ScrollListView listView_expert, final AdpHomeFgExpert adpHomeFgExpert){

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseExpertData(result,listExpert,listView_expert,adpHomeFgExpert);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });

    }
    /**
     *  从服务器获取科普数据
     */
    public void getExpertFromServer(final Context context, String url, final ArrayList<FgHomeExpertModel> listExpert, final ListView listView_expert, final AdpHomeFgExpert adpHomeFgExpert){

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseExpertData(result,listExpert,listView_expert,adpHomeFgExpert);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
    }


    private String[] ArticleTitile= new String[30];
    private String[] CreateTime = new String[30];
    private String[] Comm = new String[30];
    private String[] Likenum = new String[30];
    private String[] UserIcon = new String[30];
    private String[] UserName = new String[30];
    private String[] ArticlePic = new String[30];
    private String[] ArticleID = new String[30];

    private HomeExpertBean homeExperBean;
    private List<HomeExpertValueBean> homeExperValueBean;

    private void parseExpertData(String result,ArrayList<FgHomeExpertModel> listExpert,  ListView listView_expert,  AdpHomeFgExpert adpHomeFgExper) {

//        System.out.println(result);
        Gson gson = new Gson();
        homeExperBean = gson.fromJson(result,HomeExpertBean.class);
        homeExperValueBean = homeExperBean.getValue();

        for (int i = 0; i < homeExperValueBean.size(); i++) {
            UserIcon[i] = homeExperValueBean.get(i).User.getUserIcon();
            UserName[i] = homeExperValueBean.get(i).User.getUserName();
            ArticleTitile[i] = homeExperValueBean.get(i).getArticleTitle();
            Likenum[i] = homeExperValueBean.get(i).getLikenum();
            ArticlePic[i] = GlobalAddress.SERVER + homeExperValueBean.get(i).getArticlePic();
            Comm[i] = homeExperValueBean.get(i).getComm();
            CreateTime[i] = dateUtils.getDateToString(Long.valueOf(homeExperValueBean.get(i).getCreateTime()).longValue());
            ArticleID[i] = homeExperValueBean.get(i).getArticleID();

//            System.out.println("ArticleTitile+++++"+ArticleTitile[i]);
//            System.out.println("hahahaha"+UserIcon[i]+" "+UserName[i]+" "+CreateTime[i]+" "+ArticleTitile[i]+" "+ArticlePic[i]+" "+Likenum[i]+" "+Comm[i]);
            FgHomeExpertModel fgHomeExpertModel = new FgHomeExpertModel(UserIcon[i],UserName[i],CreateTime[i],ArticleTitile[i],ArticlePic[i],Likenum[i],Comm[i], ArticleID[i]);
            listExpert.add(fgHomeExpertModel);
        }
        listView_expert.setAdapter(adpHomeFgExper);

        adpHomeFgExper.notifyDataSetChanged();
    }



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

    private GeneralSearchBean generalSearchBean;
    private List<GeneralSearchBean.GeneralSearchPathema> generalSearchPathemas;
    private List<GeneralSearchBean.GeneralSearchMedicine> generalSearchMedicines;
    private List<GeneralSearchBean.GeneralSearchHospital> generalSearchHospitals;
    private List<GeneralSearchBean.GeneralSearchCommunity> generalSearchCommunities;
    private List<GeneralSearchBean.GeneralSearchArticle> generalSearchArticles;
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
//        System.out.println(result);
        List<Map<String, Object>> pathemaList = new ArrayList<Map<String, Object>>();
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

        if (generalSearchPathemas.size() != 0) {
            for (int i = 0; i < generalSearchPathemas.size(); i++) {
                GeneralPathemaID[i] = generalSearchPathemas.get(i).PathemaID;
                GeneralPathemaName[i] = generalSearchPathemas.get(i).PathemaName;
                GeneralPathemaTypeName[i] = generalSearchPathemas.get(i).PathemaTypeName;
                System.out.println("GeneralPathema" + GeneralPathemaID[i] + GeneralPathemaName[i] + GeneralPathemaTypeName[i]);
            }
            Map<String, Object> pathemaListmap;
            for (int i = 0; i <generalSearchPathemas.size(); i++) {
                if (GeneralPathemaName.equals(null))
                    continue;

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
