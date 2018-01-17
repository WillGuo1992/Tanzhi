// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RetValue.java

package cn.com.navia.ui.wink.base;


public class RetValue
{

    public RetValue(String action)
    {
        this.action = action;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public Object getMsg()
    {
        return msg;
    }

    public void setMsg(Object msg)
    {
        this.msg = msg;
    }

    private int code;
    private String action;
    private Object msg;
}
