package com.example.news_android;

import android.view.Window;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.*;
import java.io.*;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity
{
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
        JsonGetter jsonGetter = new ExpertJsonGetter(expertURL);
        //jsonGetter.execute();

        //UI init

    }

}
