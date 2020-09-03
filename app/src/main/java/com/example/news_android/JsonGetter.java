package com.example.news_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UTFDataFormatException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class JsonGetter extends AsyncTask
{
    String url;
    Context context;
    public JsonGetter(String url,Context context)
    {
        this.url = url;
        this.context=context;
    }

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
    }

    @Override
    protected JSONObject doInBackground(Object[] objects)
    {
        JSONObject jsonObject=null;
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

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

}

class CountryDataJsonGetter extends JsonGetter
{
    public CountryDataJsonGetter(String url,Context context)
    {
        super(url,context);
    }
    static JSONObject countryDataJson=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);

        countryDataJson= (JSONObject) o;
        if(o==null)return;
        Iterator<String>keys=countryDataJson.keys();
        while(keys.hasNext())
        {
            String keyName=keys.next();
            System.out.println(keyName);
            try
            {
                String content=countryDataJson.getString(keyName);
                System.out.println(content);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class NewsEventJsonGetter extends JsonGetter
{
    static int page=1;
    static String type="all";
    static int size=20;

    public static void updatePage(int page)
    {
        NewsEventJsonGetter.page=page;
    }
    public static void updateType(String type)
    {
        NewsEventJsonGetter.type=type;
    }
    public static void updateSize(int size)
    {
        NewsEventJsonGetter.size=size;
    }

    public NewsEventJsonGetter(String url,Context context)
    {
        super(url+"?type="+type+"&page="+page+"&size="+size,context);
    }
    static JSONObject newsEventJson=null;
    static JSONArray newsEventJsonArray=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        newsEventJson= (JSONObject) o;

        page+=1;//increment the page number for future use;

        NewsRepo newsRepo=new NewsRepo(context);
        try
        {
            newsEventJsonArray=newsEventJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(newsEventJsonArray!=null)
        {
            for(int i=0;i<newsEventJsonArray.length();i++)
            {
                try
                {
                    News news=new News();
                    JSONObject newsObj= (JSONObject) newsEventJsonArray.get(i);
                    news.set_id(newsObj.getString(News._idKey));
                    news.setType(newsObj.getString(News.typeKey));
                    news.setTitle(newsObj.getString(News.titleKey));
                    news.setLang(newsObj.getString(News.langKey));
                    news.setCategory(newsObj.getString(News.categoryKey));
                    news.setTime(newsObj.getString(News.timeKey));

                    String newsContentURL="https://covid-dashboard.aminer.cn/api/event/"+news._id;
                    NewsContentJsonGetter newsContentJsonGetter=new NewsContentJsonGetter(newsContentURL,context,news);
                    newsContentJsonGetter.execute();//get more details
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

        }
//        if(page<5)
//        {
//            JsonGetter jsonGetter=new NewsEventJsonGetter(Utils.newsEventURL,context);
//            jsonGetter.execute();
//        }
    }
}

class NewsContentJsonGetter extends JsonGetter
{
    public NewsContentJsonGetter(String url,Context context,News news)
    {
        super(url,context);
        this.news=news;
    }

    News news;
    static JSONObject newsContentJson=null;

    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        JSONObject topObject= (JSONObject) o;
        NewsRepo newsRepo=new NewsRepo(context);

        try
        {
            newsContentJson=topObject.getJSONObject("data");
            news.setContent(newsContentJson.getString(News.contentKey));
            news.setDate(newsContentJson.getString(News.dateKey));
            JSONArray relatedEventArray=newsContentJson.getJSONArray(News.relatedEventsKey);
            JSONArray entities=newsContentJson.getJSONArray(News.entitiesKey);
            for(int i=0;i<relatedEventArray.length();i++)
            {
                JSONObject obj= (JSONObject) relatedEventArray.get(i);
                news.relatedEvents.add(obj.getString("id"));
            }
            for(int i=0;i<entities.length();i++)
            {
                JSONObject obj= (JSONObject) entities.get(i);
                news.entities.add(obj.getString("label"));
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        News potentialExisting=newsRepo.getNewsById(news._id);//check if this news is already stored
        if(potentialExisting==null)
            newsRepo.insert(news);
    }
}

class EntityJsonGetter extends JsonGetter
{
    public EntityJsonGetter(String url,Context context)
    {
        super(url,context);
    }
    static JSONObject entityJson=null;
    static JSONArray entityJsonArray=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        entityJson= (JSONObject) o;


        File sdcard = Environment.getExternalStorageDirectory() ;

        final File folder = new File(sdcard.getAbsoluteFile(), Utils.imgDirectory);//the dot makes this directory hidden to the user
        folder.mkdir();
        final ImageDownloader imgDownloader=new ImageDownloader(new ImageDownloader.OnImageLoaderListener()
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
            public void onComplete(Bitmap result,String imgUrl)
            {
                File file = new File(folder.getAbsoluteFile(), imgUrl + ".jpg") ;
                ImageDownloader.writeToDisk(file,result,null,Bitmap.CompressFormat.JPEG,false);
            }
        });
        try
        {
            entityJsonArray= entityJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(entityJsonArray!=null)
        {
            for(int i=0;i<entityJsonArray.length();i++)
            {
                try
                {
                    JSONObject obj= (JSONObject) entityJsonArray.get(i);
                    Entity entity=new Entity();
                    entity.setBaidu(obj.getString(Entity.baiduKey));
                    entity.setUrl(obj.getString(Entity.urlKey));
                    JSONObject abstractInfo=obj.getJSONObject(Entity.abstractInfoKey);
                    entity.setBaidu(abstractInfo.getString(Entity.baiduKey));
                    entity.setEnwiki(abstractInfo.getString(Entity.enwikiKey));
                    entity.setZhwiki(abstractInfo.getString(Entity.zhwikiKey));
                    JSONObject COVID=abstractInfo.getJSONObject(Entity.COVIDKey);
                    JSONObject properties=abstractInfo.getJSONObject(Entity.propertiesKey);
                    Iterator<String>propKeys=properties.keys();
                    while(propKeys.hasNext())
                    {
                        String keyName=propKeys.next();
                        String value=properties.getString(keyName);
                        entity.properties.put(keyName,value);
                    }
                    JSONArray relations=COVID.getJSONArray(Entity.relationsKey);
                    for(int j=0;j<relations.length();j++)
                    {
                        JSONObject rel= (JSONObject) relations.get(j);
                        boolean forward=rel.getBoolean(Entity.forwardKey);
                        String relation=rel.getString("relation");
                        String url=rel.getString("url");
                        String label=rel.getString("label");
                        String res=relation+ Utils.strSeparator+url+Utils.strSeparator+label;
                        if(forward)
                        {
                            entity.parents.add(res);
                        }
                        else
                        {
                            entity.children.add(res);
                        }
                    }

                    String imgUrl=obj.getString(Entity.imgKey);
                    entity.setImg(imgUrl);
                    File potentialExisting = new File(folder.getAbsoluteFile(), imgUrl + ".jpg") ;
                    if(imgUrl!=null&&!potentialExisting.exists())
                        imgDownloader.download(imgUrl,true);
//                    System.out.println(entityJsonArray.get(i));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}


class ExpertJsonGetter extends JsonGetter
{
    public ExpertJsonGetter(String url,Context context)
    {
        super(url,context);
    }
    static JSONObject expertJson=null;
    static JSONArray expertJsonArray=null;
    @Override
    protected void onPostExecute(Object o)
    {
        super.onPostExecute(o);
        expertJson= (JSONObject) o;

        try
        {
            expertJsonArray= expertJson.getJSONArray("data");
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        if(expertJsonArray!=null)
        {
            for(int i=0;i<expertJsonArray.length();i++)
            {
                try
                {
                    System.out.println(expertJsonArray.get(i));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}




