// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Map.java

package cn.com.navia.ui.mybatis.model;


public class Map
{

    public Map()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getCmW()
    {
        return cmW;
    }

    public void setCmW(Integer cmW)
    {
        this.cmW = cmW;
    }

    public Integer getCmH()
    {
        return cmH;
    }

    public void setCmH(Integer cmH)
    {
        this.cmH = cmH;
    }

    public Integer getPxW()
    {
        return pxW;
    }

    public void setPxW(Integer pxW)
    {
        this.pxW = pxW;
    }

    public Integer getPxH()
    {
        return pxH;
    }

    public void setPxH(Integer pxH)
    {
        this.pxH = pxH;
    }

    public Integer getStoreyId()
    {
        return storeyId;
    }

    public void setStoreyId(Integer storeyId)
    {
        this.storeyId = storeyId;
    }

    private Integer id;
    private String name;
    private Integer cmW;
    private Integer cmH;
    private Integer pxW;
    private Integer pxH;
    private Integer storeyId;
}
