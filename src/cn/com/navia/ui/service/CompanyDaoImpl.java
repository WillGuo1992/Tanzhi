// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CompanyDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.CompanyMapper;
import cn.com.navia.ui.mybatis.model.Company;
import cn.com.navia.ui.spring.base.SpringModule;

public class CompanyDaoImpl extends SpringModule
{

    public CompanyDaoImpl()
    {
    }

    public Company getCompanyById(int cid)
        throws Exception
    {
        return companyMapper.selectByPrimaryKey(Integer.valueOf(cid));
    }

    public int updateCompany(Company company)
        throws Exception
    {
        return companyMapper.updateByPrimaryKey(company);
    }

    private CompanyMapper companyMapper;
}
