// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseAction.java

package cn.com.navia.ui.wink.base;

import cn.com.navia.ui.conf.Conf;
import cn.com.navia.ui.listener.BootListener;
import cn.com.navia.ui.utils.JSONUtils;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

// Referenced classes of package cn.com.navia.ui.wink.base:
//            RetValue

public class BaseAction
{

    public BaseAction()
    {
        conf = Conf.getInstance();
    }

    public ApplicationContext getCtx()
    {
        return BootListener.ctx;
    }

    protected RetValue getRetValue(String action)
    {
        return new RetValue(action);
    }

    protected String getRetValueJson(RetValue rv)
    {
        return JSONUtils.toJson(rv);
    }

    protected void saveToSession(String k, Object v)
    {
        request.getSession().setAttribute(k, v);
    }

    protected void saveToRequest(String k, Object v)
    {
        request.setAttribute(k, v);
    }

    protected Object getFromSession(String k)
    {
        return request.getSession().getAttribute(k);
    }

    protected void deleteFromSession(String k)
    {
        request.getSession().removeAttribute(k);
    }

    protected void forward(String uri)
        throws ServletException, IOException
    {
        request.getRequestDispatcher(uri).forward(request, response);
    }

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected Conf conf;
}
