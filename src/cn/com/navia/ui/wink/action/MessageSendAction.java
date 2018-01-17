// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MessageSendAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.utils.HttpRequest;
import cn.com.navia.ui.wink.base.BaseAction;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class MessageSendAction extends BaseAction
{

    public MessageSendAction()
    {
    }

    public String getIdentifyCodeForRemote(String telephone, String extno)
    {
        JSONObject obj = new JSONObject();
        String account = "beidoutianchengsc3";
        String password = "BEIdduuy1114";
        String action = "send";
        String userid = "294";
        String url = "http://sc3.nbark.com:7302/sms.aspx";
        try
        {
            if(StringUtils.isNotEmpty(telephone))
            {
                if(StringUtils.isEmpty(extno))
                    extno = "";
                Random r = new Random();
                String d = (new StringBuilder(String.valueOf(r.nextDouble()))).toString();
                String s = d.substring(3, 7);
                String content = (new StringBuilder("\u5C0A\u656C\u7684\u5BA2\u6237\u60A8\u597D\uFF0C\u60A8\u7684\u786E\u8BA4\u7801\u4E3A\uFF1A")).append(s).append("\uFF0C\u8BF7\u57285\u5206\u949F\u5185\u586B\u5199\u3002").toString();
                content = URLEncoder.encode(content, "utf-8");
                String params = (new StringBuilder("action=")).append(action).append("&userid=").append(userid).append("&account=").append(account).append("&password=").append(password).append("&mobile=").append(telephone).append("&content=").append(content).append("&sendTime=&extno=").append(extno).toString();
                log.info("message send : params :{}", params);
                String result = HttpRequest.sendPost(url, params);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(result)));
                Element root = doc.getDocumentElement();
                NodeList books = root.getChildNodes();
                if(books != null)
                {
                    String rtStat = "";
                    for(int i = 0; i < books.getLength(); i++)
                    {
                        Node book = books.item(i);
                        if(book.getNodeName().equalsIgnoreCase("returnstatus"))
                            rtStat = book.getFirstChild().getNodeValue();
                    }

                    if(rtStat.equals("Success"))
                    {
                        obj.put("code", Integer.valueOf(0));
                        obj.put("desc", "\u53D1\u9001\u6210\u529F");
                        obj.put("info", s);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-9));
                        obj.put("desc", "\u53D1\u9001\u5931\u8D25");
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-9));
                    obj.put("desc", "\u53D1\u9001\u5931\u8D25");
                }
                log.info("remote message send : telephone : {},code : {},result : {}", new Object[] {
                    telephone, s, result
                });
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

    public String getIdentifyCode(String telephone, String extno)
    {
        JSONObject obj = new JSONObject();
        String account = "beidoutianchengsc3";
        String password = "BEIdduuy1114";
        String action = "send";
        String userid = "294";
        String url = "http://sc3.nbark.com:7302/sms.aspx";
        try
        {
            if(StringUtils.isNotEmpty(telephone))
            {
                if(StringUtils.isEmpty(extno))
                    extno = "";
                Random r = new Random();
                String d = (new StringBuilder(String.valueOf(r.nextDouble()))).toString();
                String s = d.substring(3, 7);
                String content = (new StringBuilder("\u5C0A\u656C\u7684\u5BA2\u6237\u60A8\u597D\uFF0C\u60A8\u7684\u786E\u8BA4\u7801\u4E3A\uFF1A")).append(s).append("\uFF0C\u8BF7\u57285\u5206\u949F\u5185\u586B\u5199\u3002").toString();
                content = URLEncoder.encode(content, "utf-8");
                String params = (new StringBuilder("action=")).append(action).append("&userid=").append(userid).append("&account=").append(account).append("&password=").append(password).append("&mobile=").append(telephone).append("&content=").append(content).append("&sendTime=&extno=").append(extno).toString();
                log.info("message send : params :{}", params);
                String result = HttpRequest.sendPost(url, params);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(result)));
                Element root = doc.getDocumentElement();
                NodeList books = root.getChildNodes();
                if(books != null)
                {
                    String rtStat = "";
                    for(int i = 0; i < books.getLength(); i++)
                    {
                        Node book = books.item(i);
                        if(book.getNodeName().equalsIgnoreCase("returnstatus"))
                            rtStat = book.getFirstChild().getNodeValue();
                    }

                    if(rtStat.equals("Success"))
                    {
                        obj.put("code", Integer.valueOf(0));
                        obj.put("desc", "\u53D1\u9001\u6210\u529F");
                        obj.put("info", s);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-9));
                        obj.put("desc", "\u53D1\u9001\u5931\u8D25");
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-9));
                    obj.put("desc", "\u53D1\u9001\u5931\u8D25");
                }
                log.info("local message send : telephone : {},code : {},result : {}", new Object[] {
                    telephone, s, result
                });
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
