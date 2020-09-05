package com.example.news_android.NewsList;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_android.DataBase.Entity;
import com.example.news_android.DataBase.EpidemicData;
import com.example.news_android.DataBase.EpidemicRepo;
import com.example.news_android.R;

import java.util.ArrayList;
import java.util.Calendar;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class EntityListAdapter extends RecyclerView.Adapter<EntityListAdapter.EntityViewHolder>
{

    ArrayList<Entity>entities;
    public EntityListAdapter(ArrayList<Entity>entities)
    {
        this.entities=entities;
    }

    @NonNull
    @Override
    public EntityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entity_item, parent, false);
        return new EntityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntityViewHolder holder, int position)
    {
       Entity entity=entities.get(position);
       holder.tvInfo.setText(entity.baidu+entity.zhwiki+entity.enwiki);
       holder.tvTitle.setText(entity.label);
    }

    @Override
    public int getItemCount()
    {
        if(entities==null)return 0;
        return entities.size();
    }

    static class EntityViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle,tvInfo;
        public EntityViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_entity_title);
            tvInfo=itemView.findViewById(R.id.tv_entity_info);
        }
    }
}
