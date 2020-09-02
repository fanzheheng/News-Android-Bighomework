package com.example.news_android;

import java.util.ArrayList;

public class Utils
{
    public static String countryURL = "https://covid-dashboard.aminer.cn/api/dist/epidemic.json";

    public static String newsEventURL="https://covid-dashboard.aminer.cn/api/events/list";


    public static String newsContentURL="https://covid-dashboard.aminer.cn/api/event/5f05f3f69fced0a24b2f84ee";//this one is just for test
    public static String entityURL="https://innovaapi.aminer.cn/covid/api/v1/pneumonia/entityquery?entity=病毒";//this one is just for test
    public static String expertURL="https://innovaapi.aminer.cn/predictor/api/v1/valhalla/highlight/get_ncov_expers_list?v=2";

    public static String strSeparator = "__,__";

    public static String convertArrayToString(String[] array)
    {
        String str = "";
        for (int i = 0; i < array.length; i++)
        {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1)
            {
                str = str + strSeparator;
            }
        }
        return str;
    }


    public static String convertArrayToString(ArrayList<String> array)
    {
        String str = "";
        if(array==null)return str;
        for (int i = 0; i < array.size(); i++)
        {
            str = str + array.get(i);
            // Do not append comma at the end of last element
            if (i < array.size()- 1)
            {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static ArrayList<String> convertStringToArray(String str)
    {
        ArrayList<String>res=new ArrayList<String>();
        if(str==null)return res;
        String[] arr = str.split(strSeparator);

        for(int i=0;i<arr.length;i++)
        {
            res.add(arr[i]);
        }
        return res;
    }
}
