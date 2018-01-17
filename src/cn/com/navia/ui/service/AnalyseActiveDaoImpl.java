// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseActiveDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseActiveMapper;
import cn.com.navia.ui.mybatis.model.AnalyseActive;
import cn.com.navia.ui.mybatis.model.AnalyseActiveExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;

public class AnalyseActiveDaoImpl extends SpringModule
{

    public AnalyseActiveDaoImpl()
    {
    }
    //gid
    public List getActivesDayRecords(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        AnalyseActiveExample example = new AnalyseActiveExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId));
        return activeMapper.selectByExample(example);
    }

    public int getHistoryCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List actives = getActivesDayRecords(startDate, endDate, groupId);
        if(actives != null && actives.size() > 0)
            count = ((AnalyseActive)actives.get(actives.size() - 1)).getHistoryCount().intValue();
        return count;
    }

    public int getHistoryCountByDate(Date date, int groupId)
        throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = (new StringBuilder(String.valueOf(format.format(date)))).append(" 00:00:00").toString();
        String endDate = (new StringBuilder(String.valueOf(format.format(date)))).append(" 23:59:59").toString();
        log.info("history : s : {},e: {},g: {}", new Object[] {
            startDate, endDate, Integer.valueOf(groupId)
        });
        int count = 0;
        List actives = getActivesDayRecords(format.parse(startDate), format.parse(endDate), groupId);
        if(actives != null && actives.size() > 0)
        {
            log.info(actives.toString());
            count = ((AnalyseActive)actives.get(actives.size() - 1)).getHistoryCount().intValue();
        }
        return count;
    }

    public int getActiveCountByDate(Date date, int groupId)
        throws Exception
    {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = (new StringBuilder(String.valueOf(format1.format(date)))).append(" 00:00:00").toString();
        String endDate = (new StringBuilder(String.valueOf(format1.format(date)))).append(" 23:59:59").toString();
        log.info("startDate : {},endDate : {}", startDate, endDate);
        int count = 0;
        List actives = getActivesDayRecords(format.parse(startDate), format.parse(endDate), groupId);
        log.info("actives : {}", actives.toString());
        if(actives != null && actives.size() > 0)
            count = ((AnalyseActive)actives.get(0)).getActiveCount().intValue();
        return count;
    }

    public List getHistoryCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List results = new ArrayList();
        List actives = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            actives = getActivesDayRecords(indexDate, nextDate, groupId);
            if(actives != null && actives.size() > 0)
                results.add(((AnalyseActive)actives.get(0)).getHistoryCount());
            else
                results.add(null);
        }

        return results;
    }

    public List getLostCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List results = new ArrayList();
        List actives = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            actives = getActivesDayRecords(indexDate, nextDate, groupId);
            if(actives != null && actives.size() > 0)
                results.add(((AnalyseActive)actives.get(0)).getLostCount());
            else
                results.add(null);
        }

        return results;
    }

    public List getReActiveCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List results = new ArrayList();
        List actives = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            actives = getActivesDayRecords(indexDate, nextDate, groupId);
            if(actives != null && actives.size() > 0)
                results.add(((AnalyseActive)actives.get(0)).getReActiveCount());
            else
                results.add(null);
        }

        return results;
    }

    public List getCurrentActiveCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List results = new ArrayList();
        List actives = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            actives = getActivesDayRecords(indexDate, nextDate, groupId);
            if(actives != null && actives.size() > 0)
                results.add(((AnalyseActive)actives.get(0)).getCurActiveCount());
            else
                results.add(null);
        }

        return results;
    }

    public int getCurrentActiveCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List actives = getActivesDayRecords(startDate, endDate, groupId);
        if(actives != null && actives.size() > 0)
        {
            for(Iterator iterator = actives.iterator(); iterator.hasNext();)
            {
                AnalyseActive active = (AnalyseActive)iterator.next();
                sum += active.getCurActiveCount().intValue();
            }

        }
        return sum;
    }

    public List getNewCustomCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List results = new ArrayList();
        List actives = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            actives = getActivesDayRecords(indexDate, nextDate, groupId);
            if(actives != null && actives.size() > 0)
                results.add(((AnalyseActive)actives.get(0)).getNewCount());
            else
                results.add(null);
        }

        return results;
    }

    public List getActiveCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List results = new ArrayList();
        List actives = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            actives = getActivesDayRecords(indexDate, nextDate, groupId);
            if(actives != null && actives.size() > 0)
                results.add(((AnalyseActive)actives.get(0)).getActiveCount());
            else
                results.add(null);
        }

        return results;
    }

    public int getNewCustomCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List actives = getActivesDayRecords(startDate, endDate, groupId);
        if(actives != null && actives.size() > 0)
        {
            for(Iterator iterator = actives.iterator(); iterator.hasNext();)
            {
                AnalyseActive active = (AnalyseActive)iterator.next();
                count += active.getNewCount().intValue();
            }

        }
        return count;
    }

    private AnalyseActiveMapper activeMapper;
}
