package com.example.news_android.DetailPage;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.NewsList.NewsListAdapter;
import com.example.news_android.R;

import java.util.ArrayList;


public class HistoryActivity extends AppCompatActivity {
    RecyclerView historyNewsView;
    TopView topView;
    NewsListAdapter newsListAdapter;
    NewsRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        topView = findViewById(R.id.history_top_view);
        historyNewsView = findViewById(R.id.history_news_recycler);
        topView.setTopViewTitle("历史记录");
        topView.setDefaultBackButtonListener(this);
        topView.setShareButtonVisibility(View.INVISIBLE);
        repo = new NewsRepo(this);
        ArrayList<News> historyNews = repo.getReadNewsList();
        newsListAdapter = new NewsListAdapter(historyNews);
        historyNewsView.setAdapter(newsListAdapter);
        historyNewsView.setLayoutManager(new LinearLayoutManager(this));
        historyNewsView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}