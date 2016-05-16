package com.jinke.doctorbear.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.doctorbear.Adapter.AdpHomeFgMain;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.ScrollListView;
import com.jinke.doctorbear.Utils.NoScrollViewPager;

/**
 * 主页面业务逻辑
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

	private NoScrollViewPager viewPager;
	private TextView tv_answer;
	private TextView tv_expert;
	private TextView tv_answer_line;
	private TextView tv_expert_line;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg_home, container, false);
		initView(view);
		initListener();
		return view;
	}

	private void initView(View view) {
		viewPager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
		tv_answer = (TextView) view.findViewById(R.id.tv_answer);
		tv_expert = (TextView) view.findViewById(R.id.tv_expert);
		tv_answer_line = (TextView)view.findViewById(R.id.tv_answer_line);
		tv_expert_line = (TextView)view.findViewById(R.id.tv_expert_line);
		tv_answer.setTextColor(this.getResources().getColor(R.color.colorPressed));
		tv_answer_line.setVisibility(View.VISIBLE);
		viewPager.setAdapter(new AdpHomeFgMain(view.getContext()));

	}
	private void initListener() {
		tv_answer.setOnClickListener(this);
		tv_expert.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.tv_answer:
				viewPager.setCurrentItem(0,false);
				changeColor(0);
				break;
			case R.id.tv_expert:
				viewPager.setCurrentItem(1,false);
				changeColor(1);
				break;
			default:
				break;
		}
	}

	/**
	 * 点击问答和科普改变颜色和下划线
	 * @param position
     */
	public void changeColor(int position){
		if (position==0){
			tv_answer.setTextColor(getContext().getResources().getColor(R.color.colorPressed));
			tv_expert.setTextColor(getContext().getResources().getColor(R.color.colorNormal));
			tv_answer_line.setVisibility(View.VISIBLE);
			tv_expert_line.setVisibility(View.INVISIBLE);
		}
		if (position==1){
			tv_answer.setTextColor(getContext().getResources().getColor(R.color.colorNormal));
			tv_expert.setTextColor(getContext().getResources().getColor(R.color.colorPressed));
			tv_answer_line.setVisibility(View.INVISIBLE);
			tv_expert_line.setVisibility(View.VISIBLE);
		}
	}


}
