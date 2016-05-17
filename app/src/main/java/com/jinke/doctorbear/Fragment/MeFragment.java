package com.jinke.doctorbear.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jinke.doctorbear.Adapter.AdpMeFglv;
import com.jinke.doctorbear.R;

/**
 * 我界面业务逻辑
 */

public class MeFragment extends Fragment implements View.OnClickListener{

	private ListView fg_me_lv;
	private LinearLayout ll_mycollection;
	private LinearLayout ll_mysubscription;
	private LinearLayout ll_mymessage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fg_me, container, false);
		init(view);
		return view;
	}
	public void init(View view){
		fg_me_lv = (ListView) view.findViewById(R.id.fg_me_lv);
		ll_mycollection = (LinearLayout) view.findViewById(R.id.ll_mycollection);
		ll_mysubscription = (LinearLayout) view.findViewById(R.id.ll_mysubscription);
		ll_mymessage = (LinearLayout) view.findViewById(R.id.ll_mymessage);
		ll_mycollection.setOnClickListener(this);
		ll_mysubscription.setOnClickListener(this);
		ll_mymessage.setOnClickListener(this);

		fg_me_lv.setAdapter(new AdpMeFglv(getContext()));
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.ll_mycollection:
				System.out.println("我的收藏");
				break;
			case R.id.ll_mysubscription:
				System.out.println("我的订阅");
				break;
			case R.id.ll_mymessage:
				System.out.println("我的消息");
				break;
		}
	}



}
