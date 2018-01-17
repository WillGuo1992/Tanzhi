// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseActiveExtDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseActiveExtMapper;
import cn.com.navia.ui.mybatis.model.AnalyseActiveExt;
import cn.com.navia.ui.mybatis.model.AnalyseActiveExtExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.util.*;

public class AnalyseActiveExtDaoImpl extends SpringModule
{

    public AnalyseActiveExtDaoImpl()
    {
    }

    public List getActiveExtRecords(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        AnalyseActiveExtExample example = new AnalyseActiveExtExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId));
        return activeExtMapper.selectByExample(example);
    }

    public int getEq4ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq4count().intValue();
            }

        }
        return count;
    }

    public int getEq5ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq5count().intValue();
            }

        }
        return count;
    }

    public int getEq6ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq6count().intValue();
            }

        }
        return count;
    }

    public int getEq7ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq7count().intValue();
            }

        }
        return count;
    }

    public int getEq8ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq8count().intValue();
            }

        }
        return count;
    }

    public int getEq9ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq9count().intValue();
            }

        }
        return count;
    }

    public int getEq10ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getEq10count().intValue();
            }

        }
        return count;
    }

    public int getGt10ActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getActiveExtRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseActiveExt record = (AnalyseActiveExt)iterator.next();
                count += record.getGt10count().intValue();
            }

        }
        return count;
    }

    private AnalyseActiveExtMapper activeExtMapper;
}
