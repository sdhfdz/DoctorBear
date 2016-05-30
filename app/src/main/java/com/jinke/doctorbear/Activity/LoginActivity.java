package com.jinke.doctorbear.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.concurrent.SynchronousQueue;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,Handler.Callback {

    private ImageView login_weibo;
    private Handler handler;
    private HttpUtils http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler = new Handler(this);
        login_weibo = (ImageView) findViewById(R.id.login_weibo);
        login_weibo.setOnClickListener(this);


//移除授权
//weibo.removeAccount(true);
    }

    @Override
    public void onClick(View v) {
        System.out.println("你好年后年后");
      //  ShareSDK.initSDK(this,"12e4d47253398");
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(new SinaWeiboListener());
        weibo.SSOSetting(true);
        //weibo.authorize();
        weibo.showUser(null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what){
            case 1:
                Toast.makeText(this, "授权成功，正在跳转登录操作…", Toast.LENGTH_SHORT).show();
                Object[] objs = (Object[]) msg.obj;
//                String platform = (String) objs[0];
                HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
//                if (signupListener != null && signupListener.onSignin(platform, res)) {
//                    SignupPage signupPage = new SignupPage();
//                    signupPage.setOnLoginListener(signupListener);
//                    signupPage.setPlatform(platform);
//                    signupPage.show(activity, null);
//                }

                //解析部分用户资料字段
                System.out.println("NIHAO");
                String id,name,description,profile_image_url;
                id=res.get("id").toString();//ID
                name=res.get("name").toString();//用户名
                description=res.get("description").toString();//描述
                profile_image_url=res.get("profile_image_url").toString();//头像链接
                String str="ID: "+id+";"+
                        "用户名： "+name+";"+
                        "描述："+description+";"+
                        "用户头像地址："+profile_image_url;
                System.out.println("用户资料: "+str);
                postUserIfoToServer(res);
                break;
            case 2: {
                //取消授权
                Toast.makeText(this, "授权取消", Toast.LENGTH_SHORT).show();
            } break;
            case 3: {
                //授权失败
                Toast.makeText(this,"授权失败", Toast.LENGTH_SHORT).show();
            } break;
        }
        return false;

    }

    class SinaWeiboListener implements PlatformActionListener{
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
//            //解析部分用户资料字段
//            String id,name,description,profile_image_url;
//            id=res.get("id").toString();//ID
//            name=res.get("name").toString();//用户名
//            description=res.get("description").toString();//描述
//            profile_image_url=res.get("profile_image_url").toString();//头像链接
//            String str="ID: "+id+";\n"+
//                    "用户名： "+name+";\n"+
//                    "描述："+description+";\n"+
//                    "用户头像地址："+profile_image_url;
//            System.out.println("用户资料: "+str);
            System.out.println("Oncomplete"+"LLLLLLLLLLLLLLLLLLLLLLLLL");
            if (i == Platform.ACTION_USER_INFOR) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = new Object[] {platform.getName(), res};
                handler.sendMessage(msg);
            }

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            System.out.println("onError"+"LLLLLLLLLLLLLLLLLLLLLLLLL");
            if (i == Platform.ACTION_USER_INFOR) {
                handler.sendEmptyMessage(3);
            }
            throwable.printStackTrace();


        }

        @Override
        public void onCancel(Platform platform, int i) {

            System.out.println("onCancel"+"LLLLLLLLLLLLLLLLLLLLLLLLL");
            if (i == Platform.ACTION_USER_INFOR) {
                handler.sendEmptyMessage(2);
            }
        }
    }
    public void postUserIfoToServer(HashMap<String, Object> res){
        http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1);
        RequestParams params = new RequestParams();
        params.addBodyParameter("UserID",res.get("id").toString());
        params.addBodyParameter("UserTypeID","0");
        params.addBodyParameter("UserName",res.get("name").toString());
        params.addBodyParameter("UserIcon",res.get("profile_image_url").toString());
        params.addBodyParameter("UserDetail",res.get("description").toString());
        http.send(HttpRequest.HttpMethod.POST, GlobalAddress.SERVER+"/doctuser/signin.php",params,new MyrequestCallBack());
    }
    class MyrequestCallBack extends RequestCallBack{
        @Override
        public void onSuccess(ResponseInfo responseInfo) {
            String result=null;
            JSONObject jsonObject=null;
            String token=null;
            System.out.println(responseInfo.result.toString()+"shmguishmguishagis");
            try {
                 jsonObject=new JSONObject(responseInfo.result.toString());
                result=jsonObject.getString("UserTypeID");
                token=jsonObject.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if ("0".equals(result)){

                startActivity(new Intent(LoginActivity.this,UserTypeChooseActivity.class));
            }else{
                GlobalAddress.setToken(token,LoginActivity.this);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            System.out.println(s+"LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        }
    }
}
