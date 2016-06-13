package com.jinke.doctorbear.Bean;

/**
 * Created by Max on 2016/6/12.
 */
public class AnswerDetailConcerned {
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

    public String CommunityID;//问答id
    public String CommunityTitle;//标题
    public String CommunityDesc;//内容
    public String CommunityPic;//图片

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

    public String getCommunityID() {
        return CommunityID;
    }

    public String getCommunityTitle() {
        return CommunityTitle;
    }

    public String getCommunityDesc() {
        return CommunityDesc;
    }

    public String getCommunityPic() {
        return CommunityPic;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getComm() {
        return Comm;
    }
}

