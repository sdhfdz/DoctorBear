package com.jinke.doctorbear.Bean;

/**
 * Created by Max on 2016/5/30.
 */
public class HomeAnswerValueBean {

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
    public String CommunityID;    //问答id
    public String CommunityTitle; //标题
    public String CommunityDesc; //内容
    public String CommunityPic;  //图片
    public class PathemaType{
        public String PathemaTypeName;
        public String PathemaTypeID;

        public String getPathemaTypeName() {
            return PathemaTypeName;
        }

        public void setPathemaTypeName(String pathemaTypeName) {
            PathemaTypeName = pathemaTypeName;
        }
    }
    public String CreateTime;    //创建时间
    public String Comm; //评论数

    public String getCommunityID() {
        return CommunityID;
    }

    public void setCommunityID(String communityID) {
        CommunityID = communityID;
    }

    public String getCommunityTitle() {
        return CommunityTitle;
    }

    public void setCommunityTitle(String communityTitle) {
        CommunityTitle = communityTitle;
    }

    public String getCommunityDesc() {
        return CommunityDesc;
    }

    public void setCommunityDesc(String communityDesc) {
        CommunityDesc = communityDesc;
    }

    public String getCommunityPic() {
        return CommunityPic;
    }

    public void setCommunityPic(String communityPic) {
        CommunityPic = communityPic;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getComm() {
        return Comm;
    }

    public void setComm(String comm) {
        Comm = comm;
    }


}
