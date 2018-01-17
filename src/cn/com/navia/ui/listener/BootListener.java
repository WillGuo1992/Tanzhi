// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BootListener.java

package cn.com.navia.ui.listener;

import cn.com.navia.ui.conf.Conf;
import cn.com.navia.ui.utils.JSONUtils;
import java.io.PrintStream;
import javax.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BootListener
    implements ServletContextListener
{

    public BootListener()
    {
    }

    public void contextInitialized(ServletContextEvent event)
    {
        try
        {
            String confBaseURI = System.getProperty("confURI", event.getServletContext().getInitParameter("confURI"));
            conf = Conf.getInstance(confBaseURI);
            log.info("conf:{}", JSONUtils.toJson(conf));
            ctx = new FileSystemXmlApplicationContext((new StringBuilder(String.valueOf(confBaseURI))).append("/spring.xml").toString());
        }
        catch(Exception e)
        {
            System.err.println((new StringBuilder("Spring initialize Error:")).append(e.getMessage()).toString());
            System.exit(1);
        }
    }

    public void contextDestroyed(ServletContextEvent servletcontextevent)
    {
    }

    public Logger getLog()
    {
        return log;
    }

    public static FileSystemXmlApplicationContext getCtx()
    {
        return ctx;
    }

    public static Conf getConf()
    {
        return conf;
    }

    protected final Logger log = LoggerFactory.getLogger(getClass());
    public static FileSystemXmlApplicationContext ctx;
    private static Conf conf;
}
