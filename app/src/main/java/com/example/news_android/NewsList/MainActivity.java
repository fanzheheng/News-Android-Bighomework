package com.example.news_android.NewsList;


import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.news_android.NewsList.NewsClassFragmentPagerAdapter;
import com.example.news_android.NewsList.NewsListFragment;
import com.example.news_android.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private List<NewsListFragment> mFragmensts = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String countryURL = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
        String newsEventURL="https://covid-dashboard.aminer.cn/api/dist/events.json";
        String newsContentURL="https://covid-dashboard.aminer.cn/api/event/5f05f3f69fced0a24b2f84ee";//this one is just for test
        String entityURL="https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=病毒";//this one is just for test
        String expertURL="https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

        //JsonGetter jsonGetter = new NewsEventJsonGetter(newsEventURL);
        //JsonGetter jsonGetter = new NewsContentJsonGetter(newsContentURL);
        //JsonGetter jsonGetter = new EntityJsonGetter(entityURL);
        //JsonGetter jsonGetter = new ExpertJsonGetter(expertURL);
        //jsonGetter.execute();

        //viewPager
        mViewPager = findViewById(R.id.viewPager);
        mTablayout = findViewById(R.id.class_tab_layout);
        mTablayout.setupWithViewPager(mViewPager);
        String[] classNames = new String[]{"event", "paper", "news", "class1", "football", "computer", "tsinghua"};
        for(String className : classNames) {
            mFragmensts.add(NewsListFragment.newInstance(className));
        }
        mViewPager.setAdapter(new NewsClassFragmentPagerAdapter(getSupportFragmentManager(), mFragmensts));

    }

}

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.URL;
//import java.net.*;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//
//public class MainActivity extends AppCompatActivity
//{
//
//    void printNewsDB()
//    {
//        NewsRepo newsRepo=new NewsRepo(this);
//        ArrayList<News>newsList=newsRepo.getNewsList();
//        for(int i=0;i<newsList.size();i++)
//        {
//            System.out.println(newsList.get(i).title);
//        }
//    }
//    void clearDB()
//    {
//        NewsRepo newsRepo=new NewsRepo(this);
//        newsRepo.clearTable();
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        printNewsDB();
//        String countryURL = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";
//        String newsEventURL="https://covid-dashboard.aminer.cn/api/dist/events.json";
//        String newsContentURL="https://covid-dashboard.aminer.cn/api/event/5f05f3f69fced0a24b2f84ee";//this one is just for test
//        String entityURL="https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=病毒";//this one is just for test
//        String expertURL="https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";
//
//        JsonGetter jsonGetter = new NewsEventJsonGetter(newsEventURL,this);
//        //JsonGetter jsonGetter = new NewsContentJsonGetter(newsContentURL);
//        //JsonGetter jsonGetter = new EntityJsonGetter(entityURL);
//        //JsonGetter jsonGetter = new ExpertJsonGetter(expertURL);
//        jsonGetter.execute();
//    }
//
//}
