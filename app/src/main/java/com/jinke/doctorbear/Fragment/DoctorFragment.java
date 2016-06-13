package com.jinke.doctorbear.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jinke.doctorbear.Activity.BearSmartActivity;
import com.jinke.doctorbear.Activity.ConversationActivity;
import com.jinke.doctorbear.Activity.LoginActivity;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import io.rong.imkit.MainActivity;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * 熊大夫页面业务逻辑
 */

public class DoctorFragment extends Fragment {

    private ImageView professional_doc;
    private ImageView bear_smart;
    private HttpUtils http;
    private Platform weibo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_doctor, container, false);
        bear_smart = (ImageView) view.findViewById(R.id.bearSmart);
        professional_doc = (ImageView) view.findViewById(R.id.professional_doc);
        bear_smart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BearSmartActivity.class));
            }
        });
        professional_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("点击专业医师");
                weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                if ((GlobalAddress.getUserId(getActivity()).equals(""))) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
//					System.out.println(weibo.getDb().getUserIcon()+
//					":"+weibo.getDb().getUserId()+":"+weibo.getDb().getUserName());
//					http = new HttpUtils();
//					http.configCurrentHttpCacheExpiry(1);
//					RequestParams params = new RequestParams();
//					int radom=(int)(Math.random()*100);
//					long time=System.currentTimeMillis();
//					params.addHeader("App-Key", GlobalAddress.App_key);
//					params.addHeader("Nonce",radom+"");
//					params.addHeader("Timestamp",time+"");
//					params.addHeader("Signature",GlobalAddress.sha1(GlobalAddress.App_key+radom+time));
//					params.addBodyParameter("userId",weibo.getDb().getUserId());
//					params.addBodyParameter("name",weibo.getDb().getUserName());
//					params.addBodyParameter("portraitUri",weibo.getDb().getUserIcon());
//					http.send(HttpRequest.HttpMethod.POST,"https://api.cn.ronghub.com/user/getToken.json",params,new MyrequestCallBack());

                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(GlobalAddress.getUserId(getActivity()),
                    GlobalAddress.getUserName(getActivity()), Uri.parse(GlobalAddress.getUserIcon(getActivity()))));
                    System.out.println(GlobalAddress.getUserId(getActivity())+"测试一下idsdfgsdfgs");
                    System.out.println(GlobalAddress.getToken(getActivity())+"测试tokensf");
                    if (RongIM.getInstance() != null && !(GlobalAddress.getUserId(getActivity()).equals("3963183378"))) {
                        RongIM.getInstance().startPrivateChat(getActivity(),"3963183378","haha");
                        System.out.println(GlobalAddress.getUserName(getActivity())+">>>>>>>>>>>>>>>>>>");
                    }


                }

            }
        });
        return view;
    }

    class MyrequestCallBack extends RequestCallBack {
        @Override
        public void onSuccess(ResponseInfo responseInfo) {
            System.out.println(responseInfo.result.toString() + "你好");
        }

        @Override
        public void onFailure(HttpException e, String s) {
            System.out.println("错误信息:" + s + e.toString());

        }
    }

}
