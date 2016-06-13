package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/6/6.
 */
public class GeneralSearchBean {
    public List<GeneralSearchPathema> Pathema;
    public List<GeneralSearchMedicine> Medicine;
    public List<GeneralSearchHospital> Hospital;
    public List<GeneralSearchCommunity> Community;
    public List<GeneralSearchArticle> Article;

    public String status;

    public static class GeneralSearchPathema{
        public String PathemaID;//疾病ID
        public String PathemaName;//疾病名称
        public String PathemaTypeName;//疾病分类名称
    }
    public static class GeneralSearchMedicine{
        public String MedicineID;//药品ID
        public String MedicineName;//药品
    }
    public static class GeneralSearchHospital{
        public String HospitalID;//医院ID
        public String HospitalName;//医院
    }
    public static class GeneralSearchCommunity{
        public String CommunityID;//问答ID
        public String CommunityTitle;//问答标题
        public String PathemaTypeName;//疾病分类

    }
    public static class GeneralSearchArticle{
        public String ArticleID;//科普文章ID
        public String ArticleTitle;//科普文章标题
        public String PathemaTypeName;//疾病分类

    }
}
