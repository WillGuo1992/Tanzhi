// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompanyMapper.java

package cn.com.navia.ui.mybatis.dao;

import cn.com.navia.ui.mybatis.model.Company;
import cn.com.navia.ui.mybatis.model.CompanyExample;
import java.util.List;

public interface CompanyMapper
{

    public abstract int countByExample(CompanyExample companyexample);

    public abstract int deleteByExample(CompanyExample companyexample);

    public abstract int deleteByPrimaryKey(Integer integer);

    public abstract int insert(Company company);

    public abstract int insertSelective(Company company);

    public abstract List selectByExample(CompanyExample companyexample);

    public abstract Company selectByPrimaryKey(Integer integer);

    public abstract int updateByExampleSelective(Company company, CompanyExample companyexample);

    public abstract int updateByExample(Company company, CompanyExample companyexample);

    public abstract int updateByPrimaryKeySelective(Company company);

    public abstract int updateByPrimaryKey(Company company);
}
