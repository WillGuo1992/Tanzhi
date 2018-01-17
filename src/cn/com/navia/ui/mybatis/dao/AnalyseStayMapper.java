// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseStay;
import cn.com.navia.ui.mybatis.model.AnalyseStayExample;
import java.util.List;

public interface AnalyseStayMapper
{

    public abstract int countByExample(AnalyseStayExample analysestayexample);

    public abstract int deleteByExample(AnalyseStayExample analysestayexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseStay analysestay);

    public abstract int insertSelective(AnalyseStay analysestay);

    public abstract List selectByExample(AnalyseStayExample analysestayexample);

    public abstract AnalyseStay selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseStay analysestay, AnalyseStayExample analysestayexample);

    public abstract int updateByExample(AnalyseStay analysestay, AnalyseStayExample analysestayexample);

    public abstract int updateByPrimaryKeySelective(AnalyseStay analysestay);

    public abstract int updateByPrimaryKey(AnalyseStay analysestay);
}
