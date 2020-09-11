package com.example.news_android.NewsList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;
import com.example.news_android.JsonGetter;
import com.example.news_android.R;
import com.example.news_android.Utils;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventNewsFragment extends NewsListFragment
{

    protected EventNewsFragment(String className)
    {
        super(className);
    }

    public static EventNewsFragment newInstance(String className)
    {
        EventNewsFragment fragment = new EventNewsFragment(className);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    final String[] clusterNames = new String[]{"疫情信息", "病毒研究", "疫苗研发", "医疗突破"};
    final String[][] EventKeywords = new String[][] {
            new String[]{"疫情", "检测", "新冠", "肺炎", "研究", "中国", "感染", "美国"},
            new String[]{"病毒", "研究", "新冠", "发现", "感染", "细胞", "人员", "结构"},
            new String[]{"疫苗", "临床试验", "新冠", "重组", "期", "研究所", "研制", "动物"},
            new String[]{"患者", "研究", "治疗", "肺炎", "新冠", "重症", "临床", "发现"}
    };
    int clusterButtonIds[];
    NewsListAdapter newsListAdapter;
    EventKeywordsAdapter eventKeywordsAdapter;

    RefreshLayout refreshLayout;
    NewsRepo repo;
    RadioGroup radioGroup;
    int checkedType = 0;
    ArrayList<News> eventList;
    RecyclerView recyclerView, eventKeywordsRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_event, container, false);

        clusterButtonIds = new int[]{
                R.id.cluster0_radio,
                R.id.cluster1_radio,
                R.id.cluster2_radio,
                R.id.cluster3_radio
        };
        for(int i = 0; i < clusterButtonIds.length; i++) {
            final RadioButton radioButton = view.findViewById(clusterButtonIds[i]);
            radioButton.setText(clusterNames[i]);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(radioButton.isChecked())
                        refreshLayout.autoRefresh();
                }
            });
        }
        recyclerView = view.findViewById(R.id.event_list_view);
        refreshLayout = view.findViewById(R.id.event_refreshLayout);
        repo = new NewsRepo(getContext());
        eventList = new ArrayList<>();
        newsListAdapter = new NewsListAdapter(eventList);
        recyclerView.setAdapter(newsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                eventList = repo.getNewsByCluster(checkedType);
                newsListAdapter.setNewsArrayList(eventList);
                eventKeywordsAdapter.setKeywords(EventKeywords[checkedType]);
                recyclerView.scrollToPosition(recyclerView.getTop());
                newsListAdapter.notifyDataSetChanged();
                eventKeywordsAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        radioGroup = view.findViewById(R.id.event_radios);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for(int i = 0; i < clusterButtonIds.length; i++) {
                    if(checkedId == clusterButtonIds[i]) {
                        checkedType = i;
                    }
                }
                refreshLayout.autoRefresh();
            }
        });
        //add divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        eventKeywordsRecyclerView = view.findViewById(R.id.event_keywords);
        eventKeywordsAdapter = new EventKeywordsAdapter(EventKeywords[checkedType]);
        eventKeywordsRecyclerView.setAdapter(eventKeywordsAdapter);
        eventKeywordsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        return view;
    }
}

class EventKeywordsAdapter extends RecyclerView.Adapter<EventKeywordsAdapter.EventKeywordHolder> {
    String[] keywords;

    public void setKeywords(String[] keywords)
    {
        this.keywords = keywords;
    }
    public EventKeywordsAdapter(String[] keywords) {
        this.keywords = keywords;
    }

    @NonNull
    @Override
    public EventKeywordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_keyword_view, parent, false);
        return new EventKeywordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventKeywordHolder holder, int position) {
        holder.keywordText.setText(keywords[position]);
    }

    @Override
    public int getItemCount() {
        return keywords.length;
    }

    static class EventKeywordHolder extends RecyclerView.ViewHolder {
        TextView keywordText;
        public EventKeywordHolder(@NonNull View itemView) {
            super(itemView);
            keywordText = itemView.findViewById(R.id.class_name_textview);
        }
    }
}
