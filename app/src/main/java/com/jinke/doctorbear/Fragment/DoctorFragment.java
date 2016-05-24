package com.jinke.doctorbear.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jinke.doctorbear.Activity.BearSmartActivity;
import com.jinke.doctorbear.R;

/**
 * 熊大夫页面业务逻辑
 */

public class DoctorFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fg_doctor, container, false);
		ImageView bear_smart = (ImageView) view.findViewById(R.id.bearSmart);
		bear_smart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getContext(), BearSmartActivity.class));
			}
		});
		return view;
	}

}
