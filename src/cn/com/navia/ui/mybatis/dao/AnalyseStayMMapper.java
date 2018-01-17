// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayMMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseStayM;
import cn.com.navia.ui.mybatis.model.AnalyseStayMExample;
import java.util.List;

public interface AnalyseStayMMapper
{

    public abstract int countByExample(AnalyseStayMExample analysestaymexample);

    public abstract int deleteByExample(AnalyseStayMExample analysestaymexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseStayM analysestaym);

    public abstract int insertSelective(AnalyseStayM analysestaym);

    public abstract List selectByExample(AnalyseStayMExample analysestaymexample);

    public abstract AnalyseStayM selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseStayM analysestaym, AnalyseStayMExample analysestaymexample);

    public abstract int updateByExample(AnalyseStayM analysestaym, AnalyseStayMExample analysestaymexample);

    public abstract int updateByPrimaryKeySelective(AnalyseStayM analysestaym);

    public abstract int updateByPrimaryKey(AnalyseStayM analysestaym);
}
