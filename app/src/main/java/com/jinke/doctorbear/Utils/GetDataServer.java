package com.jinke.doctorbear.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinke.doctorbear.Adapter.AdpHomeFgAnswer;
import com.jinke.doctorbear.Adapter.AdpHomeFgExpert;
import com.jinke.doctorbear.Adapter.AdpHomeFgVpExpert;
import com.jinke.doctorbear.Bean.HomeAnswerBean;
import com.jinke.doctorbear.Bean.HomeAnswerValueBean;
import com.jinke.doctorbear.Bean.HomeExpertBean;
import com.jinke.doctorbear.Bean.HomeExpertPictureBean;
import com.jinke.doctorbear.Bean.HomeExpertValueBean;
import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

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
    private String[] Iv_headImage = new String[30];
    private String[] NickName = new String[30];
    private String[] Comment = new String[30];
    private String[] AnswerTitle = new String[30];
    private String[] Illness = new String[30];
    private String[] Time = new String[30];
    private String[] AnswerContent = new String[30];

    private void parseAnswerData(String result, ArrayList<FgHomeAnswerModel> listAnswer, ListView listView_answer, AdpHomeFgAnswer adpHomeFgAnswer) {

        System.out.println("---------------------" + result);
        Gson gson = new Gson();
        homeAnswerBean = gson.fromJson(result, HomeAnswerBean.class);
        answerValue = homeAnswerBean.getValue();

        for (int i = 0; i < answerValue.size(); i++) {
            Iv_headImage[i] = answerValue.get(i).User.getUserIcon();
            NickName[i] = answerValue.get(i).User.getUserName();
            Comment[i] = answerValue.get(i).getComm();
            AnswerTitle[i] = answerValue.get(i).getCommunityTitle();
            Illness[i] = answerValue.get(i).PathemaType.getPathemaTypeName();
            Time[i] = dateUtils.getDateToString(Long.valueOf(answerValue.get(i).getCreateTime()).longValue());
            AnswerContent[i] = answerValue.get(i).getCommunityDesc();
            FgHomeAnswerModel fgHomeAnswerModel = new FgHomeAnswerModel(Iv_headImage[i], NickName[i], AnswerTitle[i], Time[i], AnswerContent[i],Illness[i], Comment[i] );
            listAnswer.add(fgHomeAnswerModel);
        }
        listView_answer.setAdapter(adpHomeFgAnswer);

        adpHomeFgAnswer.notifyDataSetChanged();
    }


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

        System.out.println("---------------------"+result);
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
    public void getExpertFromServer(final Context context, final ArrayList<FgHomeExpertModel> listExpert, final ListView listView_expert, final AdpHomeFgExpert adpHomeFgExpert){

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

    /**
     * 解析科普数据
     * @param result
     */

    private String[] ArticleTitile= new String[30];
    private String[] CreateTime = new String[30];
    private String[] Comm = new String[30];
    private String[] Likenum = new String[30];
    private String[] UserIcon = new String[30];
    private String[] UserName = new String[30];
    private String[] ArticlePic = new String[30];

    private HomeExpertBean homeExperBean;
    private List<HomeExpertValueBean> homeExperValueBean;

    private void parseExpertData(String result,ArrayList<FgHomeExpertModel> listExpert,  ListView listView_expert,  AdpHomeFgExpert adpHomeFgExper) {

        System.out.println(result);
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

//            System.out.println("ArticleTitile+++++"+ArticleTitile[i]);
            System.out.println("hahahaha"+UserIcon[i]+" "+UserName[i]+" "+CreateTime[i]+" "+ArticleTitile[i]+" "+ArticlePic[i]+" "+Likenum[i]+" "+Comm[i]);
            FgHomeExpertModel fgHomeExpertModel = new FgHomeExpertModel(UserIcon[i],UserName[i],CreateTime[i],ArticleTitile[i],ArticlePic[i],Likenum[i],Comm[i]);
            listExpert.add(fgHomeExpertModel);
        }
        listView_expert.setAdapter(adpHomeFgExper);

        adpHomeFgExper.notifyDataSetChanged();
    }


    /**
     * 综合搜索
     *
     */
    public void getGeneralSearch(final Context context){
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalAddress.SERVER+"/doctuser/generalsearch.php", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
    }
}
