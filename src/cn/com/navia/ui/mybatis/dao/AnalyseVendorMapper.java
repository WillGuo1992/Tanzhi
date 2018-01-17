// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseVendorMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseVendor;
import cn.com.navia.ui.mybatis.model.AnalyseVendorExample;
import java.util.List;

public interface AnalyseVendorMapper
{

    public abstract int countByExample(AnalyseVendorExample analysevendorexample);

    public abstract int deleteByExample(AnalyseVendorExample analysevendorexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseVendor analysevendor);

    public abstract int insertSelective(AnalyseVendor analysevendor);

    public abstract List selectByExample(AnalyseVendorExample analysevendorexample);

    public abstract AnalyseVendor selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseVendor analysevendor, AnalyseVendorExample analysevendorexample);

    public abstract int updateByExample(AnalyseVendor analysevendor, AnalyseVendorExample analysevendorexample);

    public abstract int updateByPrimaryKeySelective(AnalyseVendor analysevendor);

    public abstract int updateByPrimaryKey(AnalyseVendor analysevendor);
}
