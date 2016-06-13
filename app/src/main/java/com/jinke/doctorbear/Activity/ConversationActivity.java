package com.jinke.doctorbear.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

import io.rong.imkit.RongIM;

public class ConversationActivity extends AppCompatActivity {

    private TextView BarTitle;
    private HttpUtils http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
//       String title= getIntent().getData().getQueryParameter("title");
//        System.out.println("titile"+title+"???");
        BarTitle = (TextView) findViewById(R.id.title_bar_content);
        BarTitle.setText("");

        System.out.println(getIntent().getData().getQueryParameter("targetId")+"对方id");
        http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1);

        http.send(HttpRequest.HttpMethod.GET,GlobalAddress.SERVER+"/doctuser/getuserinfo.php?UserID="+getIntent().getData().getQueryParameter("targetId"),
                new MyrequestCallBack());




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("lalllallal");
        finish();
    }
    class MyrequestCallBack extends RequestCallBack{

        private JSONObject jsonObject;

        @Override
        public void onSuccess(ResponseInfo responseInfo) {

            try {
                if (responseInfo.result.toString().trim().length()>0){

                    jsonObject = new JSONObject(responseInfo.result.toString());
                    BarTitle.setText(jsonObject.getString("UserName"));

                }else{
                    BarTitle.setText("未知");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(responseInfo.result+"hahhahhhhh");


        }

        @Override
        public void onFailure(HttpException e, String s) {
            System.out.println(s+"hahhahhhhh");
        }
    }
}
