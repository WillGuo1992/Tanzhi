// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   mdSmsSend_u.java

package cn.com.navia.ui.utils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

// Referenced classes of package cn.com.navia.ui.utils:
//            Client

public class mdSmsSend_u
{

    public mdSmsSend_u()
    {
    }

    public static void main(String args[])
        throws UnsupportedEncodingException
    {
        String sn = "SDK-DLS-010-00201";
        String pwd = "123105";
        String mobiles = "18500366171";
        String content = "\u5C0A\u656C\u7684\u5BA2\u6237\u60A8\u597D\uFF1A\u60A8\u7684\u9A8C\u8BC1\u7801\u662F\uFF1A123456\u3010\u5317\u6597\u5929\u6210\u3011";
        Client client = new Client(sn, pwd);
        String result_mt = client.mt(mobiles, content, "", "", "");
        if(result_mt.startsWith("-") || result_mt.equals(""))
        {
            System.out.print((new StringBuilder("\u53D1\u9001\u5931\u8D25\uFF01\u8FD4\u56DE\u503C\u4E3A\uFF1A")).append(result_mt).append("\u8BF7\u67E5\u770Bwebservice\u8FD4\u56DE\u503C\u5BF9\u7167\u8868").toString());
            return;
        } else
        {
            System.out.print((new StringBuilder("\u53D1\u9001\u6210\u529F\uFF0C\u8FD4\u56DE\u503C\u4E3A\uFF1A")).append(result_mt).toString());
            return;
        }
    }
}
