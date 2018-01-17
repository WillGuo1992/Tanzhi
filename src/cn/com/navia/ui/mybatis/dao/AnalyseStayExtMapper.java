// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayExtMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseStayExt;
import cn.com.navia.ui.mybatis.model.AnalyseStayExtExample;
import java.util.List;

public interface AnalyseStayExtMapper
{

    public abstract int countByExample(AnalyseStayExtExample analysestayextexample);

    public abstract int deleteByExample(AnalyseStayExtExample analysestayextexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseStayExt analysestayext);

    public abstract int insertSelective(AnalyseStayExt analysestayext);

    public abstract List selectByExample(AnalyseStayExtExample analysestayextexample);

    public abstract AnalyseStayExt selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseStayExt analysestayext, AnalyseStayExtExample analysestayextexample);

    public abstract int updateByExample(AnalyseStayExt analysestayext, AnalyseStayExtExample analysestayextexample);

    public abstract int updateByPrimaryKeySelective(AnalyseStayExt analysestayext);

    public abstract int updateByPrimaryKey(AnalyseStayExt analysestayext);
}
