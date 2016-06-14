package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/6/12.
 */
public class ExpertDetailValueBean {
    public String PathemaTypeID;//疾病分类
    public String sub;  //(0未订阅／1已订阅)
    public String ArticleTitile;//标题
    public String ArticleDesc;//内容
    public String ArticlePic;//图片
    public String CreateTime;//创建时间
    public String ArticleID;
    public User User;
    public PathemaType PathemaType;
    public static class PathemaType{
        public String PathemaTypeID;
        public String PathemaTypeName;
    }
    public static class User {
        public String UserIcon;
        public String UserName;
        public String UserDetail;

    }

    public String UserID;
    public String Likenum;//点赞数
    public String Comm;//评论数
    public String Send;//转发数

    public List<ExpertDetailConcerned> concerned;

    public List<ExpertDetailConcerned> getConcerned() {
        return concerned;
    }
}
