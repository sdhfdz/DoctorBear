package com.jinke.doctorbear.Model;

/**
 * FragmentHome中回答的数据类型
 * Created by Max on 2016/5/17.
 */
public class FgHomeAnswerModel {
    String iv_headImage;
    String nickName;
    String time;
    String answerTitle;
    String answerContent;
    String illness;
    String comment;
    String picture;
    String communityID;


    public FgHomeAnswerModel(String iv_headImage, String nickName, String answerTitle, String time, String answerContent,
                             String illness, String comment, String picture, String communityID) {
        this.iv_headImage = iv_headImage;
        this.nickName = nickName;
        this.answerTitle = answerTitle;
        this.time = time;
        this.answerContent = answerContent;
        this.illness = illness;
        this.comment = comment;
        this.picture = picture;
        this.communityID = communityID;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getIv_headImage() {
        return iv_headImage;
    }

    public void setIv_headImage(String iv_headImage) {
        this.iv_headImage = iv_headImage;
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

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
