// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayWMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseStayW;
import cn.com.navia.ui.mybatis.model.AnalyseStayWExample;
import java.util.List;

public interface AnalyseStayWMapper
{

    public abstract int countByExample(AnalyseStayWExample analysestaywexample);

    public abstract int deleteByExample(AnalyseStayWExample analysestaywexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseStayW analysestayw);

    public abstract int insertSelective(AnalyseStayW analysestayw);

    public abstract List selectByExample(AnalyseStayWExample analysestaywexample);

    public abstract AnalyseStayW selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseStayW analysestayw, AnalyseStayWExample analysestaywexample);

    public abstract int updateByExample(AnalyseStayW analysestayw, AnalyseStayWExample analysestaywexample);

    public abstract int updateByPrimaryKeySelective(AnalyseStayW analysestayw);

    public abstract int updateByPrimaryKey(AnalyseStayW analysestayw);
}
