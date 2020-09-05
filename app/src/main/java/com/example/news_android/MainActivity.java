package com.example.news_android;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

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
import com.example.news_android.NewsList.EpidemicDataFragment;
import com.example.news_android.NewsList.ExpertFragment;
import com.example.news_android.NewsList.NewsClassFragmentPagerAdapter;
import com.example.news_android.NewsList.NewsListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private ViewPager mViewPager;
    private TabLayout mTablayout;

    public void printImageDB()
    {
        ImageRepo repo = new ImageRepo(this);
        ArrayList<String> urlList = repo.getImageURLList();
        for (int i = 0; i < urlList.size(); i++)
        {
            System.out.println(urlList.get(i));
        }
        System.out.println("_________");
    }

    public void printEntityDB()
    {
        EntityRepo repo = new EntityRepo(this);
        ArrayList<Entity> list = repo.getEntityList();
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).label);
        }
        System.out.println("_________");
    }

    public void printEpidemicDB()
    {
        EpidemicRepo repo=new EpidemicRepo(this);
        ArrayList<EpidemicData> list = repo.getEpidemicList();
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).district);
        }
        System.out.println("_________");
    }

    public void printExpertDB()
    {
        ExpertRepo repo=new ExpertRepo(this);
        ArrayList<Expert> list = repo.getExpertList();
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).nameZh);
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
        //JsonGetter jsonGetter = new NewsEventJsonGetter(Utils.newsEventURL,this);
        //JsonGetter jsonGetter = new NewsContentJsonGetter(Utils.newsContentURL,this);
        //JsonGetter jsonGetter = new EntityJsonGetter(Utils.entityURL, this);
        JsonGetter jsonGetter = new ExpertJsonGetter(Utils.expertURL,this);
        //JsonGetter jsonGetter=new EpidemicDataJsonGetter(Utils.countryURL,this);
        jsonGetter.execute();

        //viewPager
        mViewPager = findViewById(R.id.viewPager);
        mTablayout = findViewById(R.id.class_tab_layout);
        mTablayout.setupWithViewPager(mViewPager);



        //String[] classNames = new String[]{"event"};
//        for (String className : classNames)
//        {
//            mFragmensts.add(NewsListFragment.newInstance(className));
//        }
//        mFragmensts.add(EpidemicDataFragment.newInstance("Epidemic Data"));
//        mFragmensts.add(ExpertFragment.newInstance("Expert List"));

        //mViewPager.setAdapter(new NewsClassFragmentPagerAdapter(getSupportFragmentManager(), mFragmensts));

//        Intent intent=new Intent(this,ExpertDetailActivity.class);
//        intent.putExtra(Expert.idKey,"53f4495cdabfaeb22f4cc34d");
//        startActivity(intent);
    }

    public boolean requestPermission(String[] permissions)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            // 检查权限是否获取（android6.0及以上系统可能默认关闭权限，且没提示）
            PackageManager pm = getPackageManager();
            List<String> list = new LinkedList<>();
            for (int i = 0; i < permissions.length; i++)
            {
                if (pm.checkPermission(permissions[i], getPackageName()) == PackageManager.PERMISSION_DENIED)
                {
                    Log.e("lzh", permissions[i] + ": PERMISSION_DENIED");
                    list.add(permissions[i]);
                } else
                {
                    Log.e("lzh", permissions[i] + ": good");
                }
            }
            if (list.size() != 0)
            {
                requestPermissions(list.toArray(new String[list.size()]), 100);
                return false;
            } else
            {
                return true;
            }
        } else
        {
            return true;
        }
    }


}


