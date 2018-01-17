// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpRequest.java

package cn.com.navia.ui.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class HttpRequest
{

    public HttpRequest()
    {
    }

    public static String sendGet(String url, String param)
    {
        String result;
        BufferedReader in;
        result = "";
        in = null;
        try
        {
            String urlNameString = (new StringBuilder(String.valueOf(url))).append("?").append(param).toString();
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map map = connection.getHeaderFields();
            String key;
            for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); System.out.println((new StringBuilder(String.valueOf(key))).append("--->").append(map.get(key)).toString()))
                key = (String)iterator.next();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while((line = in.readLine()) != null) 
                result = (new StringBuilder(String.valueOf(result))).append(line).toString();
            break MISSING_BLOCK_LABEL_294;
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder("\u53D1\u9001GET\u8BF7\u6C42\u51FA\u73B0\u5F02\u5E38\uFF01")).append(e).toString());
            e.printStackTrace();
        }
        try
        {
            if(in != null)
                in.close();
        }
        catch(Exception e2)
        {
            e2.printStackTrace();
        }
        break MISSING_BLOCK_LABEL_312;
        Exception exception;
        exception;
        try
        {
            if(in != null)
                in.close();
        }
        catch(Exception e2)
        {
            e2.printStackTrace();
        }
        throw exception;
        try
        {
            if(in != null)
                in.close();
        }
        catch(Exception e2)
        {
            e2.printStackTrace();
        }
        return result;
    }

    public static String sendPost(String url, String param)
    {
        PrintWriter out;
        BufferedReader in;
        String result;
        out = null;
        in = null;
        result = "";
        try
        {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine()) != null) 
                result = (new StringBuilder(String.valueOf(result))).append(line).toString();
            break MISSING_BLOCK_LABEL_234;
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder("\u53D1\u9001 POST \u8BF7\u6C42\u51FA\u73B0\u5F02\u5E38\uFF01")).append(e).toString());
            e.printStackTrace();
        }
        try
        {
            if(out != null)
                out.close();
            if(in != null)
                in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        break MISSING_BLOCK_LABEL_260;
        Exception exception;
        exception;
        try
        {
            if(out != null)
                out.close();
            if(in != null)
                in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        throw exception;
        try
        {
            if(out != null)
                out.close();
            if(in != null)
                in.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }
}
