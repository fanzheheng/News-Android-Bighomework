package com.example.news_android;

import java.util.ArrayList;

public class Utils
{
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
