package com.jinke.doctorbear.Utils;

import com.baidu.mapapi.map.BaiduMap;

/**
 * Created by Max on 2016/5/23.
 */
public class MyPoiOverlay extends PoiOverlay {
    public MyPoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }
    @Override
    public boolean onPoiClick(int index) {
        super.onPoiClick(index);
        return true;
    }

}
