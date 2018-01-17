// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpringModule.java

package cn.com.navia.ui.spring.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.jdbc.core.JdbcTemplate;

// Referenced classes of package cn.com.navia.ui.spring.base:
//            Module

public abstract class SpringModule extends ApplicationObjectSupport
    implements Module
{

    public SpringModule()
    {
    }

    public void initMethod()
    {
        ctx = getApplicationContext();
        log.info("{} initMethod=>OK", getClass().getSimpleName());
    }

    public ApplicationContext getCtx()
    {
        return ctx;
    }

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected ApplicationContext ctx;
    protected JdbcTemplate analyseJdbcTemplate;
    protected JdbcTemplate uiJdbcTemplate;
    protected JdbcTemplate exJdbcTemplate;
}
