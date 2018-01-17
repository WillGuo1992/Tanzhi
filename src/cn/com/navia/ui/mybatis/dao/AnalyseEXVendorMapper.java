// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseEXVendorMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.AnalyseEXVendor;
import cn.com.navia.ui.mybatis.model.AnalyseEXVendorExample;
import java.util.List;

public interface AnalyseEXVendorMapper
{

    public abstract int countByExample(AnalyseEXVendorExample analyseexvendorexample);

    public abstract int deleteByExample(AnalyseEXVendorExample analyseexvendorexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(AnalyseEXVendor analyseexvendor);

    public abstract int insertSelective(AnalyseEXVendor analyseexvendor);

    public abstract List selectByExample(AnalyseEXVendorExample analyseexvendorexample);

    public abstract AnalyseEXVendor selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(AnalyseEXVendor analyseexvendor, AnalyseEXVendorExample analyseexvendorexample);

    public abstract int updateByExample(AnalyseEXVendor analyseexvendor, AnalyseEXVendorExample analyseexvendorexample);

    public abstract int updateByPrimaryKeySelective(AnalyseEXVendor analyseexvendor);

    public abstract int updateByPrimaryKey(AnalyseEXVendor analyseexvendor);
}
