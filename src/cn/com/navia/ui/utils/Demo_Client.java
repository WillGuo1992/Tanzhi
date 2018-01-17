// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Demo_Client.java

package cn.com.navia.ui.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URLEncoder;

// Referenced classes of package cn.com.navia.ui.utils:
//            Client

public class Demo_Client
{

    public Demo_Client()
    {
    }

    public static void main(String args[])
        throws IOException
    {
        String sn = "SDK-DLS-010-00201";
        String pwd = "123105";
        Client client = new Client(sn, pwd);
        String content = URLEncoder.encode("\u5C0A\u656C\u7684\u5BA2\u6237\u60A8\u597D\uFF1A\u60A8\u7684\u9A8C\u8BC1\u7801\u662F\uFF1A123456\u3010\u5317\u6597\u5929\u6210\u3011");
        String mobile = "18500366171";
        String result = client.mt(mobile, content, "", "", "");
        System.out.print(result);
    }
}
