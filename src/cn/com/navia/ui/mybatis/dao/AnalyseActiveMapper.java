// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseActiveMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseActive;
import cn.com.navia.ui.mybatis.model.AnalyseActiveExample;
import java.util.List;

public interface AnalyseActiveMapper
{

    public abstract int countByExample(AnalyseActiveExample analyseactiveexample);

    public abstract int deleteByExample(AnalyseActiveExample analyseactiveexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseActive analyseactive);

    public abstract int insertSelective(AnalyseActive analyseactive);

    public abstract List selectByExample(AnalyseActiveExample analyseactiveexample);

    public abstract AnalyseActive selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseActive analyseactive, AnalyseActiveExample analyseactiveexample);

    public abstract int updateByExample(AnalyseActive analyseactive, AnalyseActiveExample analyseactiveexample);

    public abstract int updateByPrimaryKeySelective(AnalyseActive analyseactive);

    public abstract int updateByPrimaryKey(AnalyseActive analyseactive);
}
