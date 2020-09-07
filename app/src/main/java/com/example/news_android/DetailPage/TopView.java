package com.example.news_android.DetailPage;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.news_android.R;


public class TopView extends RelativeLayout {
    Button backButton, shareButton;
    public TopView(Context context) {
        super(context);
    }
    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.topview_layout,this);
        backButton = findViewById(R.id.topview_back_button);
        shareButton = findViewById(R.id.topview_share_button);
    }
    public void setBackButtonListener(OnClickListener onClickListener) {
        backButton.setOnClickListener(onClickListener);
    }
    public void setShareButtonListener(OnClickListener onClickListener) {
        backButton.setOnClickListener(onClickListener);
    }
    public void setDefaultBackButtonListener(final Activity activity) {
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
    public void setDefaultShareButtonListener() {
        shareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
    }
}
