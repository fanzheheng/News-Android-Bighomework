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
import com.example.news_android.NewsTextPage.NewsTextActivity;
import com.example.news_android.R;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsTitleViewHolder> {
    String[] titles;
    //TODO
    public NewsListAdapter(String[] titles) {
        this.titles = titles;
    }

    @NonNull
    @Override
    public NewsTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_title, parent, false);
        return new NewsTitleViewHolder(view, true);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsTitleViewHolder holder, int position) {
        holder.textView.setText(titles[position]);
        if(holder.visited) {
            holder.textView.setTextColor(Color.rgb(180, 180, 180)); //gray
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open News Detail
                Context context = holder.itemView.getContext();
                context.startActivity(new Intent(context, NewsTextActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    static class NewsTitleViewHolder extends RecyclerView.ViewHolder {
        Boolean visited;        //Whether news has been read
        TextView textView;

        public NewsTitleViewHolder(@NonNull View itemView, boolean visited) {
            super(itemView);
            this.visited = visited;
            textView = itemView.findViewById(R.id.news_title);
        }
    }
}
