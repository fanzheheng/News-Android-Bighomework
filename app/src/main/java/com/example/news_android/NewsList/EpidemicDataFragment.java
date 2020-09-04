package com.example.news_android.NewsList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_android.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpidemicDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpidemicDataFragment extends NewsListFragment
{

    protected EpidemicDataFragment(String className)
    {
        super(className);
    }

    public static EpidemicDataFragment newInstance(String className)
    {
        EpidemicDataFragment fragment = new EpidemicDataFragment(className);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        System.out.println(className + "CreateView!!");
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //refresh news
                System.out.println("refresh");
                refreshLayout.finishRefresh(100);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //load more news
                System.out.println("load more");
                refreshLayout.finishLoadMore(100);
            }
        });
        //newsListView init
        newsListView = view.findViewById(R.id.news_list_view);

        String districts[]={"China","United States of America","Brazil"};
        EpidemicListAdapter epidemicAdapter=new EpidemicListAdapter(districts);
        newsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsListView.setAdapter(epidemicAdapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
        //return inflater.inflate(R.layout.fragment_epidemic_data, container, false);

    }
}