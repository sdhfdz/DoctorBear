package com.jinke.doctorbear.Bean;

/**
 * Created by Max on 2016/6/12.
 */
public class ExpertDetailConcerned {
    public User User;
    public PathemaType PathemaType;

    public static class User {
        public String UserIcon;
        public String UserName;

        public String getUserIcon() {
            return UserIcon;
        }

        public String getUserName() {
            return UserName;
        }
    }

    public String ArticleID;//问答id
    public String ArticleTitile;//标题
    public String ArticleDesc;//内容
    public String ArticlePic;//图片

    public static class PathemaType {
        public String PathemaTypeName;
        public String PathemaTypeID;

        public String getPathemaTypeName() {
            return PathemaTypeName;
        }

        public String getPathemaTypeID() {
            return PathemaTypeID;
        }
    }

    public String CreateTime;//创建时间
    public String Comm;//评论数
    public String Likenum;

    public ExpertDetailConcerned.User getUser() {
        return User;
    }

    public ExpertDetailConcerned.PathemaType getPathemaType() {
        return PathemaType;
    }

    public String getArticleID() {
        return ArticleID;
    }

    public String getArticleTitile() {
        return ArticleTitile;
    }

    public String getArticleDesc() {
        return ArticleDesc;
    }

    public String getArticlePic() {
        return ArticlePic;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getComm() {
        return Comm;
    }

    public String getLikenum() {
        return Likenum;
    }
}
