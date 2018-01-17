// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.MapMapper;
import cn.com.navia.ui.mybatis.model.Map;
import cn.com.navia.ui.mybatis.model.MapExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.List;

public class MapDaoImpl extends SpringModule
{

    public MapDaoImpl()
    {
    }

    public Map getMapByStoreyId(int storeyId)
        throws Exception
    {
        Map map = new Map();
        MapExample example = new MapExample();
        example.createCriteria().andStoreyIdEqualTo(Integer.valueOf(storeyId));
        List maps = mapMapper.selectByExample(example);
        if(maps != null && maps.size() > 0)
            map = (Map)maps.get(0);
        return map;
    }

    private MapMapper mapMapper;
}
