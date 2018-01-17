// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaCPointMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AreaCPoint;
import cn.com.navia.ui.mybatis.model.AreaCPointExample;
import java.util.List;

public interface AreaCPointMapper
{

    public abstract int countByExample(AreaCPointExample areacpointexample);

    public abstract int deleteByExample(AreaCPointExample areacpointexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AreaCPoint areacpoint);

    public abstract int insertSelective(AreaCPoint areacpoint);

    public abstract List selectByExample(AreaCPointExample areacpointexample);

    public abstract AreaCPoint selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AreaCPoint areacpoint, AreaCPointExample areacpointexample);

    public abstract int updateByExample(AreaCPoint areacpoint, AreaCPointExample areacpointexample);

    public abstract int updateByPrimaryKeySelective(AreaCPoint areacpoint);

    public abstract int updateByPrimaryKey(AreaCPoint areacpoint);
}
