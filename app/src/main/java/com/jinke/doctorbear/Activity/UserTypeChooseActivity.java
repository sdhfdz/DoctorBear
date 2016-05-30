package com.jinke.doctorbear.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

public class UserTypeChooseActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout iamDoc;
    private RelativeLayout iamUser;
    private HttpUtils http;
    private Platform weibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_choose);
        init();
    }
    public void init(){
        iamDoc = (RelativeLayout) findViewById(R.id.IamDoc);
        iamUser = (RelativeLayout) findViewById(R.id.IamUser);
        iamDoc.setOnClickListener(this);
        iamUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.IamUser:
                PostUserTypeID("1");
                break;
            case R.id.IamDoc:
                PostUserTypeID("2");
                break;
        }
    }
    public void PostUserTypeID(String UserType){

        weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        http = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("UserID",weibo.getDb().getUserId());
        params.addBodyParameter("UserTypeID",UserType);
        http.send(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER+"/doctuser/setusertype.php",params,new MyrequestCallBack());
    }
    class MyrequestCallBack extends RequestCallBack{
        @Override
        public void onSuccess(ResponseInfo responseInfo) {
            System.out.println(responseInfo.result.toString()+"LLLLLLLLLLLLLLLLLLLLLLL");
            String result=null;
            JSONObject jsonObject=null;
            String token=null;
            try {
                 jsonObject=new JSONObject(responseInfo.result.toString());
                result=jsonObject.getString("UserTypeID");
                token=jsonObject.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("0".equals(result)){
                Toast.makeText(UserTypeChooseActivity.this,"设置失败",Toast.LENGTH_SHORT).show();

              //  startActivity(new Intent(UserTypeChooseActivity.this,UserTypeChooseActivity.class));
            }else{
                GlobalAddress.setToken(token,UserTypeChooseActivity.this);
                startActivity(new Intent(UserTypeChooseActivity.this,MainActivity.class));
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

            System.out.println(s+"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");

        }
    }
}
