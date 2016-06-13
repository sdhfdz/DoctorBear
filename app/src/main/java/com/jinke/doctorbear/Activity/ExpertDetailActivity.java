package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinke.doctorbear.Adapter.AdpAnswerDetailComment;
import com.jinke.doctorbear.Adapter.AdpHomeFgExpert;
import com.jinke.doctorbear.Bean.ExpertDetailBean;
import com.jinke.doctorbear.Bean.ExpertDetailConcerned;
import com.jinke.doctorbear.Bean.ExpertDetailValueBean;
import com.jinke.doctorbear.Model.AnswerDetailModel;
import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.CircleImageView;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2016/6/12.
 */
public class ExpertDetailActivity extends Activity {

    final static String TAG = ExpertDetailActivity.class.getName();
    private String UserID = null;
    private String id = null;
    private String Sub = null;
    private String Fav = null;
    private ScrollListView lv_comment;
    private ScrollListView lv_concerned;

    private ArrayList<FgHomeExpertModel> listconcerned = new ArrayList<>();
    private ArrayList<AnswerDetailModel> listcomment = new ArrayList<>();

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
    private TextView authorName;
    private CircleImageView authorIcon;
    private TextView authorDetail;


    GlobalAddress globalAddress = new GlobalAddress();
    DateUtils dateUtils = new DateUtils();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_detail);

        init();
    }

    private void init() {
        initView();
        initData();
        initListener();

    }

    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("ArticleID");
        System.out.println("id +++:" + id);
        String url = GlobalAddress.SERVER + "/doctuser/article_detail.php?" + "ArticleID=" + id ;
        getExpertDetail(this, url);


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
                Toast.makeText(getApplicationContext(),"点赞成功",Toast.LENGTH_LONG).show();
            }
        });
        layout_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExpertDetailActivity.this, PushCommentActivity.class);
                int kind = 1;
                intent.putExtra("kind", kind);
                Log.i(TAG, "UserID  " + UserID);
                intent.putExtra("UserID", UserID);
                intent.putExtra("ArticleID", id);
                startActivity(intent);
            }
        });
        layout_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"转发成功",Toast.LENGTH_LONG).show();
            }
        });

        iv_articalFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Fav.equals("1")) {
                    iv_articalFav.setImageResource(R.mipmap.artical_fav);
                    Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_LONG).show();

                }else {
                    iv_articalFav.setImageResource(R.mipmap.artical_fav_on);
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_LONG).show();
                }
                HttpUtils http = new HttpUtils();
                http.configCurrentHttpCacheExpiry(1);
                RequestParams params = new RequestParams();

                params.addBodyParameter("ArticleID",id);
                params.addBodyParameter("UserID",UserID);
                params.addBodyParameter("Fav",Fav);
                try {
                    http.sendSync(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER+"/doctuser/setfavourite.php",params);
                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        });
        tv_subcribelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"订阅成功",Toast.LENGTH_LONG).show();

                HttpUtils http = new HttpUtils();
                http.configCurrentHttpCacheExpiry(1);
                RequestParams params = new RequestParams();

                params.addBodyParameter("PathemaTypeID",tv_pathemaType.getText().toString());
                params.addBodyParameter("UserID",UserID);
                if (Sub.equals("0")) {
                    params.addBodyParameter("sub", "1");
                }else {
                    params.addBodyParameter("sub", "0");
                }
                try {
                    http.sendSync(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER+"/doctuser/setsubscribe.php",params);
                } catch (HttpException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private void initView() {

        lv_comment = (ScrollListView) findViewById(R.id.activity_expert_detail_lv_comment);
        lv_concerned = (ScrollListView) findViewById(R.id.activity_expert_detail_lv_concerned);

        iv_back = (ImageView) findViewById(R.id.activity_expert_detail_iv_title_bar_back);
        iv_articalFav = (ImageView) findViewById(R.id.activity_expert_detail_iv_artical_fav);
        iv_urlContent = (ImageView) findViewById(R.id.activity_expert_detail_iv_content);

        tv_articalTime = (TextView) findViewById(R.id.activity_expert_detail_tv_articalTime);
        tv_pathemaType = (TextView) findViewById(R.id.activity_expert_detail_tv_pathemaType);
        tv_subcribelist = (TextView) findViewById(R.id.activity_expert_detail_tv_subcribelist);
        tv_pathemaType2 = (TextView) findViewById(R.id.activity_expert_detail_lv_tv_illness);
        tv_articalTitle = (TextView) findViewById(R.id.activity_expert_detail_tv_articalTitle);
        tv_content = (TextView) findViewById(R.id.activity_expert_detail_tv_content);

        layout_like = (LinearLayout) findViewById(R.id.activity_expert_detail_layout_social_like);
        layout_comment = (LinearLayout) findViewById(R.id.activity_expert_detail_layout_social_comment);
        layout_report = (LinearLayout) findViewById(R.id.activity_expert_detail_layout_social_report);

        tv_like = (TextView) findViewById(R.id.activity_expert_detail_tv_social_like);
        tv_comment = (TextView) findViewById(R.id.activity_expert_detail_tv_social_comment);
        tv_report = (TextView) findViewById(R.id.activity_expert_detail_tv_social_report);
        authorDetail = (TextView) findViewById(R.id.activity_expert_detail_author_detail);
        authorIcon = (CircleImageView) findViewById(R.id.activity_expert_detail_author_head);
        authorName = (TextView) findViewById(R.id.activity_expert_detail_author_tv_nickname);

    }

    /**
     * 问答详情
     */
    public void getExpertDetail(final Context context, String url) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = (String) responseInfo.result;
                parseExpertDetailData(result);
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
    private void parseExpertDetailData(String result) {
        ExpertDetailBean expertDetailBean;
        ExpertDetailValueBean expertDetailValueBean;
        List<ExpertDetailConcerned> expertDetailConcerneds;
        ExpertDetailValueBean.User user;
        List<ExpertDetailBean.ExpertDetailComment> expertDetailComments;
        System.out.println("ExpertDeatile==========================" + result);
        Gson gson = new Gson();
        expertDetailBean = gson.fromJson(result, ExpertDetailBean.class);
        expertDetailValueBean = expertDetailBean.value;
        expertDetailConcerneds = expertDetailValueBean.concerned;
        expertDetailComments = expertDetailBean.Comment;
        user = expertDetailValueBean.User;

        tv_pathemaType.setText(expertDetailValueBean.PathemaTypeID);
        Sub = expertDetailValueBean.sub;
        if ((Fav=expertDetailValueBean.sub) != null) {
            if (Fav.equals("1")) {
                iv_articalFav.setImageResource(R.mipmap.artical_fav_on);
            }
        }
        tv_articalTitle.setText(expertDetailValueBean.ArticleTitile);
        if (expertDetailValueBean.ArticlePic.equals("null")) {
            iv_urlContent.setVisibility(View.INVISIBLE);
        } else {
            Picasso.with(this).load(globalAddress.SERVER + expertDetailValueBean.ArticlePic).into(iv_urlContent);
        }
        tv_content.setText(expertDetailValueBean.ArticleDesc);
        tv_articalTime.setText(dateUtils.getDateToString(Long.parseLong(expertDetailValueBean.CreateTime)));
        tv_like.setText(expertDetailValueBean.Likenum);
        tv_comment.setText(expertDetailValueBean.Comm);
        tv_report.setText(expertDetailValueBean.Send);
        authorName.setText(user.UserName);
        authorDetail.setText(user.UserDetail);
        Picasso.with(this).load(user.UserIcon).into(authorIcon);
        UserID = expertDetailValueBean.UserID;

//        作者信息


//        评论数据
        String[] commentNickName = new String[30];
        String[] commentTime = new String[30];
        String[] commentImge = new String[30];
        String[] commentContent = new String[30];
        for (int i = 0; i < expertDetailComments.size(); i++) {
            commentContent[i] = expertDetailComments.get(i).Content;
            commentImge[i] = expertDetailComments.get(i).User.UserIcon;
            commentNickName[i] = expertDetailComments.get(i).User.UserName;
            commentTime[i] = dateUtils.getDateToString(Long.parseLong(expertDetailComments.get(i).CreateTime));
            AnswerDetailModel answerDetailModel = new AnswerDetailModel(commentImge[i], commentNickName[i], commentTime[i], commentContent[i]);

        }
        lv_comment.setAdapter(new AdpAnswerDetailComment(this, listcomment));

        //        相关科普的数据
        String[] Iv_headImage = new String[30];
        String[] NickName = new String[30];
        String[] Comment = new String[30];
        String[] AnswerTitle = new String[30];
        String[] expertPic = new String[30];
        String[] Time = new String[30];
        String[] like = new String[30];
        String CommunityID[] = new String[30];

        for (int i = 0; i < expertDetailConcerneds.size(); i++) {
            Iv_headImage[i] = expertDetailConcerneds.get(i).User.getUserIcon();
            NickName[i] = expertDetailConcerneds.get(i).User.getUserName();
            Comment[i] = expertDetailConcerneds.get(i).getComm();
            AnswerTitle[i] = expertDetailConcerneds.get(i).getArticleTitile();
            expertPic[i] = expertDetailConcerneds.get(i).ArticlePic;
            CommunityID[i] = expertDetailConcerneds.get(i).getArticleID();
            Time[i] = dateUtils.getDateToString(Long.valueOf(expertDetailConcerneds.get(i).getCreateTime()).longValue());
            like[i] = expertDetailConcerneds.get(i).getLikenum();
            FgHomeExpertModel fgHomeExpertModel = new FgHomeExpertModel(Iv_headImage[i], NickName[i], Time[i], AnswerTitle[i], expertPic[i], like[i], Comment[i],CommunityID[i]);
            listconcerned.add(fgHomeExpertModel);
        }
        lv_concerned.setAdapter(new AdpHomeFgExpert(this, listconcerned));

    }


}
