package com.jinke.doctorbear.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by sdh on 2016/5/23.
 */
public class GlobalAddress {
    private Context ctx;
    private static Platform weibo=  ShareSDK.getPlatform(SinaWeibo.NAME);
    public static final String SERVER="http://172.27.35.5";
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
    public static String getUserId(Context ctx){
        if (weibo.getDb().getUserId()!=null && !weibo.getDb().getUserId().equals("")){
            return weibo.getDb().getUserId();
        }else{
            SharedPreferences sp=ctx.getSharedPreferences("user_info",Context.MODE_PRIVATE);
            return  sp.getString("id","");
        }
    }
    public static String getUserIcon(Context ctx){
        if (weibo.getDb().getUserIcon()!=null && !weibo.getDb().getUserIcon().equals("")){
            return weibo.getDb().getUserIcon();
        }else{
            SharedPreferences sp=ctx.getSharedPreferences("user_info",Context.MODE_PRIVATE);
            return  sp.getString("profile_image_url","");
        }
    }
    public static String getUserName(Context ctx){
        if (weibo.getDb().getUserName()!=null && !weibo.getDb().getUserName().equals("")){
            return weibo.getDb().getUserName();
        }else{
            SharedPreferences sp=ctx.getSharedPreferences("user_info",Context.MODE_PRIVATE);
            return  sp.getString("name","");
        }
    }



}
