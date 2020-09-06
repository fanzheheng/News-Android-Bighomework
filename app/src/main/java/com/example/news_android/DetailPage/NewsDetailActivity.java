package com.example.news_android.DetailPage;

import androidx.appcompat.app.AppCompatActivity;

import com.example.news_android.DataBase.Expert;
import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.R;

import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity
{

    TextView tvTitle,tvContent,tvSource,tvTime;
    TableRow tbvEntities;
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


        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(News._idKey);
        NewsRepo repo=new NewsRepo(this);
        News news=repo.getNewsById(id);
        if(news!=null)
        {
            tvTitle.setText(news.title);
            tvTime.setText(news.time);
            tvSource.setText(news.source);
            tvContent.setText(news.source);
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
                tv.setPadding(3,3,3,3);
                tv.setBackgroundColor(Color.GRAY);
                tv.setTextColor(Color.BLACK);
                TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5,5,5,5);
                tv.setLayoutParams(layoutParams);
                tbvEntities.addView(tv);
            }
//            TextView tv=new TextView(this);
//            tv.setText("FFFF ");
//            tbvEntities.addView(tv);



        }
    }
}