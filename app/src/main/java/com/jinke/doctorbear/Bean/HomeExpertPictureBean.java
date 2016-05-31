package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/5/30.
 */
public class HomeExpertPictureBean {

    public List<HomeExpertPictureValue> value;
    public String status;

    public List<HomeExpertPictureValue> getValues() {
        return value;
    }

    public void setValues(List<HomeExpertPictureValue> values) {
        this.value = values;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public static class HomeExpertPictureValue{
        public String ArticleID;    //科普文章ID
        public String ArticlePic;   //科普文章图片

        public String getArticleID() {
            return ArticleID;
        }

        public void setArticleID(String articleID) {
            ArticleID = articleID;
        }

        public String getArticlePic() {
            return ArticlePic;
        }

        public void setArticlePic(String articlePic) {
            ArticlePic = articlePic;
        }
    }
}
