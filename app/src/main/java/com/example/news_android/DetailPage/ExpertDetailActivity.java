package com.example.news_android.DetailPage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news_android.DataBase.Expert;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.DataBase.ImageDownloader;
import com.example.news_android.DataBase.ImageRepo;
import com.example.news_android.R;

public class ExpertDetailActivity extends AppCompatActivity
{

    TextView tvName,tvActivity,tvCitations,tvGIndex,tvHIndex,tvDiversity,tvSociability,tvPosition,tvAffiliation,tvEdu,tvBio;
    ImageView ivAvatar;
    TopView topView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_detail);

        tvName=findViewById(R.id.tv_expert_name);
        tvActivity=findViewById(R.id.tv_expert_activity);
        tvCitations=findViewById(R.id.tv_expert_citations);
        tvGIndex=findViewById(R.id.tv_expert_gindex);
        tvHIndex=findViewById(R.id.tv_expert_hindex);
        tvDiversity=findViewById(R.id.tv_expert_diversity);
        tvSociability=findViewById(R.id.tv_expert_sociability);
        tvPosition=findViewById(R.id.tv_expert_position);
        tvAffiliation=findViewById(R.id.tv_expert_affiliation);
        tvEdu=findViewById(R.id.tv_expert_edu);
        tvBio=findViewById(R.id.tv_expert_bio);
        ivAvatar=findViewById(R.id.iv_expert_avatar);
        topView = findViewById(R.id.expert_top_view);

        topView.setDefaultBackButtonListener(this);
        topView.setDefaultShareButtonListener();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString(Expert.idKey);
        if (id != null)
        {
            ExpertRepo repo = new ExpertRepo(this);
            final ImageRepo imgRepo=new ImageRepo(this);
            Expert expert = repo.getExpertById(id);
            if (expert != null)
            {
                tvName.setText(expert.nameZh+" "+expert.name);
                tvActivity.setText("活跃度："+expert.activity);
                tvCitations.setText("引用数："+expert.citations);
                tvGIndex.setText("G指数："+expert.gindex);
                tvHIndex.setText("H指数："+expert.hindex);
                tvDiversity.setText("多样性："+expert.diversity);
                tvSociability.setText("社会性："+expert.sociability);
                tvPosition.setText(expert.position);
                tvAffiliation.setText(expert.affiliation);
                tvEdu.setText(expert.edu);
                tvBio.setText(expert.bio);
                Bitmap avatar=imgRepo.getImageByURL(expert.avatar);
                if(avatar!=null)
                {
                    ivAvatar.setImageBitmap(avatar);
                }
                else if(expert.avatar!=null&&!expert.avatar.equals("null"))
                {
                    ImageDownloader imageDownloader=new ImageDownloader(new ImageDownloader.OnImageLoaderListener()
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
                            ivAvatar.setImageBitmap(result);
                        }
                    });
                    imageDownloader.download(expert.avatar,false);
                }
            }
        }
    }
}