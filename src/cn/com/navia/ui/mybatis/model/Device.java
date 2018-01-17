// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Device.java

package cn.com.navia.ui.mybatis.model;


public class Device
{

    public Device()
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

    public Integer getReferId()
    {
        return referId;
    }

    public void setReferId(Integer referId)
    {
        this.referId = referId;
    }

    public String getMac()
    {
        return mac;
    }

    public void setMac(String mac)
    {
        this.mac = mac;
    }

    public Integer getMallId()
    {
        return mallId;
    }

    public void setMallId(Integer mallId)
    {
        this.mallId = mallId;
    }

    public Boolean getEnable()
    {
        return enable;
    }

    public void setEnable(Boolean enable)
    {
        this.enable = enable;
    }

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    private Integer id;
    private Integer referId;
    private String mac;
    private Integer mallId;
    private Boolean enable;
    private String sn;
    private String description;
}
