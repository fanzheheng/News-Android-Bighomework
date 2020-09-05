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
import com.example.news_android.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class NewsListFragment extends Fragment {
    final String className;
    RecyclerView newsListView;
    protected NewsListFragment(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public static NewsListFragment newInstance(String className) {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment(className);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println(className + "CreateView!!");
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        //init refreshLayout
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

        String[] titles = new String[]{className, "paper", "news", "class1", "football", "computer", "tsinghua"};
        NewsListAdapter newsListAdapter = new NewsListAdapter(titles);
        newsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsListView.setAdapter(newsListAdapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

}
