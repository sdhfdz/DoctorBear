package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinke.doctorbear.Adapter.AdpAnswerDetailComment;
import com.jinke.doctorbear.Adapter.AdpHomeFgAnswer;
import com.jinke.doctorbear.Bean.AnswerDeatilValueBean;
import com.jinke.doctorbear.Bean.AnswerDetailBean;
import com.jinke.doctorbear.Bean.AnswerDetailConcerned;
import com.jinke.doctorbear.Model.AnswerDetailModel;
import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.DateUtils;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.jinke.doctorbear.Utils.ScrollListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 问答详情页面
 * Created by Max on 2016/5/31.
 */
public class AnswerDetailActivity extends Activity {

    final static String TAG = AnswerDetailActivity.class.getName();
    private String UserID = null;
    private String Sub = null;
    private String Fav = null;
    private String id = null;
    private ScrollListView lv_doctorAnswer;
    private ScrollListView lv_comment;
    private ScrollListView lv_concerned;

    private ArrayList<FgHomeAnswerModel> listconcerned = new ArrayList<>();
    private ArrayList<AnswerDetailModel> listcomment = new ArrayList<>();
    private ArrayList<AnswerDetailModel> listDoctorComment = new ArrayList<>();

    private ImageView iv_back;
    private ImageView iv_articalFav;
    private ImageView iv_urlContent;

    private TextView tv_pathemaType;
    private TextView tv_subcribelist;
    private TextView tv_articalTitle;
    private TextView tv_pathemaType2;
    private TextView tv_articalTime;
    private TextView tv_content;

    private LinearLayout layout_like;
    private LinearLayout layout_comment;
    private LinearLayout layout_report;

    private TextView tv_like;
    private TextView tv_comment;
    private TextView tv_report;

    DateUtils dateUtils = new DateUtils();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);

        init();
    }

    private void init() {
        initView();
        initData();
        initListener();

    }

    private void initData() {
        UserID = GlobalAddress.getUserId(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("CommunityID");
        System.out.println("id +++:" + id);


//        String url = GlobalAddress.SERVER + "/doctuser/community_detail.php?" + "CommunityID=" + id + "&UserID=" + UserID;
//        getAnswerDetail(this, url);
//        reqGlobalAddress();
    }

    private void reqGlobalAddress(){
        String url = GlobalAddress.SERVER + "/doctuser/community_detail.php?" + "CommunityID=" + id + "&UserID=" + UserID;
        getAnswerDetail(this, url);
    }

    private void initListener() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点赞成功", Toast.LENGTH_LONG).show();
            }
        });
//        评论按钮的监听事件
        layout_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnswerDetailActivity.this, PushCommentActivity.class);
                int kind = 0;
                intent.putExtra("kind", kind);
                Log.i(TAG, "UserID  " + UserID);
                intent.putExtra("UserID", UserID);
                intent.putExtra("CommunityID", id);
                startActivity(intent);
            }
        });
        layout_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "转发成功", Toast.LENGTH_LONG).show();
            }
        });

