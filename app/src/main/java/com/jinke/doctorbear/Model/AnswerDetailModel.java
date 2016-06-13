package com.jinke.doctorbear.Model;

import android.widget.TextView;

/**
 * Created by Max on 2016/5/31.
 */
public class AnswerDetailModel {
    String iv_head;
    String nickName;
    String time;
    String answerContent;
    String urlContent;

    public AnswerDetailModel(String iv_head, String nickName, String time, String answerContent) {
        this.iv_head = iv_head;
        this.nickName = nickName;
        this.time = time;
        this.answerContent = answerContent;
    }

    public String getIv_head() {
        return iv_head;
    }

    public void setIv_head(String iv_head) {
        this.iv_head = iv_head;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
