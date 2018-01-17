// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Map;
import cn.com.navia.ui.mybatis.model.MapExample;
import java.util.List;

public interface MapMapper
{

    public abstract int countByExample(MapExample mapexample);

    public abstract int deleteByExample(MapExample mapexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Map map);

    public abstract int insertSelective(Map map);

    public abstract List selectByExample(MapExample mapexample);

    public abstract Map selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Map map, MapExample mapexample);

    public abstract int updateByExample(Map map, MapExample mapexample);

    public abstract int updateByPrimaryKeySelective(Map map);

    public abstract int updateByPrimaryKey(Map map);
}
