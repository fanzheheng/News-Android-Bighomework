package com.example.news_android.NewsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.R;
import com.example.news_android.Utils;
import com.scwang.smart.refresh.header.ClassicsHeader;
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

    RefreshLayout refreshLayout;
    RadioButton aliveExpertRadio, deadExpertRadio;
    ArrayList<String> aliveExperts = new ArrayList<>();
    ArrayList<String> deadExperts = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        System.out.println(className + "CreateView!!");
        final View view = inflater.inflate(R.layout.fragment_expert_list, container, false);
        final ExpertRepo repo = new ExpertRepo(getContext());
        final ExpertListAdapter expertListAdapter=new ExpertListAdapter(new ArrayList<String>());
        final RadioGroup radioGroup = view.findViewById(R.id.expert_radios);
        aliveExpertRadio = view.findViewById(R.id.alive_expert_radio);
        deadExpertRadio = view.findViewById(R.id.dead_expert_radio);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                //refresh news
                System.out.println("refresh");
                aliveExpertRadio.setClickable(false);
                deadExpertRadio.setClickable(false);
                JsonGetter.JsonGetterFinishListener refreshFinishListener = new JsonGetter.JsonGetterFinishListener()
                {
                    @Override
                    public void OnFinish()
                    {
                        int checkedId = radioGroup.getCheckedRadioButtonId();
                        System.out.println(checkedId);
                        if(checkedId == R.id.alive_expert_radio) {
                            expertListAdapter.setIds(repo.getAliveExpertId());
                        } else {
                            expertListAdapter.setIds(repo.getDeadExpertId());
                        }
                        expertListAdapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh(0, jsonGetter.getResult(), !jsonGetter.getResult());
                        aliveExpertRadio.setClickable(true);
                        deadExpertRadio.setClickable(true);
                    }
                };
                if(expertListAdapter.getItemCount() == 0) {
                    jsonGetter = Utils.UpdateExpertDatabase(getContext(),refreshFinishListener);
                } else {
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    System.out.println(checkedId);
                    if(checkedId == R.id.alive_expert_radio) {
                        if(aliveExperts.isEmpty()) {
                            aliveExperts = repo.getAliveExpertId();
                        }
                        expertListAdapter.setIds(aliveExperts);
                    } else {
                        if(deadExperts.isEmpty()) {
                            deadExperts = repo.getDeadExpertId();
                        }
                        expertListAdapter.setIds(deadExperts);
                    }
                    expertListAdapter.notifyDataSetChanged();
                    aliveExpertRadio.setClickable(true);
                    deadExpertRadio.setClickable(true);
                    refreshLayout.finishRefresh();
                }
            }
        });
        aliveExpertRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.autoRefresh();
            }
        });
        deadExpertRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.autoRefresh();
            }
        });
        radioGroup.setClickable(false);

        //refreshLayout.autoRefresh();
        refreshLayout.setEnableLoadMore(false);
        //newsListView init
        newsListView = view.findViewById(R.id.news_list_view);
        newsListView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsListView.setAdapter(expertListAdapter);
        //add divider
        newsListView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }
}