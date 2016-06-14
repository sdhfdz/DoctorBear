package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/6/12.
 */
public class AnswerDeatilValueBean {

    public PathemaType PathemaType;
        public String sub;  //(0未订阅／1已订阅)
        public String fav;
        public String CommunityTitle;//标题
        public String CommunityDesc;//内容
        public String CommunityPic;//图片
        public String CreateTime;//创建时间
    public class PathemaType{
        public String PathemaTypeID;//疾病分类
        public String PathemaTypeName;//疾病分类
    }

        public String UserID;
        public String Likenum;//点赞数
        public String Comm;//评论数
        public String Send;//转发数

        public List<AnswerDetailConcerned> concerned;

        public List<AnswerDetailConcerned> getConcerned() {
        return concerned;
    }
}
