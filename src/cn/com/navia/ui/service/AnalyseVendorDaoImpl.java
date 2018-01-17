// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseVendorDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseVendorMapper;
import cn.com.navia.ui.mybatis.model.AnalyseVendorExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.*;

public class AnalyseVendorDaoImpl extends SpringModule
{

    public AnalyseVendorDaoImpl()
    {
    }

    public List getVendorsByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        List results = new ArrayList();
        AnalyseVendorExample example = new AnalyseVendorExample();
        example.createCriteria().andDataTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId));
        results = vendorMapper.selectByExample(example);
        return results;
    }

    private AnalyseVendorMapper vendorMapper;
}
