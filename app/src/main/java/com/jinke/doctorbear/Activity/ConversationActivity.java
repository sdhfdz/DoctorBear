package com.jinke.doctorbear.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinke.doctorbear.R;

import io.rong.imkit.RongIM;

public class ConversationActivity extends AppCompatActivity {

    private TextView BarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
       String title= getIntent().getData().getQueryParameter("title");
        System.out.println("titile"+title+"???");
        BarTitle = (TextView) findViewById(R.id.title_bar_content);
        BarTitle.setText(title);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("lalllallal");
        finish();
    }
}
