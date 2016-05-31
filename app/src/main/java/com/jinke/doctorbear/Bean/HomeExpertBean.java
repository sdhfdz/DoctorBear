package com.jinke.doctorbear.Bean;

import java.util.List;

/**
 * Created by Max on 2016/5/30.
 */
public class HomeExpertBean {
    public List<HomeExpertValueBean> value;
    public String status;

    public List<HomeExpertValueBean> getValue() {
        return value;
    }

    public void setValue(List<HomeExpertValueBean> value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
