// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpUtils.java

package cn.com.navia.ui.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils
{

    public HttpUtils()
    {
    }

    public static URI buildURI(String baseURI, Map args)
        throws URISyntaxException
    {
        URIBuilder builder = new URIBuilder(baseURI);
        if(args != null)
        {
            java.util.Map.Entry entry;
            for(Iterator iterator = args.entrySet().iterator(); iterator.hasNext(); builder.setParameter((String)entry.getKey(), (String)entry.getValue()))
                entry = (java.util.Map.Entry)iterator.next();

        }
        return builder.build();
    }

    public static String[] requestGet(URI uri, int timeout)
        throws ClientProtocolException, IOException
    {
        String ret[] = (String[])null;
        HttpGet httpget = new HttpGet(uri);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout * 1000).setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setCookieSpec("ignoreCookies").build();
        httpget.setConfig(requestConfig);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget, HttpClientContext.create());
        int code = response.getStatusLine().getStatusCode();
        if(code == 200)
        {
            String chars[] = response.getEntity().getContentType().getValue().split("=");
            Charset charset = Charset.defaultCharset();
            if(chars.length > 1)
                charset = Charset.forName(chars[1]);
            ret = (new String[] {
                (new StringBuilder(String.valueOf(code))).toString(), response.getEntity().getContentType().getValue(), EntityUtils.toString(response.getEntity(), charset)
            });
        } else
        {
            ret = (new String[] {
                (new StringBuilder(String.valueOf(code))).toString()
            });
        }
        return ret;
    }

    public static String encode(String string, String charset)
    {
        try
        {
            return URLEncoder.encode(string, charset);
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return charset;
    }
}
