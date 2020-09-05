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

import com.example.news_android.DataBase.Entity;
import com.example.news_android.DataBase.EntityRepo;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.R;
import com.example.news_android.Utils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import com.example.news_android.JsonGetter.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntityFragment extends NewsListFragment
{

    String searchInput;
    protected EntityFragment(String className,String searchInput)
    {
        super(className);
        this.searchInput=searchInput;
    }

    public static EntityFragment newInstance(String className,String searchInput)
    {
        EntityFragment fragment = new EntityFragment(className,searchInput);
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
        final ArrayList<Entity>[] entities = new ArrayList[]{new ArrayList<Entity>()};

        final EntityRepo repo=new EntityRepo(getContext());
        entities[0] =repo.getEntityBySearchInput(searchInput);
        if(entities[0]==null)
        {
            entities[0]=new ArrayList<Entity>();
        }
        final EntityListAdapter entityListAdapter=new EntityListAdapter(entities[0]);
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //refresh news
                System.out.println("refresh");
                entities[0] =repo.getEntityBySearchInput(searchInput);
                entityListAdapter.setEntities(entities[0]);
                entityListAdapter.notifyDataSetChanged();
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

        JsonGetterFinishListener listener=new JsonGetterFinishListener()
        {
            @Override
            public void OnFinish()
            {
                entities[0] =repo.getEntityBySearchInput(searchInput);
                entityListAdapter.setEntities(entities[0]);
                entityListAdapter.notifyDataSetChanged();
            }
        };

        Utils.UpdateEntityDatabase(getContext(),searchInput,listener);
        newsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsListView.setAdapter(entityListAdapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }
}