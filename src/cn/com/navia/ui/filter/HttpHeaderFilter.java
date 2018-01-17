// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HttpHeaderFilter.java

package cn.com.navia.ui.filter;

import java.io.IOException;
import javax.servlet.*;

public class HttpHeaderFilter
    implements Filter
{

    public HttpHeaderFilter()
    {
    }

    public void destroy()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        response.setCharacterEncoding("utf-8");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterconfig)
        throws ServletException
    {
    }
}
