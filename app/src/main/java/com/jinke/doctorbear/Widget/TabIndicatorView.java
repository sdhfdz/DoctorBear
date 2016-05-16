package com.jinke.doctorbear.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.doctorbear.R;

/**
 * 设置TabHost的图标和文字
 * Created by QZ on 2016/5/12.
 */
public class TabIndicatorView extends RelativeLayout {
    private ImageView ivTabIcon;
    private TextView tvTabHint;

    private int normalIconId;
    private int focusIconId;

    public TabIndicatorView(Context context) {
        this(context,null);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.tab_indicator, this);
        ivTabIcon = (ImageView) findViewById(R.id.tab_indicator_icon);
        tvTabHint = (TextView) findViewById(R.id.tab_indicator_hint);
    }
    // 设置tab的title
    public void setTabTitle(String title) {
        tvTabHint.setText(title);
    }

    public void setTabTitle(int titleId) {
        tvTabHint.setText(titleId);
    }
    // 初始化图标
    public void setTabIcon(int normalIconId, int focusIconId) {
        this.normalIconId = normalIconId;
        this.focusIconId = focusIconId;

        ivTabIcon.setImageResource(normalIconId);
    }
    // 设置选中
    public void setTabSelected(boolean selected) {
        if (selected) {
            ivTabIcon.setImageResource(focusIconId);
            tvTabHint.setTextColor(this.getResources().getColor(R.color.colorPressed));
        } else {
            ivTabIcon.setImageResource(normalIconId);
            tvTabHint.setTextColor(this.getResources().getColor(R.color.colorNormal));
        }
    }
}
