package com.example.news_android.NewsList.ClassChooser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_android.NewsList.NewsListFragment;
import com.example.news_android.R;

import java.util.List;

public class ClassGridAdapter extends RecyclerView.Adapter<ClassGridAdapter.NewsClassViewHolder> {
    List<NewsListFragment> classes;
    ClassGridAdapter anotherAdapter;
    OnClassChangeListener onClassChangeListener;
    public ClassGridAdapter(List<NewsListFragment> classes) {
        new ClassGridAdapter(classes, null);
    }
    public ClassGridAdapter(List<NewsListFragment> classes, OnClassChangeListener onClassChangeListener) {
        this.classes = classes;
        if(onClassChangeListener != null) {
            this.onClassChangeListener = onClassChangeListener;
            onClassChangeListener.onClassChange(classes.size());
        } else {
            this.onClassChangeListener = new OnClassChangeListener() { @Override public void onClassChange(int classNum) {}};
        }
    }

    public void setOnClassChangeListener(OnClassChangeListener onClassChangeListener) {
        this.onClassChangeListener = onClassChangeListener;
    }

    @NonNull
    @Override
    public NewsClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_name_textview, parent, false);
        return new NewsClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsClassViewHolder holder, final int position) {
        holder.textView.setText(classes.get(position).getClassName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anotherAdapter != null)
                    anotherAdapter.addData(removeData(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    private void addData(NewsListFragment className) {
        if(classes.size() == 0) {
            onClassChangeListener.onClassChange(1);
        }
        classes.add(className);
        notifyItemInserted(classes.size() - 1);
        //add animation
    }

    private NewsListFragment removeData(int position) {
        NewsListFragment s = classes.remove(position);
        //删除动画
        if(classes.size() == 0) {
            onClassChangeListener.onClassChange(0);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
        return s;
    }

    public void bondAdapter(ClassGridAdapter adapter) {
        anotherAdapter = adapter;
    }

    static class NewsClassViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public NewsClassViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.class_name_textview);
        }
    }
    public interface OnClassChangeListener {
        public void onClassChange(int classNum);
    }
}
