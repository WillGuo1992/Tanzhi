// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayExtDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseStayExtMapper;
import cn.com.navia.ui.mybatis.model.AnalyseStayExt;
import cn.com.navia.ui.mybatis.model.AnalyseStayExtExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.time.DateUtils;

public class AnalyseStayExtDaoImpl extends SpringModule
{

    public AnalyseStayExtDaoImpl()
    {
    }

    //query : gid 
    public List getAnalyseStayExtDayRecords(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        AnalyseStayExtExample example = new AnalyseStayExtExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId));
        return stayExtMapper.selectByExample(example);
    }
  //query : analyseId
    public AnalyseStayExt getAnalyseStayExtRecordByAnaId(int analyseId)
        throws Exception
    {
        AnalyseStayExtExample example = new AnalyseStayExtExample();
        example.createCriteria().andAnalyIdEqualTo(Integer.valueOf(analyseId));
        List exts = stayExtMapper.selectByExample(example);
        if(exts != null && exts.size() > 0)
            return (AnalyseStayExt)exts.get(0);
        else
            return null;
    }

    public List getBusinessAnalyseStayExtDayRecords(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        AnalyseStayExtExample example = new AnalyseStayExtExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andBIdEqualTo(Integer.valueOf(businessId));
        return stayExtMapper.selectByExample(example);
    }

    public List getGt10StayListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        List result = new ArrayList();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayExtDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStayExt)stays.get(0)).getGt10());
            else
                result.add(null);
        }

        return result;
    }

    public List getBusinessGt10StayListByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        List result = new ArrayList();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getBusinessAnalyseStayExtDayRecords(indexDate, nextDate, businessId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStayExt)stays.get(0)).getGt10());
            else
                result.add(null);
        }

        return result;
    }

    public List getGt5StayListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        List result = new ArrayList();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayExtDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStayExt)stays.get(0)).getGt5());
            else
                result.add(null);
        }

        return result;
    }

    public int getLess5StayCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List exts = getAnalyseStayExtDayRecords(startDate, endDate, groupId);
        if(exts != null && exts.size() > 0)
        {
            for(Iterator iterator = exts.iterator(); iterator.hasNext();)
            {
                AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                sum += ext.getLt5().intValue();
            }

        }
        return sum;
    }

    public int getGt5StayCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List exts = getAnalyseStayExtDayRecords(startDate, endDate, groupId);
        if(exts != null && exts.size() > 0)
        {
            for(Iterator iterator = exts.iterator(); iterator.hasNext();)
            {
                AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                sum += ext.getGt5().intValue();
            }

        }
        return sum;
    }

    public int getGt10StayCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List exts = getAnalyseStayExtDayRecords(startDate, endDate, groupId);
        if(exts != null && exts.size() > 0)
        {
            for(Iterator iterator = exts.iterator(); iterator.hasNext();)
            {
                AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                sum += ext.getGt10().intValue();
            }

        }
        return sum;
    }

    public int getBusinessLess10StayCountByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        int sum = 0;
        List exts = getBusinessAnalyseStayExtDayRecords(startDate, endDate, businessId);
        if(exts != null && exts.size() > 0)
        {
            for(Iterator iterator = exts.iterator(); iterator.hasNext();)
            {
                AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                sum += ext.getLt10().intValue();
            }

        }
        return sum;
    }

    public int getBusinessGt10StayCountByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        int sum = 0;
        List exts = getBusinessAnalyseStayExtDayRecords(startDate, endDate, businessId);
        if(exts != null && exts.size() > 0)
        {
            for(Iterator iterator = exts.iterator(); iterator.hasNext();)
            {
                AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                sum += ext.getGt10().intValue();
            }

        }
        return sum;
    }

    private AnalyseStayExtMapper stayExtMapper;
}
