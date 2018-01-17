// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseActiveExtMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseActiveExt;
import cn.com.navia.ui.mybatis.model.AnalyseActiveExtExample;
import java.util.List;

public interface AnalyseActiveExtMapper
{

    public abstract int countByExample(AnalyseActiveExtExample analyseactiveextexample);

    public abstract int deleteByExample(AnalyseActiveExtExample analyseactiveextexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseActiveExt analyseactiveext);

    public abstract int insertSelective(AnalyseActiveExt analyseactiveext);

    public abstract List selectByExample(AnalyseActiveExtExample analyseactiveextexample);

    public abstract AnalyseActiveExt selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseActiveExt analyseactiveext, AnalyseActiveExtExample analyseactiveextexample);

    public abstract int updateByExample(AnalyseActiveExt analyseactiveext, AnalyseActiveExtExample analyseactiveextexample);

    public abstract int updateByPrimaryKeySelective(AnalyseActiveExt analyseactiveext);

    public abstract int updateByPrimaryKey(AnalyseActiveExt analyseactiveext);
}
