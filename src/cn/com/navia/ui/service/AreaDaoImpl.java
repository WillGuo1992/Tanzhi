// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AreaMapper;
import cn.com.navia.ui.mybatis.model.AreaExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.ArrayList;
import java.util.List;

public class AreaDaoImpl extends SpringModule
{

    public AreaDaoImpl()
    {
    }

    public List getAreasByMallId(int mallId)
        throws Exception
    {
        List areas = new ArrayList();
        AreaExample example = new AreaExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mallId));
        areas = areaMapper.selectByExample(example);
        return areas;
    }

    public List getAreasByMapId(int mapId)
        throws Exception
    {
        List areas = new ArrayList();
        AreaExample example = new AreaExample();
        example.createCriteria().andMapIdEqualTo(Integer.valueOf(mapId));
        areas = areaMapper.selectByExample(example);
        return areas;
    }

    //query: mallid && AType=1
    public List getEntrysByMallId(int mallId)
        throws Exception
    {
        List areas = new ArrayList();
        AreaExample example = new AreaExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mallId)).andATypeEqualTo(Integer.valueOf(1));
        areas = areaMapper.selectByExample(example);
        return areas;
    }

    public List getImportantAreasByMallId(int mallId)
        throws Exception
    {
        List areas = new ArrayList();
        AreaExample example = new AreaExample();
        example.createCriteria().andMallIdEqualTo(Integer.valueOf(mallId)).andIsImportantEqualTo(Boolean.valueOf(true));
        areas = areaMapper.selectByExample(example);
        return areas;
    }

    private AreaMapper areaMapper;
}
