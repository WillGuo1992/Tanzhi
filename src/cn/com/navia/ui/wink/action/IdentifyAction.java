// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IdentifyAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.utils.Client;
import cn.com.navia.ui.wink.base.BaseAction;
import java.util.Random;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

public class IdentifyAction extends BaseAction
{

    public IdentifyAction()
    {
    }

    public String getIdentifyCodeForRemote(String telephone)
    {
        JSONObject obj = new JSONObject();
        String sn = "SDK-DLS-010-00201";
        String pwd = "123105";
        try
        {
            if(StringUtils.isNotEmpty(telephone))
            {
                Random r = new Random();
                String d = (new StringBuilder(String.valueOf(r.nextDouble()))).toString();
                String s = d.substring(3, 7);
                String content = (new StringBuilder("\u5C0A\u656C\u7684\u5BA2\u6237\u60A8\u597D\uFF1A\u60A8\u7684\u9A8C\u8BC1\u7801\u662F\uFF1A")).append(s).append("\u3010\u5317\u6597\u5929\u6210\u3011").toString();
                Client c = new Client(sn, pwd);
                String result_mt = c.mt(telephone, content, "", "", "");
                log.info("identify-code send : telephone : {},code : {}", telephone, s);
                if(result_mt.startsWith("-") || result_mt.equals(""))
                {
                    log.info("identify-code send error : {}", result_mt);
                    obj.put("code", Integer.valueOf(-8));
                    obj.put("desc", "\u9A8C\u8BC1\u7801\u53D1\u9001\u5931\u8D25!");
                } else
                {
                    obj.put("code", Integer.valueOf(0));
                    obj.put("desc", "\u53D1\u9001\u6210\u529F");
                    obj.put("info", s);
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
                obj.put("desc", "\u53C2\u6570\u9519\u8BEF\uFF0C\u53C2\u6570\u4E0D\u80FD\u4E3A\u7A7A");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String getIdentifyCode(String telephone)
    {
        JSONObject obj = new JSONObject();
        String sn = "SDK-DLS-010-00201";
        String pwd = "123105";
        try
        {
            if(StringUtils.isNotEmpty(telephone))
            {
                Random r = new Random();
                String d = (new StringBuilder(String.valueOf(r.nextDouble()))).toString();
                String s = d.substring(3, 7);
                String content = (new StringBuilder("\u5C0A\u656C\u7684\u5BA2\u6237\u60A8\u597D\uFF1A\u60A8\u7684\u9A8C\u8BC1\u7801\u662F\uFF1A")).append(s).append("\u3010\u5317\u6597\u5929\u6210\u3011").toString();
                Client c = new Client(sn, pwd);
                String result_mt = c.mt(telephone, content, "", "", "");
                log.info("identify-code send : telephone : {},code : {}", telephone, s);
                if(result_mt.startsWith("-") || result_mt.equals(""))
                {
                    log.info("identify-code send error : {}", result_mt);
                    obj.put("code", Integer.valueOf(-8));
                    obj.put("desc", "\u9A8C\u8BC1\u7801\u53D1\u9001\u5931\u8D25!");
                } else
                {
                    obj.put("code", Integer.valueOf(0));
                    obj.put("desc", "\u53D1\u9001\u6210\u529F");
                    obj.put("info", s);
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
                obj.put("desc", "\u53C2\u6570\u9519\u8BEF\uFF0C\u53C2\u6570\u4E0D\u80FD\u4E3A\u7A7A");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }
}
