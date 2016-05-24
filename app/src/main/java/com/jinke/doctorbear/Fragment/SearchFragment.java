package com.jinke.doctorbear.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.doctorbear.Activity.SearchAllActivity;
import com.jinke.doctorbear.Activity.SearchResultLayout;
import com.jinke.doctorbear.Activity.SearchLocationActivity;
import com.jinke.doctorbear.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;

/**
 * 搜索界面业务逻辑
 */

public class SearchFragment extends Fragment implements View.OnClickListener{

	private EditText et_search;
	private ImageView iv_scan;
	private ImageView iv_hospital;
	private ImageView iv_disease;
	private ImageView iv_capsule;
	private ImageView iv_location;
	private ImageView iv_putian;

	private TextView tv_hot1;
	private TextView tv_hot2;
	private TextView tv_hot3;
	private TextView tv_hot4;
	private TextView tv_hot5;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fg_search, container, false);
		init(view);
		return view;
	}

	private void init(View view) {
		initView(view);
		initData(view);
		initListener();
	}

	/**
	 * 设置监听
	 */
	private void initListener() {
		iv_scan.setOnClickListener(this);
		iv_capsule.setOnClickListener(this);
		iv_putian.setOnClickListener(this);
		iv_location.setOnClickListener(this);
		iv_disease.setOnClickListener(this);
		iv_hospital.setOnClickListener(this);

        et_search.setOnClickListener(this);
        et_search.setOnKeyListener(onKeyListener);


    }

	/**
	 * 初始化数据
	 * @param view
     */
	private void initData(View view) {
	}

	/**
	 * 初始化控件
	 * @param view
     */
	private void initView(View view) {
		iv_scan = (ImageView) view.findViewById(R.id.fg_search_iv_scan);
		iv_capsule = (ImageView) view.findViewById(R.id.fg_search_iv_capsule);
		iv_disease = (ImageView) view.findViewById(R.id.fg_search_iv_disease);
		iv_hospital = (ImageView) view.findViewById(R.id.fg_search_iv_hospital);
		iv_location = (ImageView) view.findViewById(R.id.fg_search_iv_location);
		iv_putian = (ImageView) view.findViewById(R.id.fg_search_iv_putian);
		et_search = (EditText) view.findViewById(R.id.fg_search_et_search);

		tv_hot1 = (TextView) view.findViewById(R.id.fg_search_tv_hot1);
		tv_hot2 = (TextView) view.findViewById(R.id.fg_search_tv_hot2);
		tv_hot3 = (TextView) view.findViewById(R.id.fg_search_tv_hot3);
		tv_hot4 = (TextView) view.findViewById(R.id.fg_search_tv_hot4);
		tv_hot5 = (TextView) view.findViewById(R.id.fg_search_tv_hot5);

	}

	/**
	 * 点击事件
	 * @param v
     */
	@Override
	public void onClick(View v) {
        switch (v.getId()){
            case R.id.fg_search_iv_scan:
				Intent intent = new Intent(getActivity(), CaptureActivity.class);
				startActivity(intent);
                break;
            case R.id.fg_search_iv_disease:
				Intent intent_disease = new Intent(getActivity(), SearchAllActivity.class);
				intent_disease.putExtra("kind","disease");
				startActivity(intent_disease);
                break;
            case R.id.fg_search_iv_capsule:
				Intent intent_capsule = new Intent(getActivity(), SearchAllActivity.class);
				intent_capsule.putExtra("kind","capsule");
				startActivity(intent_capsule);
                break;
            case R.id.fg_search_iv_hospital:
				Intent intent_hospital = new Intent(getActivity(), SearchAllActivity.class);
				intent_hospital.putExtra("kind","hospital");
				startActivity(intent_hospital);
                break;
            case R.id.fg_search_iv_location:
				System.out.println("调到百度地图");
				startActivity(new Intent(getActivity(), SearchLocationActivity.class));

                break;
            case R.id.fg_search_iv_putian:
				Intent intent_putian = new Intent(getActivity(), SearchAllActivity.class);
				intent_putian.putExtra("kind","putian");
				startActivity(intent_putian);
                break;
            default:
                break;
        }

	}
    /**
     * 输入法按下搜索的监听
     */
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                /**
                 * 本来应该从服务器获取数据,但是由于尚未与服务器进行连接,所以这里做的操作是隐藏软件盘.
                 */
				Intent intent_s = new Intent(getActivity(), SearchResultLayout.class);
				intent_s.putExtra("kind",0);//默认疾病
				startActivity(intent_s);
                InputMethodManager inputMethodManager = (InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

                }

//                search_name.clear();
//                search_id.clear();
//                search_price.clear();
//                search_picUrl.clear();
//                search_number.clear();
                //pb_progress.setVisibility(View.VISIBLE);
//                name = et_search.getText().toString();
//                System.out.println("DATADATA:"+name);
//                search_detail_url = DownloadUrl.search_url + name;
//                if (search_detail_url!=null){
//                    getDataFromServer();
//                }


                return true;
            }
            return false;
        }
    };
}
