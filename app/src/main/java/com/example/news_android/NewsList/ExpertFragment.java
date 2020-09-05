package com.example.news_android.NewsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.news_android.DataBase.EpidemicRepo;
import com.example.news_android.DataBase.Expert;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.R;
import com.example.news_android.Utils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpertFragment extends NewsListFragment
{

    protected ExpertFragment(String className)
    {
        super(className);
    }

    public static ExpertFragment newInstance(String className)
    {
        ExpertFragment fragment = new ExpertFragment(className);
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

        final String[][] ids = {{}};
        final ExpertRepo repo=new ExpertRepo(getContext());
        final ArrayList<String>[] idList = new ArrayList[]{repo.getAllExpertId()};//now we get all expert id (dead or alive)
        ids[0] = idList[0].toArray(new String[0]);
        final ExpertListAdapter expertListAdapter=new ExpertListAdapter(ids[0]);

        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //refresh news
                System.out.println("refresh");
                JsonGetter.JsonGetterFinishListener listener=new JsonGetter.JsonGetterFinishListener()
                {
                    @Override
                    public void OnFinish()
                    {
                        ArrayList<String>idList=repo.getAllExpertId();//now we get all expert id (dead or alive)
                        ids[0] =idList.toArray(new String[0]);
                        expertListAdapter.setIds(ids[0]);
                        expertListAdapter.notifyDataSetChanged();
                    }
                };
                Utils.UpdateExpertDatabase(getContext(),listener);
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


        newsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsListView.setAdapter(expertListAdapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }
}