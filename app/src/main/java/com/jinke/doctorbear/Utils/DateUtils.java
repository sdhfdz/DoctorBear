package com.jinke.doctorbear.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  @author max
 */

public class DateUtils {
    private SimpleDateFormat sf = null;
    private SimpleDateFormat sdf = null;

    /*获取系统时间 格式为："yyyy/MM/dd "*/

    public String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*将字符串转为时间戳*/

    public long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
