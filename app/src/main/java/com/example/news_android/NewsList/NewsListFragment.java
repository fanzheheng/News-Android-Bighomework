package com.example.news_android.NewsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.R;
import com.example.news_android.Utils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class NewsListFragment extends Fragment
{
    final String className;
    RecyclerView newsListView;
    protected JsonGetter jsonGetter;
    ArrayList<News> newsList;
    String type=null;
    protected NewsListFragment(String className)
    {
        this.className = className;
        newsList = new ArrayList<News>();
    }

    protected NewsListFragment(String className,String type)
    {
        this.className = className;
        newsList = new ArrayList<News>();
        this.type=type;
    }

    public String getClassName()
    {
        return className;
    }

    public static NewsListFragment newInstance(String className)
    {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment(className);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        System.out.println(className + "CreateView!!");
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        NewsRepo repo=new NewsRepo(getContext());
        if(type!=null)
        {
            newsList=repo.getNewsListByType(type);
        }

        final NewsListAdapter adapter=new NewsListAdapter(newsList);
        //init refreshLayout
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener()
        {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout)
            {
                //refresh news
                System.out.println("refresh");
                JsonGetter.JsonGetterFinishListener listener=new JsonGetter.JsonGetterFinishListener()
                {
                    @Override
                    public void OnFinish()
                    {
                        NewsRepo repo=new NewsRepo(getContext());
                        newsList = repo.getNewsListByType(type);
                        adapter.setNewsArrayList(newsList);
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh(1000, jsonGetter.getResult(), !jsonGetter.getResult());
                    }
                };
                jsonGetter=Utils.UpdateNewsDatabase(getContext(),listener,true,type);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener()
        {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout)
            {
                //load more news
                System.out.println("load more");
                JsonGetter.JsonGetterFinishListener listener=new JsonGetter.JsonGetterFinishListener()
                {
                    @Override
                    public void OnFinish()
                    {
                        NewsRepo repo=new NewsRepo(getContext());
                        newsList = repo.getNewsListByType(type);
                        adapter.setNewsArrayList(newsList);
                        adapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore(1000, jsonGetter.getResult(), !jsonGetter.getResult());
                    }
                };
                jsonGetter=Utils.UpdateNewsDatabase(getContext(),listener,false,type);
            }
        });

        //newsListView init
        newsListView = view.findViewById(R.id.news_list_view);

        newsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsListView.setAdapter(adapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

}
