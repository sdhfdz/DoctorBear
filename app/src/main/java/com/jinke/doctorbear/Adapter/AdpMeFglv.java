package com.jinke.doctorbear.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinke.doctorbear.R;

/**
 * Created by sdh on 2016/5/17.
 */
public class AdpMeFglv extends BaseAdapter {
    private Context ctx;
    public AdpMeFglv(Context ctx) {
       this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        if (convertView==null){
            view=View.inflate(ctx, R.layout.fg_me_listitem,null);

        }else{
            view=convertView;
        }
        return view;
    }
}
