package com.jinke.doctorbear.Activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.jinke.doctorbear.Adapter.AdpBearSmartLv;
import com.jinke.doctorbear.Bean.BSListData;
import com.jinke.doctorbear.Listener.HttpGetDataListener;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.BSHttpData;

public class BearSmartActivity extends Activity implements HttpGetDataListener,
		View.OnClickListener {

	private BSHttpData httpData;
	private List<BSListData> lists;
	private ListView lv;
	private EditText sendtext;
	private Button send_btn;
	private String content_str;
	private AdpBearSmartLv adapter;
	private String[] welcome_array;
	private long currentTime;
	private long oldTime = 0;
	private ImageView back_arr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bear_smart);

		initView();
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.lv);
		sendtext = (EditText) findViewById(R.id.sendText);
		send_btn = (Button) findViewById(R.id.send_btn);
		back_arr = (ImageView)findViewById(R.id.iv_back);
		lists = new ArrayList<BSListData>();
		back_arr.setOnClickListener(this);
		send_btn.setOnClickListener(this);
		adapter = new AdpBearSmartLv(lists, this);
		lv.setAdapter(adapter);
		BSListData listData = new BSListData(getRandomWelcomeTips(),
				BSListData.RECEIVER, getTime());
		lists.add(listData);

	}

	private String getRandomWelcomeTips() {
		String welcome_tips = null;
		welcome_array = this.getResources()
				.getStringArray(R.array.welcome_tips);
		int index = (int) (Math.random() * (welcome_array.length - 1));
		welcome_tips = welcome_array[index];
		return welcome_tips;

	}

	@Override
	public void getDataUrl(String data) {
		parseText(data);

	}

	private void parseText(String data) {
		try {
			JSONObject jb = new JSONObject(data);
			BSListData listData = new BSListData(jb.getString("text"),
					BSListData.RECEIVER, getTime());
			lists.add(listData);
			adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.send_btn:
				content_str = sendtext.getText().toString();
				sendtext.setText("");
				String string = content_str.replace(" ", "").replace("\n", "");
				BSListData listData = new BSListData(content_str, BSListData.SEND, getTime());
				lists.add(listData);
				if (lists.size() > 30) {
					for (int i = 0; i < 10; i++) {
						lists.remove(i);
					}
				}
				adapter.notifyDataSetChanged();
				httpData = (BSHttpData) new BSHttpData(
						"http://www.tuling123.com/openapi/api?key=38c40787015545b4799496cc525e4e68&info="
								+ string, this).execute();
				break;
			case R.id.iv_back:
				finish();
				break;
			default:
				break;
		}

	}

	private String getTime() {
		currentTime = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date curData = new Date();
		String str = format.format(curData);
		if (currentTime - oldTime >= 5 * 60 * 1000) {
			oldTime = currentTime;
			return str;
		} else {
			return "";
		}
	}

}
