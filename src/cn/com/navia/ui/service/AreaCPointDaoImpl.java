// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaCPointDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AreaCPointMapper;
import cn.com.navia.ui.mybatis.model.AreaCPointExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.ArrayList;
import java.util.List;

public class AreaCPointDaoImpl extends SpringModule
{

    public AreaCPointDaoImpl()
    {
    }

    public List getCPointByAreaId(int areaId)
        throws Exception
    {
        List points = new ArrayList();
        AreaCPointExample example = new AreaCPointExample();
        example.createCriteria().andAreaIdEqualTo(Integer.valueOf(areaId));
        points = cPointMapper.selectByExample(example);
        return points;
    }

    private AreaCPointMapper cPointMapper;
}
