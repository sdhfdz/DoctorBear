package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/6/12.
 */
public class ExpertDetailBean {
    public ExpertDetailValueBean value;
    public List<ExpertDetailComment> Comment;


    public static class ExpertDetailComment {
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
