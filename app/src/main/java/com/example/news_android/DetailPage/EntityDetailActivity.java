package com.example.news_android.DetailPage;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.news_android.DataBase.*;
import com.example.news_android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntityDetailActivity extends AppCompatActivity {
    TextView etLabel, etDesc;
    ImageView etImage;
    RecyclerView etProperties, etRelations;
    TopView topView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);

        etLabel = findViewById(R.id.et_label);
        etDesc = findViewById(R.id.et_desc);
        etImage = findViewById(R.id.et_image);
        etProperties = findViewById(R.id.et_properties);
        etRelations = findViewById(R.id.et_rels);
        topView = findViewById(R.id.entity_top_view);

        topView.setDefaultBackButtonListener(this);

        Bundle bundle = getIntent().getExtras();
        String label = bundle.getString(Entity.labelKey);
        if(label != null) {
            EntityRepo repo = new EntityRepo(this);
            final ImageRepo imgRepo = new ImageRepo(this);
            Entity entity = repo.getEntityByLabel(label);
            if(entity != null) {
                etLabel.setText(label);
                String desc = "";
                if(!entity.baidu.equals("")) {
                    desc = entity.baidu;
                }
                if(!entity.zhwiki.equals("")) {
                    desc = entity.zhwiki;
                }
                if(!entity.enwiki.equals("")) {
                    desc = entity.baidu;
                }
                if(desc.equals("")) {
                    etDesc.setVisibility(View.GONE);
                } else {
                    etDesc.setText(desc);
                }

                EntityRelationAdapter entityRelationAdapter = new EntityRelationAdapter(entity.parents, entity.children);
                etRelations.setAdapter(entityRelationAdapter);
                etRelations.setLayoutManager(new LinearLayoutManager(this));

                EntityPropertiesAdapter entityPropertiesAdapter = new EntityPropertiesAdapter(entity.properties);
                etProperties.setAdapter(entityPropertiesAdapter);
                etProperties.setLayoutManager(new LinearLayoutManager(this));
                Bitmap image =imgRepo.getImageByURL(entity.imgURL);
                if(image!=null) {
                    etImage.setImageBitmap(image);
                } else {
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
                            etImage.setImageBitmap(result);
                            etImage.setVisibility(View.VISIBLE);
                        }
                    });
                    if(entity.imgURL!=null&&!entity.imgURL.equals("null"))
                    {
                        imageDownloader.download(entity.imgURL,false);
                    }
                    etImage.setVisibility(View.GONE);
                }
            }
        }
    }
}


class EntityRelationAdapter extends RecyclerView.Adapter<EntityRelationAdapter.EntityRelationHolder> {
    class EntityRelation {
        String relation;
        String url;
        String targetLabel;
        boolean forward;

        EntityRelation(String relation, String url, String targetLabel, boolean forward) {
            this.relation = relation;
            this.url = url;
            this.targetLabel = targetLabel;
            this.forward = forward;
        }
    }
    private ArrayList<EntityRelation> relations = new ArrayList<>();
    public EntityRelationAdapter(ArrayList<String> parents, ArrayList<String> children) {
        EntityRelation r;
        for (int i = 0; i + 2 < parents.size(); i += 3) {
            r = new EntityRelation(parents.get(i), parents.get(i + 1), parents.get(i + 2), true);
            relations.add(r);
        }
        for (int i = 0; i + 2 < children.size(); i += 3) {
            r = new EntityRelation(children.get(i), children.get(i + 1), children.get(i + 2), false);
            relations.add(r);
        }
    }

    @NonNull
    @Override
    public EntityRelationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.et_relation_layout, parent, false);
        return new EntityRelationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntityRelationHolder holder, int position) {
        holder.et_rel.setText(relations.get(position).relation);
        holder.target.setText(relations.get(position).targetLabel);
        if(relations.get(position).forward) {
            holder.forwardImage.setImageResource(R.drawable.forward_parent_shape);
        } else {
            holder.forwardImage.setImageResource(R.drawable.forward_child_shape);
        }
    }

    @Override
    public int getItemCount() {
        return relations.size();
    }

    static class EntityRelationHolder extends RecyclerView.ViewHolder {
        TextView et_rel, target;
        ImageView forwardImage;
        public EntityRelationHolder(@NonNull View itemView) {
            super(itemView);
            et_rel = itemView.findViewById(R.id.et_rel);
            target = itemView.findViewById(R.id.et_target);
            forwardImage = itemView.findViewById(R.id.et_forward);
        }
    }
}

class EntityPropertiesAdapter extends RecyclerView.Adapter<EntityPropertiesAdapter.EntityPropertyHolder> {
    ArrayList<String> propertyLabels = new ArrayList<>();
    ArrayList<String> propertyTexts = new ArrayList<>();

    public EntityPropertiesAdapter(HashMap<String, String> properties) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            propertyLabels.add(entry.getKey());
            String text = entry.getValue();
            if(text.charAt(text.length() - 1) == '\n')
                text = text.substring(0, text.length() - 1);
            propertyTexts.add(text);
        }
    }

    @NonNull
    @Override
    public EntityPropertyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.et_property_layout, parent, false);
        return new EntityPropertyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntityPropertyHolder holder, int position) {
        holder.property.setText(propertyLabels.get(position));
        holder.propertyText.setText(propertyTexts.get(position));
    }

    @Override
    public int getItemCount() {
        return propertyLabels.size();
    }

    static class EntityPropertyHolder extends RecyclerView.ViewHolder {
        TextView property, propertyText;
        public EntityPropertyHolder(@NonNull View itemView) {
            super(itemView);
            property = itemView.findViewById(R.id.et_property);
            propertyText = itemView.findViewById(R.id.et_property_text);
        }
    }
}