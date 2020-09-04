package com.example.news_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.LineChartView;


public class EpidemicDetailActivity extends AppCompatActivity
{

    LineChartView lcvEpidemic;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidemic_detail);
        lcvEpidemic=findViewById(R.id.lcv_epidemic);
        Bundle bundle = getIntent().getExtras();
        String country=bundle.getString(EpidemicData.countryKey);
        if(country==null)
        {

        }
        else if(country.equals("China"))//Chinese province data
        {
            String province=bundle.getString(EpidemicData.provinceKey);
            if(province!=null)
            {
                EpidemicRepo repo = new EpidemicRepo(this);
                ArrayList<EpidemicData> datas = repo.getEpidemicByProvince(province);
                for(int i=0;i<datas.size();i++)
                {

                }
            }
        }
        else //other country data
        {
            EpidemicRepo repo = new EpidemicRepo(this);
            ArrayList<EpidemicData>datas=repo.getEpidemicByCountry(country);
            for(int i=0;i<datas.size();i++)
            {
                
            }
        }
    }
}