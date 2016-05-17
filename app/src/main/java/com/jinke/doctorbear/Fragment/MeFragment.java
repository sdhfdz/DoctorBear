package com.jinke.doctorbear.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jinke.doctorbear.R;

/**
 * 我界面业务逻辑
 */

public class MeFragment extends Fragment {

	private ListView fg_me_lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fg_me, container, false);
		init(view);
		return view;
	}
	public void init(View view){
		fg_me_lv = (ListView) view.findViewById(R.id.fg_me_lv);
		fg_me_lv.setAdapter(new MyBaseAdapter());
	}
	class MyBaseAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 20;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			return super.hasStableIds();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=null;
			if (convertView==null){
				view=View.inflate(getContext(),R.layout.fg_me_listitem,null);

			}else{
				view=convertView;
			}
			return view;
		}
	}

}
