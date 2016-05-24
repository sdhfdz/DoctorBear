package com.jinke.doctorbear.Bean;
/**
 * 熊大夫智能界面数据封装类
 */

import android.R.integer;

public class BSListData {
	
	public static final int SEND = 1;
	public static final int RECEIVER = 2;
	private int flag;
	private String time;
	
	private String content;
	public BSListData(String content,int flag,String time) {
		setContent(content);
		setFlag(flag);
		setTime(time);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
