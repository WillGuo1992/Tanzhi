// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayMDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseStayMMapper;
import cn.com.navia.ui.mybatis.model.AnalyseStayM;
import cn.com.navia.ui.mybatis.model.AnalyseStayMExample;
import cn.com.navia.ui.spring.base.SpringModule;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.time.DateUtils;

public class AnalyseStayMDaoImpl extends SpringModule
{

    public AnalyseStayMDaoImpl()
    {
    }

    public List getAnalyseStayMRecords(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        AnalyseStayMExample example = new AnalyseStayMExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId));
        return stayMMapper.selectByExample(example);
    }

    public List getMonthUnrepeatCountListByDate(Date startDate, Date endDate, int groupId)
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
            stays = getAnalyseStayMRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStayM)stays.get(0)).getUnreapeatCount());
            else
                result.add(null);
        }

        return result;
    }

    public int getMonthUnrepeatCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int count = 0;
        List records = getAnalyseStayMRecords(startDate, endDate, groupId);
        if(records != null && records.size() > 0)
        {
            for(Iterator iterator = records.iterator(); iterator.hasNext();)
            {
                AnalyseStayM record = (AnalyseStayM)iterator.next();
                count += record.getUnreapeatCount().intValue();
            }

        }
        return count;
    }

    private AnalyseStayMMapper stayMMapper;
}
