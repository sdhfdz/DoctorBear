package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinke.doctorbear.Bean.HomeAnswerBean;
import com.jinke.doctorbear.Bean.HomeAnswerValueBean;
import com.jinke.doctorbear.Bean.HomeExpertBean;
import com.jinke.doctorbear.Bean.HomeExpertPictureBean;
import com.jinke.doctorbear.Bean.HomeExpertValueBean;
import com.jinke.doctorbear.Model.FgHomeAnswerModel;
import com.jinke.doctorbear.Model.FgHomeExpertModel;
import com.jinke.doctorbear.R;
import com.jinke.doctorbear.Utils.DateUtils;
import com.jinke.doctorbear.Utils.GetDataServer;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.jinke.doctorbear.Utils.ScrollListView;
import com.jinke.doctorbear.Utils.SwipeRefresh.SwipeRefreshLayout;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * 主界面ViewPager适配器，只有两个界面
 * 一个问答界面，一个科普界面
 * Created by Max on 2016/5/16.
 */
public class AdpHomeFgMain  extends PagerAdapter implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private Context context;
    private View view;

    public DateUtils dateUtils = new DateUtils();

    public ArrayList<FgHomeAnswerModel> listAnswer;
    public ArrayList<FgHomeExpertModel> listExpert;

    private AdpHomeFgAnswer adpHomeFgAnswer;
    private AdpHomeFgExpert adpHomeFgExper;
    private AdpHomeFgVpExpert adpHomeFgVpExpert;

    private ViewPager viewPager_expert;
    private ListView listView_answer;
    private ScrollListView listView_expert;
    private  SwipeRefreshLayout mSwipeLayout;

    private String[] img_id = new String[10];      //科普轮播图片id
    private ArrayList<String> img_url = new ArrayList<String>();      //科普轮播图片

    private GetDataServer getDataServer = new GetDataServer();

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

        view = null;
        //问答界面
        if (position==0){

            view = LayoutInflater.from(context).inflate(R.layout.fg_home_answer, null);
            listView_answer = (ListView) view.findViewById(R.id.fg_home_answer_lv);


            //从服务器给listview添加数据
            listAnswer = new ArrayList<FgHomeAnswerModel>();
            adpHomeFgAnswer = new AdpHomeFgAnswer(view.getContext(),listAnswer);
            getDataServer.getAnswerFromServer(context,listAnswer,listView_answer,adpHomeFgAnswer);

            mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_answer_swipe_container);
            mSwipeLayout.setOnRefreshListener(this);
            mSwipeLayout.setOnLoadListener(this);
            mSwipeLayout.setColor(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            mSwipeLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
            mSwipeLayout.setLoadNoFull(false);
        }
        //科普界面
        if (position==1){
            view = LayoutInflater.from(context).inflate(R.layout.fg_home_expert, null);
            listView_expert = (ScrollListView) view.findViewById(R.id.fg_home_expert_lv);
            viewPager_expert = (ViewPager)view.findViewById(R.id.fg_home_expert_vp);

            //给listview添加数据
            listExpert = new ArrayList<FgHomeExpertModel>();

            //从服务器添加轮播图片
            adpHomeFgVpExpert = new AdpHomeFgVpExpert(context,img_url);
            getDataServer.getExpertPictureFromServer(context,img_url,adpHomeFgVpExpert,viewPager_expert);

            //从服务器给listview添加数据
            adpHomeFgExper = new AdpHomeFgExpert(context,listExpert);
            getDataServer.getExpertFromServer(context,listExpert,listView_expert,adpHomeFgExper);

        }
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public void onRefresh() {
       // values.add(0, "Add " + values.size());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
           //     mListAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onLoad() {
        //values.add("Add " + values.size());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setLoading(false);
          //      mListAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }



}
