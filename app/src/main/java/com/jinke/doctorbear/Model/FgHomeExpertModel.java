package com.jinke.doctorbear.Model;

/**
 * FragmentHome中的科普页面的数据类型
 * Created by Max on 2016/5/17.
 */
public class FgHomeExpertModel {
    String iv_headImage;
    String nickName;
    String time;
    String expertTitle;
    String expertPicture;
    String like;
    String comment;
    String ArticleID;

    public FgHomeExpertModel(String iv_headImage, String nickName, String time, String expertTitle, String expertPicture, String like, String comment,String ArticleID) {
        this.iv_headImage = iv_headImage;
        this.nickName = nickName;
        this.time = time;
        this.expertTitle = expertTitle;
        this.expertPicture = expertPicture;
        this.like = like;
        this.comment = comment;
        this.ArticleID = ArticleID;
    }

    public String getExpertTitle() {
        return expertTitle;
    }

    public void setExpertTitle(String expertTitle) {
        this.expertTitle = expertTitle;
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

    public String getExpertPicture() {
        return expertPicture;
    }

    public void setExpertPicture(String expertPicture) {
        this.expertPicture = expertPicture;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setArticleID(String articleID) {
        ArticleID = articleID;
    }

    public String getArticleID() {
        return ArticleID;
    }
}
