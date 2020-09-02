package com.example.news_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity
{

    void printNewsDB()
    {
        NewsRepo newsRepo=new NewsRepo(this);
        ArrayList<News>newsList=newsRepo.getNewsList();
        System.out.println("SIZE IS : "+newsList.size());
        for(int i=0;i<newsList.size();i++)
        {
            System.out.println(newsList.get(i).title);
            System.out.println(newsList.get(i).content);
            System.out.println(newsList.get(i).date);
            System.out.println(newsList.get(i).type);
            for(int j=0;j<newsList.get(i).entities.size();j++)
            {
                System.out.println(newsList.get(i).entities.get(j));
            }

            for(int j=0;j<newsList.get(i).relatedEvents.size();j++)
            {
                System.out.println(newsList.get(i).relatedEvents.get(j));
            }

        }
    }
    void clearDB()
    {
        NewsRepo newsRepo=new NewsRepo(this);
        newsRepo.clearTable();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //clearDB();
        printNewsDB();


        JsonGetter jsonGetter = new NewsEventJsonGetter(Utils.newsEventURL,this);
        //JsonGetter jsonGetter = new NewsContentJsonGetter(newsContentURL);
        //JsonGetter jsonGetter = new EntityJsonGetter(entityURL);
        //JsonGetter jsonGetter = new ExpertJsonGetter(expertURL);
        jsonGetter.execute();
    }

}
