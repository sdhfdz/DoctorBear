package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * 问答详情页面
 * Created by Max on 2016/6/6.
 */
public class AnswerDetailBean {
    public AnswerDeatilValueBean value;
    public List<AnswerDetailComment> Comment;

    public AnswerDeatilValueBean getValue() {
        return value;
    }

    public static class AnswerDetailComment {
        public User User;

        public static class User {
            public String UserIcon;
            public String UserName;
            public String UserTypeID;

        }

        public String Content;
        public String CreateTime;
    }

}
