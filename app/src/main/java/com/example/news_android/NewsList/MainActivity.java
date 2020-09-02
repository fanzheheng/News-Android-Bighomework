package com.example.news_android.NewsList;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
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
        String[] classNames = new String[]{"event", "paper", "news"};
        for(String className : classNames) {
            mFragmensts.add(NewsListFragment.newInstance(className));
        }
        mViewPager.setAdapter(new NewsClassFragmentPagerAdapter(getSupportFragmentManager(), mFragmensts));

    }

}

