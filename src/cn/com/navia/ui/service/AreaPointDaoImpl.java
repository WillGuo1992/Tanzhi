// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaPointDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AreaPointMapper;
import cn.com.navia.ui.mybatis.model.AreaPointExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.ArrayList;
import java.util.List;

public class AreaPointDaoImpl extends SpringModule
{

    public AreaPointDaoImpl()
    {
    }

    public List getPointsByAreaId(int areaId)
        throws Exception
    {
        List points = new ArrayList();
        AreaPointExample example = new AreaPointExample();
        example.createCriteria().andAreaIdEqualTo(Integer.valueOf(areaId));
        points = pointMapper.selectByExample(example);
        return points;
    }

    private AreaPointMapper pointMapper;
}
