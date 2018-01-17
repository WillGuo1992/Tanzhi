// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GradationMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Gradation;
import cn.com.navia.ui.mybatis.model.GradationExample;
import java.util.List;

public interface GradationMapper
{

    public abstract int countByExample(GradationExample gradationexample);

    public abstract int deleteByExample(GradationExample gradationexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Gradation gradation);

    public abstract int insertSelective(Gradation gradation);

    public abstract List selectByExample(GradationExample gradationexample);

    public abstract Gradation selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Gradation gradation, GradationExample gradationexample);

    public abstract int updateByExample(Gradation gradation, GradationExample gradationexample);

    public abstract int updateByPrimaryKeySelective(Gradation gradation);

    public abstract int updateByPrimaryKey(Gradation gradation);
}