//        收藏按钮的监听事件
        iv_articalFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Fav == null)
                    Fav = "0";
                if (Fav.equals("1")) {
                    iv_articalFav.setImageResource(R.mipmap.artical_fav);
                    Toast.makeText(getApplicationContext(), "取消收藏", Toast.LENGTH_LONG).show();
                    Fav = "0";

                } else {
                    iv_articalFav.setImageResource(R.mipmap.artical_fav_on);
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();
                    Fav = "1";
                }
                HttpUtils http = new HttpUtils();
                http.configCurrentHttpCacheExpiry(1);
                RequestParams params = new RequestParams();

                params.addBodyParameter("CommunityID", id);
                params.addBodyParameter("UserID", UserID);
                params.addBodyParameter("Fav", Fav);
                Log.e("CommunityIDUserIDFav",id+" "+UserID+" " +Fav);

                http.send(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER + "/doctuser/setfavourite.php", params, new MyrequestCallBack());


            }
        });
        tv_subcribelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Sub.equals("1")) {
                    iv_articalFav.setImageResource(R.mipmap.artical_fav);
                    Toast.makeText(getApplicationContext(), "取消订阅", Toast.LENGTH_LONG).show();
                    Sub = "0";

                } else {
                    iv_articalFav.setImageResource(R.mipmap.artical_fav_on);
                    Toast.makeText(getApplicationContext(), "订阅成功", Toast.LENGTH_LONG).show();
                    Sub = "1";
                }

                HttpUtils http = new HttpUtils();
                http.configCurrentHttpCacheExpiry(1);
                RequestParams params = new RequestParams();

                params.addBodyParameter("PathemaTypeID",tv_pathemaType.getText().toString());
                params.addBodyParameter("UserID",UserID);
                params.addBodyParameter("sub", Sub);

                try {
                    http.sendSync(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER+"/doctuser/setsubscribe.php",params);
                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        });

        lv_concerned.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("--------"+listconcerned.get(position).getCommunityID());
                Intent intent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                intent.putExtra("CommunityID",listconcerned.get(position).getCommunityID());
                startActivity(intent);
            }
        });

    }

    private void initView() {

        lv_comment = (ScrollListView) findViewById(R.id.activity_answer_detail_lv_comment);
        lv_doctorAnswer = (ScrollListView) findViewById(R.id.activity_answer_detail_lv_doctorAnswer);
        lv_concerned = (ScrollListView) findViewById(R.id.activity_answer_detail_lv_concerned);

        iv_back = (ImageView) findViewById(R.id.activity_answer_detail_iv_title_bar_back);
        iv_articalFav = (ImageView) findViewById(R.id.activity_answer_detail_iv_artical_fav);
        iv_urlContent = (ImageView) findViewById(R.id.activity_answer_detail_iv_content);

        tv_articalTime = (TextView) findViewById(R.id.activity_answer_detail_tv_articalTime);
        tv_pathemaType = (TextView) findViewById(R.id.activity_answer_detail_tv_pathemaType);
        tv_subcribelist = (TextView) findViewById(R.id.activity_answer_detail_tv_subcribelist);
        tv_pathemaType2 = (TextView) findViewById(R.id.activity_answer_detail_lv_tv_illness);
        tv_articalTitle = (TextView) findViewById(R.id.activity_answer_detail_tv_articalTitle);
        tv_content = (TextView) findViewById(R.id.activity_answer_detail_tv_content);

        layout_like = (LinearLayout) findViewById(R.id.activity_answer_detail_layout_social_like);
        layout_comment = (LinearLayout) findViewById(R.id.activity_answer_detail_layout_social_comment);
        layout_report = (LinearLayout) findViewById(R.id.activity_answer_detail_layout_social_report);

        tv_like = (TextView) findViewById(R.id.activity_answer_detail_tv_social_like);
        tv_comment = (TextView) findViewById(R.id.activity_answer_detail_tv_social_comment);
        tv_report = (TextView) findViewById(R.id.activity_answer_detail_tv_social_report);
    }

    /**
     * 问答详情
     */
    public void getAnswerDetail(final Context context, String url) {
        HttpUtils utils = new HttpUtils();
        Log.d(TAG,"getAnswerDetail");
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseAnswerDetailData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
    }

    /**
     * 解析问答详情页面数据
     *
     * @param result
     */
    private void parseAnswerDetailData(String result) {
        AnswerDetailBean answerDetailBean;
        AnswerDeatilValueBean answerDetailBeanValue;
        List<AnswerDetailConcerned> answerDetailConcerneds;
        List<AnswerDetailBean.AnswerDetailComment> answerDetailComments;
//        System.out.println("AnswerDeatile==========================" + result);
        Gson gson = new Gson();
        answerDetailBean = gson.fromJson(result, AnswerDetailBean.class);
        answerDetailBeanValue = answerDetailBean.value;
        answerDetailConcerneds = answerDetailBeanValue.concerned;
        answerDetailComments = answerDetailBean.Comment;


        tv_pathemaType.setText(answerDetailBeanValue.PathemaType.PathemaTypeName);
        tv_pathemaType2.setText(answerDetailBeanValue.PathemaType.PathemaTypeName);
        Sub = answerDetailBeanValue.sub;
        if (Sub == null) {
            Sub = "0";
        }
        if ((Fav = answerDetailBeanValue.fav) != null) {
            if (Fav.equals("1")) {
                iv_articalFav.setImageResource(R.mipmap.artical_fav_on);
            }
        } else {
            Fav = "0";
        }
        tv_articalTitle.setText(answerDetailBeanValue.CommunityTitle);
        if (answerDetailBeanValue.CommunityPic.equals("null")) {
            iv_urlContent.setVisibility(View.GONE);
        } else {
            Picasso.with(this).load(GlobalAddress.SERVER + answerDetailBeanValue.CommunityPic).into(iv_urlContent);
        }
        tv_content.setText(answerDetailBeanValue.CommunityDesc);
        tv_articalTime.setText(dateUtils.getDateToString(Long.parseLong(answerDetailBeanValue.CreateTime)));
        tv_like.setText(answerDetailBeanValue.Likenum);
        tv_comment.setText(answerDetailBeanValue.Comm);
        tv_report.setText(answerDetailBeanValue.Send);


//        评论数据
        String[] commentNickName = new String[30];
        String[] commentTime = new String[30];
        String[] commentImge = new String[30];
        String[] commentContent = new String[30];
        listcomment.clear();
        listDoctorComment.clear();
        for (int i = 0; i < answerDetailComments.size(); i++) {
            commentContent[i] = answerDetailComments.get(i).Content;
            commentImge[i] = answerDetailComments.get(i).User.UserIcon;
            commentNickName[i] = answerDetailComments.get(i).User.UserName;
            commentTime[i] = dateUtils.getDateToString(Long.parseLong(answerDetailComments.get(i).CreateTime));
            AnswerDetailModel answerDetailModel = new AnswerDetailModel(commentImge[i], commentNickName[i], commentTime[i], commentContent[i]);
            Log.d(TAG,commentContent[i]);
            if (answerDetailComments.get(i).User.UserTypeID.equals("2")) {
                Log.i(TAG, "here!!!!!!!!!!");
                listDoctorComment.add(answerDetailModel);
            } else if (answerDetailComments.get(i).User.UserTypeID.equals("1")) {
                listcomment.add(answerDetailModel);
            } else {
                listcomment.add(answerDetailModel);
                listDoctorComment.add(answerDetailModel);
            }
        }
        lv_comment.setAdapter(new AdpAnswerDetailComment(this, listcomment));
        lv_doctorAnswer.setAdapter(new AdpAnswerDetailComment(this, listDoctorComment));

        //        相关问答的数据
        String[] Iv_headImage = new String[30];
        String[] NickName = new String[30];
        String[] Comment = new String[30];
        String[] AnswerTitle = new String[30];
        String[] Illness = new String[30];
        String[] Time = new String[30];
        String[] AnswerContent = new String[30];
        String[] CommunityID = new String[30];
        String[] Picture = new String[30];
        listconcerned.clear();
        for (int i = 0; i < answerDetailConcerneds.size(); i++) {
            Picture[i] = GlobalAddress.SERVER + answerDetailConcerneds.get(i).getCommunityPic();
            Iv_headImage[i] = answerDetailConcerneds.get(i).User.getUserIcon();
            NickName[i] = answerDetailConcerneds.get(i).User.getUserName();
            Comment[i] = answerDetailConcerneds.get(i).getComm();
            AnswerTitle[i] = answerDetailConcerneds.get(i).getCommunityTitle();
            Illness[i] = answerDetailConcerneds.get(i).PathemaType.getPathemaTypeName();
            CommunityID[i] = answerDetailConcerneds.get(i).getCommunityID();
            Time[i] = dateUtils.getDateToString(Long.valueOf(answerDetailConcerneds.get(i).getCreateTime()).longValue());
            AnswerContent[i] = answerDetailConcerneds.get(i).getCommunityDesc();
            Log.e(TAG,Illness[i]+Comment[i]);
            FgHomeAnswerModel fgHomeAnswerModel = new FgHomeAnswerModel(Iv_headImage[i], NickName[i], AnswerTitle[i], Time[i], AnswerContent[i], Illness[i], Comment[i], Picture[i], CommunityID[i]);
            listconcerned.add(fgHomeAnswerModel);
        }
        lv_concerned.setAdapter(new AdpHomeFgAnswer(this, listconcerned));

    }

    protected void onResume() {
        super.onResume();
//        onCreate(null);
//        initData();
        Log.d(TAG,"onResume");
        reqGlobalAddress();
    }

    class MyrequestCallBack extends RequestCallBack {
        @Override
        public void onSuccess(ResponseInfo responseInfo) {
            JSONObject jsonObject= null;
            try {
                jsonObject = new JSONObject(responseInfo.result.toString());
                String result=jsonObject.getString("sql");
                Log.e("error!!!!!!!!!!!!!!",result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e(TAG, "操作成功");
        }

        @Override
        public void onFailure(HttpException e, String s) {
            System.out.println(s + "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        }
    }
}
