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

import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.MyListView;
import com.jinke.doctorbear.View.NoScrollViewPager;

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
		View view = inflater.inflate(R.layout.fragment_home, container, false);
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
		viewPager.setAdapter(new MyAdapter());

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

	/**
	 * 主界面ViewPager适配器，只有两个界面
	 * 一个问答界面，一个科普界面
	 */
	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}
		public Object instantiateItem(ViewGroup container, int position) {
			View view = null;
			//问答界面
			if (position==0){
				view = View.inflate(getContext(), R.layout.fg_home_answer,null);
				ListView listView_answer = (ListView) view.findViewById(R.id.fg_home_answer_lv);
				listView_answer.setAdapter(new AnswerLvAdapter());
			}
			//科普界面
			if (position==1){
				view = View.inflate(getContext(), R.layout.fg_home_expert,null);
				MyListView listView_expert = (MyListView) view.findViewById(R.id.fg_home_expert_lv);
				ViewPager viewPager_expert = (ViewPager)view.findViewById(R.id.fg_home_expert_vp);
				viewPager_expert.setAdapter(new ExpertVpAdapter());
				listView_expert.setAdapter(new ExpertLvAdapter());

			}
			container.addView(view);
			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
	}

	/**
	 * 科普界面ViewPager的适配器
	 */
	class ExpertVpAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(getContext(), R.layout.fg_home_expert_vp_item,null);
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
	}

	/**
	 * 科普界面ListView的适配器
	 */
	class ExpertLvAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getContext(), R.layout.fg_home_expert_lv_item,null);
			return view;
		}
	}

	/**
	 * 问答界面ListView的适配器
	 */
	class AnswerLvAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getContext(), R.layout.fg_home_answer_lv_item,null);
			return view;
		}
	}
}
