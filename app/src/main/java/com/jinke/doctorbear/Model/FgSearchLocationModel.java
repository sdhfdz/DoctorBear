package com.jinke.doctorbear.Model;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Max on 2016/5/23.
 */
public class FgSearchLocationModel {
    String locationNumber;
    String locationName;
    String locationDetail;
    boolean ifON;
    LatLng location;

    public FgSearchLocationModel(String locationNumber, String locationName, String locationDetail, LatLng location) {
        this.locationNumber = locationNumber;
        this.locationName = locationName;
        this.locationDetail = locationDetail;
        this.location = location;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public boolean isIfON() {
        return ifON;
    }

    public void setIfON(boolean ifON) {
        this.ifON = ifON;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
