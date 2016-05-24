package com.jinke.doctorbear.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.doctorbear.Bean.BSListData;
import com.jinke.doctorbear.R;

/**
 * 熊大夫智能界面listview适配器
 */
public class AdpBearSmartLv extends BaseAdapter{
	
	private List<BSListData> lists;
	private Context mContext;
	private RelativeLayout layout;
	
	public AdpBearSmartLv(List<BSListData> lists, Context mContext) {
		this.lists = lists;
		this.mContext = mContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		if (lists.get(position).getFlag() == BSListData.RECEIVER) {
			layout = (RelativeLayout)inflater.inflate(R.layout.bear_smart_lv_leftitem, null);
			
		}
		if (lists.get(position).getFlag() == BSListData.SEND) {
			layout = (RelativeLayout)inflater.inflate(R.layout.bear_smart_lv_rightitem, null);
			
		}
		TextView tv = (TextView)layout.findViewById(R.id.tv);
		TextView time = (TextView)layout.findViewById(R.id.time);
		tv.setText(lists.get(position).getContent());
		time.setText(lists.get(position).getTime());
		
		return layout;
	}

}
