// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageServlet.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.utils.RandomValidateCode;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ImageServlet extends HttpServlet
{

    public ImageServlet()
    {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0L);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try
        {
            randomValidateCode.getRandcode(request, response);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doGet(request, response);
    }

    private static final long serialVersionUID = 1L;
}
