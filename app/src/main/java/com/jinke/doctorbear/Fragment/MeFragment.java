package com.jinke.doctorbear.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.jinke.doctorbear.Activity.LoginActivity;
import com.jinke.doctorbear.Activity.MyCollectionActivity;
import com.jinke.doctorbear.Adapter.AdpMeFglv;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.CircleImageView;
import com.lidroid.xutils.BitmapUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * 我界面业务逻辑
 */

public class MeFragment extends Fragment implements View.OnClickListener{

	private ListView fg_me_lv;
	private LinearLayout ll_mycollection;
	private LinearLayout ll_mysubscription;
	private LinearLayout ll_mymessage;
	private CircleImageView fg_me_usericon_off;
	private CircleImageView fg_me_usericon_on;
	private Platform weibo;
	private BitmapUtils bitmapUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fg_me, container, false);
		init(view);
		return view;
	}
	public void init(View view){

		fg_me_usericon_off = (CircleImageView) view.findViewById(R.id.fg_me_usericon_off);
		fg_me_usericon_on = (CircleImageView) view.findViewById(R.id.fg_me_usericon_on);
		weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
		if (weibo.isAuthValid()){
			bitmapUtils = new BitmapUtils(getActivity());
			fg_me_usericon_off.setVisibility(View.GONE);
			fg_me_usericon_on.setVisibility(View.VISIBLE);
			bitmapUtils.display(fg_me_usericon_on,weibo.getDb().getUserIcon());
		}else{
			fg_me_usericon_off.setVisibility(View.VISIBLE);
			fg_me_usericon_on.setVisibility(View.GONE);
		}
		fg_me_lv = (ListView) view.findViewById(R.id.fg_me_lv);
		ll_mycollection = (LinearLayout) view.findViewById(R.id.ll_mycollection);
		ll_mysubscription = (LinearLayout) view.findViewById(R.id.ll_mysubscription);
		ll_mymessage = (LinearLayout) view.findViewById(R.id.ll_mymessage);
		ll_mycollection.setOnClickListener(this);
		ll_mysubscription.setOnClickListener(this);
		ll_mymessage.setOnClickListener(this);
		fg_me_usericon_off.setOnClickListener(this);
		fg_me_usericon_on.setOnClickListener(this);

		fg_me_lv.setAdapter(new AdpMeFglv(getContext()));
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.ll_mycollection:
				Intent intent = new Intent(getContext(), MyCollectionActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_mysubscription:
				System.out.println("我的订阅");
				break;
			case R.id.ll_mymessage:
				System.out.println("我的hah");
				break;
			case R.id.fg_me_usericon_off:
				System.out.println("LOGINLOGIN");
				startActivity(new Intent(getActivity(), LoginActivity.class));
				break;
			case R.id.fg_me_usericon_on:
				Toast.makeText(getContext(),"将会显示个人信息",Toast.LENGTH_SHORT).show();
				break;
		}
	}



}
