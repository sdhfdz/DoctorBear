package com.jinke.doctorbear.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sdh on 2016/5/23.
 */
public class GlobalAddress {
    private Context ctx;

    public static final String SERVER="http://192.168.1.100";
    public static final  String App_key="vnroth0krx3io";
    public static String sha1(String data)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }
    public static void setToken(String token,Context ctx){
        SharedPreferences sp=ctx.getSharedPreferences("tokenfile",Context.MODE_PRIVATE);
        sp.edit().putString("token",token).commit();
    }
    public static String getToken(Context ctx){
        SharedPreferences sp=ctx.getSharedPreferences("tokenfile",Context.MODE_PRIVATE);
        return  sp.getString("token","");
    }

}
