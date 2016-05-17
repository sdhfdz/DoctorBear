package com.jinke.doctorbear.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.doctorbear.Activity.HomeTextEdit;
import com.jinke.doctorbear.Adapter.AdpHomeFgMain;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.NoScrollViewPager;
import com.jinke.doctorbear.Activity.FgHomeSearchActivity;

/**
 * 主页面业务逻辑
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

	private NoScrollViewPager viewPager;
	private TextView tv_answer;
	private TextView tv_expert;
	private TextView tv_answer_line;
	private TextView tv_expert_line;
	private ImageView iv_search;
	private ImageView iv_edit;
	SharedPreferences sp;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_home, container, false);
		sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
		initView(view);
		initListener();
		return view;
	}

	private void initView(View view) {
		viewPager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
		tv_answer = (TextView) view.findViewById(R.id.fg_home_tv_answer);
		tv_expert = (TextView) view.findViewById(R.id.fg_home_tv_expert);
		tv_answer_line = (TextView)view.findViewById(R.id.fg_home_tv_answer_line);
		tv_expert_line = (TextView)view.findViewById(R.id.fg_home_tv_expert_line);
		iv_search = (ImageView) view.findViewById(R.id.fg_home_iv_search);
		iv_edit = (ImageView)view.findViewById(R.id.fg_home_iv_edit);

		boolean page = sp.getBoolean("page",true);
		if (page){
			changeColorAndPage(0);
		}else {
			changeColorAndPage(1);
		}
		viewPager.setAdapter(new AdpHomeFgMain(view.getContext()));

	}
	private void initListener() {
		tv_answer.setOnClickListener(this);
		tv_expert.setOnClickListener(this);
		iv_search.setOnClickListener(this);
		iv_edit.setOnClickListener(this);
	}
	@Override

	public void onClick(View v) {
		switch (v.getId()){
			case R.id.fg_home_tv_answer:
				changeColorAndPage(0);
				sp.edit().putBoolean("page",true).commit();
				break;
			case R.id.fg_home_tv_expert:
				changeColorAndPage(1);
				sp.edit().putBoolean("page",false).commit();
				break;
			case R.id.fg_home_iv_edit:
				Intent intent_edit = new Intent(v.getContext(), HomeTextEdit.class);
				startActivity(intent_edit);
				break;
			case R.id.fg_home_iv_search:
				Intent intent = new Intent(v.getContext(), FgHomeSearchActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.alphain, R.anim.alphaout);
				break;
			default:

		}
	}

	/**
	 * 点击问答和科普改变颜色和页面
	 * @param position
     */
	public void changeColorAndPage(int position){
		if (position==0){
			tv_answer.setTextColor(getContext().getResources().getColor(R.color.colorPressed));
			tv_expert.setTextColor(getContext().getResources().getColor(R.color.colorNormal));
			tv_answer_line.setVisibility(View.VISIBLE);
			tv_expert_line.setVisibility(View.INVISIBLE);
			viewPager.setCurrentItem(0,false);
		}
		if (position==1){
			tv_answer.setTextColor(getContext().getResources().getColor(R.color.colorNormal));
			tv_expert.setTextColor(getContext().getResources().getColor(R.color.colorPressed));
			tv_answer_line.setVisibility(View.INVISIBLE);
			tv_expert_line.setVisibility(View.VISIBLE);
			viewPager.setCurrentItem(1,false);
		}
	}
}
