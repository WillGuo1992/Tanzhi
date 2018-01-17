// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MallMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Mall;
import cn.com.navia.ui.mybatis.model.MallExample;
import java.util.List;

public interface MallMapper
{

    public abstract int countByExample(MallExample mallexample);

    public abstract int deleteByExample(MallExample mallexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Mall mall);

    public abstract int insertSelective(Mall mall);

    public abstract List selectByExample(MallExample mallexample);

    public abstract Mall selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Mall mall, MallExample mallexample);

    public abstract int updateByExample(Mall mall, MallExample mallexample);

    public abstract int updateByPrimaryKeySelective(Mall mall);

    public abstract int updateByPrimaryKey(Mall mall);
}
