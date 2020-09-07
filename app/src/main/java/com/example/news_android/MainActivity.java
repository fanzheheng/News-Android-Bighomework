package com.example.news_android;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.example.news_android.DataBase.Entity;
import com.example.news_android.DataBase.EntityRepo;
import com.example.news_android.DataBase.EpidemicData;
import com.example.news_android.DataBase.EpidemicRepo;
import com.example.news_android.DataBase.Expert;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.DataBase.ImageRepo;
import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.DetailPage.EntityDetailActivity;
import com.example.news_android.DetailPage.NewsDetailActivity;
import com.google.android.material.tabs.TabLayout;
import com.example.news_android.SearchPage.SearchPageActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class MainActivity extends AppCompatActivity
{
    public void printImageDB()
    {
        ImageRepo repo = new ImageRepo(this);
        repo.clearTable();
        ArrayList<String> urlList = repo.getImageURLList();
        for (String s : urlList) {
            System.out.println(s);
        }
        System.out.println("_________");
    }

    public void printEntityDB()
    {
        EntityRepo repo = new EntityRepo(this);
        repo.clearTable();
        ArrayList<Entity> list = repo.getEntityList();
        for (Entity entity : list) {
            System.out.println(entity.label);
        }
        System.out.println("_________");
    }

    public void printEpidemicDB()
    {
        EpidemicRepo repo=new EpidemicRepo(this);
        repo.clearTable();
        ArrayList<EpidemicData> list = repo.getEpidemicList();
        for (EpidemicData epidemicData : list) {
            System.out.println(epidemicData.district);
        }
        System.out.println("_________");
    }

    public void printExpertDB()
    {

        ExpertRepo repo=new ExpertRepo(this);
        repo.clearTable();
        ArrayList<Expert> list = repo.getExpertList();
        for (Expert expert : list) {
            System.out.println(expert.nameZh);
        }
        System.out.println("_________");
    }

    TextView searchBar;



    public void printNewsDB()
    {
        NewsRepo repo=new NewsRepo(this);
        repo.clearTable();
        ArrayList<News> list = repo.getNewsList();
        for (News news:list) {
            System.out.println(news._id);
        }
        System.out.println("_________");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestPermission(new String[]
                {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                });

        setContentView(R.layout.activity_main);

        printImageDB();
        printEntityDB();
        printEpidemicDB();
        printExpertDB();
        printNewsDB();

        JsonGetter jsonGetter=new NewsEventJsonGetter(Utils.newsEventURL,this);
        jsonGetter.execute();


        searchBar = findViewById(R.id.search_bar_text);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPageActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, searchBar, "search_bar").toBundle());
            }
        });
//        Intent intent=new Intent(this, NewsDetailActivity.class);
//        intent.putExtra(News._idKey,"5f560a119fced0a24b39257c");
//        startActivity(intent);



        OnekeyShare oks = new OnekeyShare();
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
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    public void requestPermission(String[] permissions)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            // 检查权限是否获取（android6.0及以上系统可能默认关闭权限，且没提示）
            PackageManager pm = getPackageManager();
            List<String> list = new LinkedList<>();
            for (String permission : permissions) {
                if (pm.checkPermission(permission, getPackageName()) == PackageManager.PERMISSION_DENIED) {
                    Log.e("lzh", permission + ": PERMISSION_DENIED");
                    list.add(permission);
                } else {
                    Log.e("lzh", permission + ": good");
                }
            }
            if (list.size() != 0)
            {
                requestPermissions(list.toArray(new String[list.size()]), 100);
            }
        }
    }


}


