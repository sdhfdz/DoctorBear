package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/5/30.
 */
public class HomeAnswerBean {
    public List<HomeAnswerValueBean> value;
    public String status;

    public List<HomeAnswerValueBean> getValue() {
        return value;
    }

    public void setValue(List<HomeAnswerValueBean> value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
