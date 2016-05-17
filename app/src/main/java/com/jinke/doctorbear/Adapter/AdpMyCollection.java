package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.ScrollListView;

/**
 * 我的收藏ViewPager适配器，只有两个界面
 * 一个问答界面，一个科普界面
 * Created by Max on 2016/5/16.
 */
public class AdpMyCollection extends PagerAdapter {
    private Context context;

    public AdpMyCollection(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 2;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        //问答界面
        if (position==0){

            view = LayoutInflater.from(context).inflate(R.layout.mycollection_answer, null);
            ListView listView_answer = (ListView) view.findViewById(R.id.mycollection_answer_lv);
            listView_answer.setAdapter(new AdpMyCollectionAnswer(view.getContext()));
        }
        //科普界面
        if (position==1){
            view = LayoutInflater.from(context).inflate(R.layout.mycollection_expert, null);
            ListView listView_expert = (ListView) view.findViewById(R.id.mycollection_expert_lv);
            listView_expert.setAdapter(new AdpMyCollectionExpert(view.getContext()));
        }
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
