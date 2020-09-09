package com.example.news_android.DetailPage;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.news_android.R;

import cn.sharesdk.onekeyshare.OnekeyShare;


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
            public void onClick(View v) {         OnekeyShare oks = new OnekeyShare();
//关闭sso授权
                oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
                oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getContext().getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
                oks.show(getContext());}
        });
    }
}
