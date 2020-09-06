package com.example.news_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.news_android.DataBase.Entity;
import com.example.news_android.DataBase.EntityRepo;
import com.example.news_android.DataBase.EpidemicData;
import com.example.news_android.DataBase.EpidemicRepo;
import com.example.news_android.DataBase.Expert;
import com.example.news_android.DataBase.ExpertRepo;
import com.example.news_android.DataBase.ImageDownloader;
import com.example.news_android.DataBase.ImageRepo;
import com.example.news_android.DataBase.News;
import com.example.news_android.DataBase.NewsRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class JsonGetter extends AsyncTask
{
    String url;
    Context context;
    JsonGetterFinishListener finishListener=null;
    Boolean result = true;

    public Boolean getResult() {
        return result;
    }

    public interface  JsonGetterFinishListener
    {
        void OnFinish();
    }

    public JsonGetter(String url, Context context)
    {
        this.url = url;
        this.context = context;
        this.finishListener=null;
    }
    public JsonGetter(String url, Context context,JsonGetterFinishListener listener)
    {
        this.url = url;
        this.context = context;
        this.finishListener=listener;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        if(finishListener!=null)
            finishListener.OnFinish();
    }

    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        JSONObject jsonObject = null;
        URL targetURL = null;
        try
        {
            targetURL = new URL(url);
            URLConnection tc = targetURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String tmp = new String();
            while ((tmp = in.readLine()) != null)
            {
                buffer.append(tmp);
            }
            String builder = buffer.toString();
            jsonObject = new JSONObject(builder);

        } catch (Exception e)
        {
            result = false;
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

}

class EpidemicDataJsonGetter extends JsonGetter
{
    public EpidemicDataJsonGetter(String url, Context context)
    {
        super(url, context);
    }
    public EpidemicDataJsonGetter(String url, Context context,JsonGetterFinishListener listener)
    {
        super(url, context,listener);
    }
    static JSONObject epidemicDataJson = null;
    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        epidemicDataJson=super.doInBackground(objects);
        if(epidemicDataJson==null)return null;
        EpidemicRepo repo=new EpidemicRepo(context);
        if (epidemicDataJson == null) return null;
        Iterator<String> keys = epidemicDataJson.keys();
        while (keys.hasNext())
        {
            String keyName = keys.next();
            String[]location=keyName.split("\\|");// COUNTRY|PROVINCE|CITY
            try
            {
                EpidemicData epidemicData=new EpidemicData();
                JSONObject obj=epidemicDataJson.getJSONObject(keyName);
                epidemicData.setBeginDate(obj.getString(EpidemicData.jsonBeginDateKey));
                epidemicData.setDistrict(keyName);
                epidemicData.setCountry(location[0]);
                if(location.length>=2)
                {
                    epidemicData.setProvince(location[1]);
                    if(location.length>=3)
                    {
                        epidemicData.setCity(location[2]);
                    }
                }
                JSONArray data=obj.getJSONArray(EpidemicData.jsonDataKey);
                for(int i=0;i<data.length();i++)
                {
                    JSONArray numArray= (JSONArray) data.get(i);
                    epidemicData.confirmed.add(numArray.getInt(0));
                    epidemicData.cured.add(numArray.getInt(2));
                    epidemicData.dead.add(numArray.getInt(3));
                }
                if(repo.getEpidemicByDistrict(epidemicData.district)==null)
                    repo.insert(epidemicData);
                else
                    repo.update(epidemicData);

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return epidemicDataJson;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }
}

class NewsEventJsonGetter extends JsonGetter
{
    static int page = 1;
    static String type = "all";
    static int size = 20;

    public static void updatePage(int page)
    {
        NewsEventJsonGetter.page = page;
    }

    public static void updateType(String type)
    {
        NewsEventJsonGetter.type = type;
    }

    public static void updateSize(int size)
    {
        NewsEventJsonGetter.size = size;
    }

    public NewsEventJsonGetter(String url, Context context)
    {
        super(url + "?type=" + type + "&page=" + page + "&size=" + size, context);
    }
    public NewsEventJsonGetter(String url, Context context,JsonGetterFinishListener listener)
    {
        super(url + "?type=" + type + "&page=" + page + "&size=" + size, context,listener);
    }

    static JSONObject newsEventJson = null;
    static JSONArray newsEventJsonArray = null;

    @SuppressLint("WrongThread")
    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        newsEventJson = super.doInBackground(objects);
        if(newsEventJson==null)return null;
        page += 1;//increment the page number for future use;
        NewsRepo newsRepo = new NewsRepo(context);
        try
        {
            newsEventJsonArray = newsEventJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (newsEventJsonArray != null)
        {
            for (int i = 0; i < newsEventJsonArray.length(); i++)
            {
                try
                {
                    News news = new News();
                    JSONObject newsObj = (JSONObject) newsEventJsonArray.get(i);
                    news.set_id(newsObj.getString(News._idKey));
                    news.setType(newsObj.getString(News.typeKey));
                    news.setTitle(newsObj.getString(News.titleKey));
                    news.setLang(newsObj.getString(News.langKey));
                    news.setCategory(newsObj.getString(News.categoryKey));
                    news.setTime(newsObj.getString(News.timeKey));
                    String newsContentURL = "https://covid-dashboard.aminer.cn/api/event/" + news._id;
                    NewsContentJsonGetter newsContentJsonGetter = new NewsContentJsonGetter(newsContentURL, context, news);
                    newsContentJsonGetter.execute();//get more details
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }
        return newsEventJson;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }
}

class NewsContentJsonGetter extends JsonGetter
{
    public NewsContentJsonGetter(String url, Context context, News news)
    {
        super(url, context);
        this.news = news;
    }

    public NewsContentJsonGetter(String url, Context context,JsonGetterFinishListener listener)
    {
        super(url, context,listener);
    }

    News news;
    static JSONObject newsContentJson = null;

    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        JSONObject topObject= super.doInBackground(objects);
        if(topObject==null)return null;
        NewsRepo newsRepo = new NewsRepo(context);

        try
        {
            newsContentJson = topObject.getJSONObject("data");
            news.setContent(newsContentJson.getString(News.contentKey));
            news.setDate(newsContentJson.getString(News.dateKey));
            news.setSource(newsContentJson.getString(News.sourceKey));
            //JSONArray relatedEventArray = newsContentJson.getJSONArray(News.relatedEventsKey);
            JSONArray entities = newsContentJson.getJSONArray(News.entitiesKey);
//            for (int i = 0; i < relatedEventArray.length(); i++)
//            {
//                JSONObject obj = (JSONObject) relatedEventArray.get(i);
//                news.relatedEvents.add(obj.getString("id"));
//            }
            for (int i = 0; i < entities.length(); i++)
            {
                JSONObject obj = (JSONObject) entities.get(i);
                news.entities.add(obj.getString("label"));
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        News potentialExisting = newsRepo.getNewsById(news._id);//check if this news is already stored
        if (potentialExisting == null)
            newsRepo.insert(news);
        else {
            newsRepo.update(news);
        }
        return newsContentJson;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }
}

class EntityJsonGetter extends JsonGetter
{
    public EntityJsonGetter(String url, Context context)
    {
        super(url, context);
    }
    public EntityJsonGetter(String url, Context context,JsonGetterFinishListener listener)
    {
        super(url, context,listener);
    }
    static JSONObject entityJson = null;
    static JSONArray entityJsonArray = null;

    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        entityJson =super.doInBackground(objects);
        if(entityJson==null)return null;
        final ImageRepo imageRepo = new ImageRepo(context);
        EntityRepo entityRepo=new EntityRepo(context);
        final ImageDownloader imgDownloader = new ImageDownloader(new ImageDownloader.OnImageLoaderListener()
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
            public void onComplete(Bitmap result, final String imgUrl)
            {
                imageRepo.insert(imgUrl, result);
            }
        });
        try
        {
            entityJsonArray = entityJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (entityJsonArray != null)
        {
            for (int i = 0; i < entityJsonArray.length(); i++)
            {
                try
                {
                    JSONObject obj = (JSONObject) entityJsonArray.get(i);
                    JSONObject abstractInfo = obj.getJSONObject(Entity.abstractInfoKey);
                    Entity entity = new Entity();
                    entity.setBaidu(abstractInfo.getString(Entity.baiduKey));
                    entity.setLabel(obj.getString(Entity.labelKey));
                    entity.setUrl(obj.getString(Entity.urlKey));
                    entity.setBaidu(abstractInfo.getString(Entity.baiduKey));
                    entity.setEnwiki(abstractInfo.getString(Entity.enwikiKey));
                    entity.setZhwiki(abstractInfo.getString(Entity.zhwikiKey));
                    JSONObject COVID = abstractInfo.getJSONObject(Entity.COVIDKey);
                    JSONObject properties = COVID.getJSONObject(Entity.propertiesKey);
                    Iterator<String> propKeys = properties.keys();
                    while (propKeys.hasNext())
                    {
                        String keyName = propKeys.next();
                        String value = properties.getString(keyName);
                        entity.properties.put(keyName, value);
                    }
                    JSONArray relations = COVID.getJSONArray(Entity.relationsKey);
                    for (int j = 0; j < relations.length(); j++)
                    {
                        JSONObject rel = (JSONObject) relations.get(j);
                        boolean forward = rel.getBoolean(Entity.forwardKey);
                        String relation = rel.getString("relation");
                        String url = rel.getString("url");
                        String label = rel.getString("label");
                        String res = relation + Utils.strSeparator + url + Utils.strSeparator + label;
                        if (forward)
                        {
                            entity.parents.add(res);
                        } else
                        {
                            entity.children.add(res);
                        }
                    }

                    String imgUrl = obj.getString(Entity.imgURLKey);
                    entity.setImgURL(imgUrl);
                    if(imgUrl!=null&&imageRepo.getImageByURL(imgUrl)==null)
                        imgDownloader.download(imgUrl, true);
                    if(entityRepo.getEntityByLabel(entity.label)==null)
                        entityRepo.insert(entity);
                    else
                        entityRepo.update(entity);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return entityJson;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }
}


class ExpertJsonGetter extends JsonGetter
{
    public ExpertJsonGetter(String url, Context context)
    {
        super(url, context);
    }
    public ExpertJsonGetter(String url, Context context,JsonGetterFinishListener listener)
    {
        super(url, context,listener);
    }
    static JSONObject expertJson = null;
    static JSONArray expertJsonArray = null;

    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        expertJson = super.doInBackground(objects);
        if(expertJson==null)return null;
        ExpertRepo repo=new ExpertRepo(context);
        final ImageRepo imageRepo = new ImageRepo(context);
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
                imageRepo.insert(imgUrl, result);
            }
        });
        try
        {
            expertJsonArray = expertJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if (expertJsonArray != null)
        {
            for (int i = 0; i < expertJsonArray.length(); i++)
            {
                try
                {
                    System.out.println(expertJsonArray.get(i));
                    Expert expert=new Expert();
                    JSONObject obj= (JSONObject) expertJsonArray.get(i);
                    expert.setAvatar(obj.getString(Expert.avatarKey));
                    expert.setId(obj.getString(Expert.idKey));
                    JSONObject indices=obj.getJSONObject(Expert.jsonIndicesKey);
                    expert.setActivity((float) indices.getDouble(Expert.activityKey));
                    expert.setCitations(indices.getInt(Expert.citationsKey));
                    expert.setDiversity((float) indices.getDouble(Expert.diversityKey));
                    expert.setGindex((float) indices.getDouble(Expert.gindexKey));
                    expert.setHindex((float) indices.getDouble(Expert.hindexKey));
                    expert.setNewStar((float) indices.getDouble(Expert.newStarKey));
                    expert.setRisingStar((float) indices.getDouble(Expert.risingStarKey));
                    expert.setSociability((float) indices.getDouble(Expert.sociabilityKey));
                    expert.setName(obj.getString(Expert.nameKey));
                    expert.setNameZh(obj.getString(Expert.nameZhKey));
                    expert.setPassedAway(obj.getBoolean(Expert.isPassedAwayKey));

                    JSONObject profile=obj.getJSONObject(Expert.jsonProfileKey);
                    Iterator<String> profileKeys=profile.keys();
                    while(profileKeys.hasNext())
                    {
                        String key=profileKeys.next();
                        if(key.equals(Expert.affiliationKey))
                        {
                            expert.setAffiliation(profile.getString(key));
                        }
                        else if(key.equals(Expert.affiliationZhKey))
                        {
                            expert.setAffiliationZh(profile.getString(key));
                        }
                        else if(key.equals(Expert.bioKey))
                        {
                            expert.setBio(profile.getString(key));
                        }
                        else if(key.equals(Expert.eduKey))
                        {
                            expert.setEdu(profile.getString(key));
                        }
                        else if(key.equals(Expert.positionKey))
                        {
                            expert.setPosition(profile.getString(key));
                        }
                        else if(key.equals(Expert.workKey))
                        {
                            expert.setWork(profile.getString(key));
                        }
                    }
                    if(expert.avatar!=""&&imageRepo.getImageByURL(expert.avatar)==null)
                        imageDownloader.download(expert.avatar,true);
                    if(repo.getExpertById(expert.id)==null)
                        repo.insert(expert);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return expertJson;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }
}




