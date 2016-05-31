package com.jinke.doctorbear.Bean;

/**
 * Created by Max on 2016/5/30.
 */
public class HomeExpertValueBean {
    public User User;
    public PathemaType PathemaType;

    public static class User{
        public String UserIcon;
        public String UserName;

        public String getUserIcon() {
            return UserIcon;
        }

        public void setUserIcon(String userIcon) {
            UserIcon = userIcon;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }
    }
    public String ArticleID;    //id
    public String ArticleTitle; //标题
    public String ArticleDesc; //内容
    public String ArticlePic;  //图片
    public class PathemaType{
        public String PathemaTypeName;
        public String PathemaTypeID;

        public String getPathemaTypeName() {
            return PathemaTypeName;
        }

        public String getPathemaTypeID() {
            return PathemaTypeID;
        }

        public void setPathemaTypeID(String pathemaTypeID) {
            PathemaTypeID = pathemaTypeID;
        }

        public void setPathemaTypeName(String pathemaTypeName) {
            PathemaTypeName = pathemaTypeName;
        }
    }
    public String CreateTime;    //创建时间
    public String Comm; //评论数
    public String Likenum; //点赞数



    public String getArticleID() {
        return ArticleID;
    }

    public void setArticleID(String articleID) {
        ArticleID = articleID;
    }

    public String getArticleTitle() {
        return ArticleTitle;
    }

    public void setArticleTitle(String articleTitile) {
        ArticleTitle = articleTitile;
    }

    public String getArticleDesc() {
        return ArticleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        ArticleDesc = articleDesc;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getArticlePic() {
        return ArticlePic;
    }

    public void setArticlePic(String articlePic) {
        ArticlePic = articlePic;
    }

    public String getComm() {
        return Comm;
    }

    public void setComm(String comm) {
        Comm = comm;
    }

    public String getLikenum() {
        return Likenum;
    }

    public void setLikenum(String likenum) {
        Likenum = likenum;
    }

    public HomeExpertValueBean(HomeExpertValueBean.User user, HomeExpertValueBean.PathemaType pathemaType, String articleID, String articleTitile, String articleDesc, String createTime, String articlePic, String comm, String likenum) {
        User = user;
        PathemaType = pathemaType;
        ArticleID = articleID;
        ArticleTitle = articleTitile;
        ArticleDesc = articleDesc;
        CreateTime = createTime;
        ArticlePic = articlePic;
        Comm = comm;
        Likenum = likenum;
    }
}
