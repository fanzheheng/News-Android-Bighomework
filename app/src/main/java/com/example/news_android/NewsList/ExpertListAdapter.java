package com.example.news_android.NewsList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_android.DataBase.Expert;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.DataBase.ImageDownloader;
import com.example.news_android.DataBase.ImageRepo;
import com.example.news_android.DetailPage.ExpertDetailActivity;
import com.example.news_android.R;

import java.util.ArrayList;

public class ExpertListAdapter extends RecyclerView.Adapter<ExpertListAdapter.ExpertViewHolder>
{
    ArrayList<String> ids;

    public void setIds(ArrayList<String> ids)
    {
        this.ids = ids;
        for(String s : ids) System.out.println(s);
        System.out.println("asdasdasdsd");
        notifyDataSetChanged();
    }

    public ExpertListAdapter(ArrayList<String> ids)
    {
        this.ids=ids;
    }

    @NonNull
    @Override
    public ExpertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expert_item, parent, false);
        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpertViewHolder holder, final int position)
    {
        final ExpertRepo repo=new ExpertRepo(holder.itemView.getContext());
        final ImageRepo imgRepo=new ImageRepo(holder.itemView.getContext());
        Expert expert=repo.getExpertById(ids.get(position));
        if(expert!=null)
        {
            holder.tvName.setText(expert.nameZh+" "+expert.name);
            holder.tvActivity.setText("活跃度："+expert.activity);
            holder.tvCitations.setText("引用数："+expert.citations);
            holder.tvGIndex.setText("G指数："+expert.gindex);
            holder.tvHIndex.setText("H指数："+expert.hindex);
            holder.tvDiversity.setText("多样性："+expert.diversity);
            holder.tvSociability.setText("社会性："+expert.sociability);
            Bitmap avatar=imgRepo.getImageByURL(expert.avatar);
            if(avatar!=null)
            {
                holder.ivAvatar.setImageBitmap(avatar);
            }
            else //re-download the image
            {
                ImageDownloader imgDownloader=new ImageDownloader(new ImageDownloader.OnImageLoaderListener()
                {
                    @Override
                    public void onError(ImageDownloader.ImageError error)
                    {

                    }

                    @Override
                    public void onProgressChange(int percent)
                    {

                    }

                    @Override
                    public void onComplete(Bitmap result, String imgUrl)
                    {
                        imgRepo.insert(imgUrl,result);
                        holder.ivAvatar.setImageBitmap(result);
                    }
                });
                imgDownloader.download(expert.avatar,false);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent=new Intent(v.getContext(), ExpertDetailActivity.class);
                    intent.putExtra(Expert.idKey,ids.get(position));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return ids.size();
    }

    static class ExpertViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvActivity,tvCitations,tvGIndex,tvHIndex,tvDiversity,tvSociability;
        ImageView ivAvatar;
        public ExpertViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_expert_name);
            tvActivity=itemView.findViewById(R.id.tv_expert_activity);
            tvCitations=itemView.findViewById(R.id.tv_expert_citations);
            tvGIndex=itemView.findViewById(R.id.tv_expert_gindex);
            tvHIndex=itemView.findViewById(R.id.tv_expert_hindex);
            tvDiversity=itemView.findViewById(R.id.tv_expert_diversity);
            tvSociability=itemView.findViewById(R.id.tv_expert_sociability);
            ivAvatar=itemView.findViewById(R.id.iv_expert_avatar);
        }
    }
}
