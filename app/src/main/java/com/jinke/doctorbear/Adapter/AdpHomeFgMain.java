package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.ScrollListView;

import java.util.ArrayList;

/**
 * 主界面ViewPager适配器，只有两个界面
 * 一个问答界面，一个科普界面
 * Created by Max on 2016/5/16.
 */
public class AdpHomeFgMain  extends PagerAdapter {
    private Context context;
    public ArrayList<FgHomeAnswerModel> listAnswer;
    public ArrayList<FgHomeExpertModel> listExpert;
    public AdpHomeFgMain(Context context) {
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

            view = LayoutInflater.from(context).inflate(R.layout.fg_home_answer, null);
            ListView listView_answer = (ListView) view.findViewById(R.id.fg_home_answer_lv);

            //给listview添加数据
            listAnswer = new ArrayList<FgHomeAnswerModel>();
            for(int i=0;i<10;i++){
                FgHomeAnswerModel fgHomeAnswerModel = new FgHomeAnswerModel("http://images.3158.cn/data/attachment/jiangsu/article/2014/02/12/e28fe78c7a1393a1700453513d8ec4fb.jpg",
                        "小小飞行家","腹膜后及后腹腔巨大占位",i+"分钟前",
                        "患者性别：女  患者年龄：70  简要病史：发现血糖升高10余年，反复双下肢中度凹陷水肿半年，辅助检查：腹部CT",
                        "神经科",i+"");
                listAnswer.add(fgHomeAnswerModel);
            }
            listView_answer.setAdapter(new AdpHomeFgAnswer(view.getContext(),listAnswer));
        }
        //科普界面
        if (position==1){
            view = LayoutInflater.from(context).inflate(R.layout.fg_home_expert, null);
            ScrollListView listView_expert = (ScrollListView) view.findViewById(R.id.fg_home_expert_lv);
            ViewPager viewPager_expert = (ViewPager)view.findViewById(R.id.fg_home_expert_vp);

            //给listview添加数据
            listExpert = new ArrayList<FgHomeExpertModel>();
            for(int i=0;i<10;i++){
                FgHomeExpertModel fgHomeExpertModel = new FgHomeExpertModel("http://images.3158.cn/data/attachment/jiangsu/article/2014/02/12/e28fe78c7a1393a1700453513d8ec4fb.jpg","小小飞行家",i+"分钟前","腹膜后及后腹腔巨大占位", "", i+"",i+"");
                listExpert.add(fgHomeExpertModel);
            }

            viewPager_expert.setAdapter(new AdpHomeFgVpExpert(view.getContext()));
            listView_expert.setAdapter(new AdpHomeFgExpert(view.getContext(),listExpert));

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
