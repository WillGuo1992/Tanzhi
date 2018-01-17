// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferAreaPoint.java

package cn.com.navia.ui.mybatis.model;


public class ReferAreaPoint
{

    public ReferAreaPoint()
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

    public Double getLng()
    {
        return lng;
    }

    public void setLng(Double lng)
    {
        this.lng = lng;
    }

    public Double getLat()
    {
        return lat;
    }

    public void setLat(Double lat)
    {
        this.lat = lat;
    }

    public Integer getAreaId()
    {
        return areaId;
    }

    public void setAreaId(Integer areaId)
    {
        this.areaId = areaId;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    private Integer id;
    private Double lng;
    private Double lat;
    private Integer areaId;
    private Integer sort;
}
