package com.example.news_android.NewsList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_android.DataBase.EpidemicRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.R;
import com.example.news_android.Utils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EpidemicDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EpidemicDataFragment extends NewsListFragment
{
    RefreshLayout refreshLayout;

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

        final String[][] districts = {{}};
        final EpidemicRepo repo=new EpidemicRepo(this.getContext());

        ArrayList<String>provinces=repo.getAllChineseProvinceName();
        ArrayList<String>countries=repo.getAllCountryName();
        for(int i=0;i<provinces.size();i++)
        {
            provinces.set(i,"China|"+provinces.get(i));
        }
        provinces.addAll(countries);
        districts[0] =provinces.toArray(new String[0]);
        final EpidemicListAdapter epidemicAdapter=new EpidemicListAdapter(districts[0]);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                //refresh news
                System.out.println("refresh");

                JsonGetter.JsonGetterFinishListener listener=new JsonGetter.JsonGetterFinishListener()
                {
                    @Override
                    public void OnFinish()
                    {
                        ArrayList<String>provinces=repo.getAllChineseProvinceName();
                        ArrayList<String>countries=repo.getAllCountryName();
                        for(int i=0;i<provinces.size();i++)
                        {
                            provinces.set(i,"China|"+provinces.get(i));
                        }
                        provinces.addAll(countries);
                        districts[0] =provinces.toArray(new String[0]);
                        epidemicAdapter.setDistricts(districts[0]);
                        epidemicAdapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                    }
                };
                Utils.UpdateEpidemicDatabase(getContext(),listener);
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
        newsListView.setAdapter(epidemicAdapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }
}