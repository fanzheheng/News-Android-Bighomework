package com.example.news_android.NewsList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_android.DataBase.News;
import com.example.news_android.DetailPage.NewsDetailActivity;
import com.example.news_android.R;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsTitleViewHolder> {
    ArrayList<News> newsArrayList;
    //TODO
    public NewsListAdapter(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public NewsTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsTitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsTitleViewHolder holder, int position) {
        final News news = newsArrayList.get(position);
        //set whether news have been visited
        holder.setVisited(news.date.equals(""));
        holder.textView.setText(news.title);
        if(holder.visited) {
            holder.textView.setTextColor(Color.rgb(180, 180, 180)); //gray
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open News Detail
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra(News._idKey, news._id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    static class NewsTitleViewHolder extends RecyclerView.ViewHolder {
        Boolean visited = false;        //Whether news has been read
        TextView textView;

        public void setVisited(Boolean visited) {
            this.visited = visited;
        }

        public NewsTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.news_title);
        }
    }
}
