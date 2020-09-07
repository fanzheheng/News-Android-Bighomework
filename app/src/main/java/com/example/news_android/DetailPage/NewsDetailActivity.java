package com.example.news_android.DetailPage;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.R;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity
{

    TextView tvTitle,tvContent,tvSource,tvTime;
    TableRow tbvEntities;
    TopView topView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        tvContent=findViewById(R.id.tv_news_content);
        tvTime=findViewById(R.id.tv_news_time);
        tvTitle=findViewById(R.id.tv_news_title);
        tvSource=findViewById(R.id.tv_news_source);
        tbvEntities=findViewById(R.id.tbv_news_entites);
        topView = findViewById(R.id.news_top_view);

        topView.setDefaultBackButtonListener(this);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(News._idKey);
        NewsRepo repo=new NewsRepo(this);
        News news=repo.getNewsById(id);
        if(news!=null)
        {
            tvTitle.setText(news.title);
            tvTime.setText(news.time);
            if(news.source != null) {
                tvSource.setText(news.source);
            } else {
                tvSource.setVisibility(View.GONE);
            }
            tvContent.setText(news.content);
            ArrayList<String>entities=news.entities;
            if(entities.size()==0)
            {
                TextView tvEntitiesTitle=findViewById(R.id.tv_news_entities_title);
                tvEntitiesTitle.setText("");
            }
            tbvEntities.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            for(int i=0;i<entities.size();i++)
            {
                TextView tv=new TextView(this);
                tv.setText(entities.get(i));
                tv.setBackgroundResource(R.drawable.gray_rect);
                tv.setTextColor(Color.BLACK);
                tv.setPadding(40 ,20, 40, 20);
                tv.setTextSize(25);
                TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20,20,20,20);
                tv.setLayoutParams(layoutParams);
                tbvEntities.addView(tv);
            }



        }
    }
}