package com.jinke.doctorbear.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jinke.doctorbear.MainActivity;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 发表评论页面
 * Created by Max on 2016/6/12.
 */
public class PushCommentActivity extends Activity{

    private TextView tv_cancel;
    private TextView tv_push;
    private TextView et_comment;

    private HttpUtils http;


    String UserID;
    String ArticleID;
    String CommunityID;
    int kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_comment);

        init();
    }

    private void init() {
        initView();
        initData();
        initListener();
    }

    private void initData() {

        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
        kind = intent.getIntExtra("kind", 0);
        if (kind == 0) {
            CommunityID = intent.getStringExtra("CommunityID");

        }
        else {
            ArticleID = intent.getStringExtra("ArticleID");
        }
    }

    private void initListener() {

        tv_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postCommentToServer();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {

        tv_cancel = (TextView) findViewById(R.id.activity_push_comment_cancel_tv);
        tv_push = (TextView) findViewById(R.id.activity_push_comment_submit_tv);
        et_comment = (TextView) findViewById(R.id.activity_push_comment_et_comment);
    }
    public void postCommentToServer(){
        http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1);
        RequestParams params = new RequestParams();
        if (kind == 0 ){
            params.addBodyParameter("CommunityID",CommunityID);
            System.out.println("kind = 0");
        }else {
            params.addBodyParameter("ArticleID",ArticleID);
            System.out.println("kind = 1");
        }
        params.addBodyParameter("UserID",UserID);
        params.addBodyParameter("content",et_comment.getText().toString());
        http.send(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER+"/doctuser/commentadd.php",params,new MyrequestCallBack());
    }
    class MyrequestCallBack extends RequestCallBack {
        @Override
        public void onSuccess(ResponseInfo responseInfo) {
            JSONObject jsonObject=null;
            System.out.println(responseInfo.result.toString()+"PushCommentActivity");
            finish();
        }

        @Override
        public void onFailure(HttpException e, String s) {
            System.out.println(s+"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        }
    }
}

