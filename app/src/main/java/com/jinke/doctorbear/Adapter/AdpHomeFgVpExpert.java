package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jinke.doctorbear.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * 科普界面ViewPager的适配器
 * Created by Max on 2016/5/16.
 */
public class AdpHomeFgVpExpert extends PagerAdapter {
    Context context;
    public ArrayList<String> img_url;
    public AdpHomeFgVpExpert(Context context ,ArrayList<String> img_url) {
        this.context = context;
        this.img_url = img_url;
    }

    @Override
    public int getCount() {
        return img_url.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.fg_home_expert_vp_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.fg_home_expert_vp_item_iv);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        Picasso.with(context).load(img_url.get(position)).resize(width,200).error(R.mipmap.logo).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}