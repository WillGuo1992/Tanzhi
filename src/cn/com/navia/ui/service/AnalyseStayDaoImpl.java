// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnalyseStayDaoImpl.java

package cn.com.navia.ui.service;

import cn.com.navia.ui.mybatis.dao.AnalyseStayMapper;
import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.spring.base.SpringModule;
import cn.com.navia.ui.utils.NaviaDateUtil;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.ApplicationContext;

// Referenced classes of package cn.com.navia.ui.service:
//            AnalyseStayExtDaoImpl

public class AnalyseStayDaoImpl extends SpringModule
{

    public AnalyseStayDaoImpl()
    {
    }

    //query: gid && bid=0
    public List getAnalyseStayDayRecords(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        AnalyseStayExample example = new AnalyseStayExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andGIdEqualTo(Integer.valueOf(groupId)).andBIdEqualTo(Integer.valueOf(0));
        return stayMapper.selectByExample(example);
    }

    //query: bid
    public List getBusinessAnalyseStayDayRecords(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        AnalyseStayExample example = new AnalyseStayExample();
        example.setOrderByClause("data_time");
        example.createCriteria().andDataTimeBetween(startDate, endDate).andBIdEqualTo(Integer.valueOf(businessId));
        return stayMapper.selectByExample(example);
    }

    public List getIndoorCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStay)stays.get(0)).getRepeatCount());
            else
                result.add(null);
        }

        return result;
    }

    public List getUnrepeatCountListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStay)stays.get(0)).getUnrepeatCount());
            else
                result.add(null);
        }

        return result;
    }

    public List getBusinessIndoorCountListByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getBusinessAnalyseStayDayRecords(indexDate, nextDate, businessId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStay)stays.get(0)).getRepeatCount());
            else
                result.add(null);
        }

        return result;
    }

    public int getIndoorCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List stays = getAnalyseStayDayRecords(startDate, endDate, groupId);
        if(stays != null && stays.size() > 0)
        {
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                sum += stay.getRepeatCount().intValue();
            }

        }
        return sum;
    }

    public int getUnRepeatCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List stays = getAnalyseStayDayRecords(startDate, endDate, groupId);
        if(stays != null && stays.size() > 0)
        {
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                sum += stay.getUnrepeatCount().intValue();
            }

        }
        return sum;
    }

    public int getBusinessIndoorCountByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        int sum = 0;
        List stays = getBusinessAnalyseStayDayRecords(startDate, endDate, businessId);
        if(stays != null && stays.size() > 0)
        {
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                sum += stay.getRepeatCount().intValue();
            }

        }
        return sum;
    }

    public List getStayGt10ConversionListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
            {
                AnalyseStayExt ext = extDao.getAnalyseStayExtRecordByAnaId(((AnalyseStay)stays.get(0)).getId().intValue());
                if(((AnalyseStay)stays.get(0)).getRepeatCount().intValue() != 0)
                    result.add(Integer.valueOf((ext.getGt10().intValue() * 100) / ((AnalyseStay)stays.get(0)).getRepeatCount().intValue()));
                else
                    result.add(Integer.valueOf(0));
            } else
            {
                result.add(null);
            }
        }

        return result;
    }

    public List getStayGt5ConversionListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
            {
                AnalyseStayExt ext = extDao.getAnalyseStayExtRecordByAnaId(((AnalyseStay)stays.get(0)).getId().intValue());
                if(((AnalyseStay)stays.get(0)).getRepeatCount().intValue() != 0)
                    result.add(Integer.valueOf((ext.getGt5().intValue() * 100) / ((AnalyseStay)stays.get(0)).getRepeatCount().intValue()));
                else
                    result.add(Integer.valueOf(0));
            } else
            {
                result.add(null);
            }
        }

        return result;
    }

    public List getBusinessStayConversionListByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getBusinessAnalyseStayDayRecords(indexDate, nextDate, businessId);
            if(stays != null && stays.size() > 0)
            {
                AnalyseStayExt ext = extDao.getAnalyseStayExtRecordByAnaId(((AnalyseStay)stays.get(0)).getId().intValue());
                if(((AnalyseStay)stays.get(0)).getRepeatCount().intValue() != 0)
                    result.add(Integer.valueOf((ext.getGt10().intValue() * 100) / ((AnalyseStay)stays.get(0)).getRepeatCount().intValue()));
                else
                    result.add(Integer.valueOf(0));
            } else
            {
                result.add(null);
            }
        }

        return result;
    }

    public int getStayGt10ConversionByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        List stays = getAnalyseStayDayRecords(startDate, endDate, groupId);
        if(stays != null && stays.size() > 0)
        {
            int indoorCount = 0;
            int stayCount = 0;
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                AnalyseStayExt ext = extDao.getAnalyseStayExtRecordByAnaId(stay.getId().intValue());
                indoorCount += stay.getRepeatCount().intValue();
                stayCount += ext.getGt10().intValue();
            }

            if(indoorCount != 0)
                sum = (stayCount * 100) / indoorCount;
        }
        return sum;
    }

    public int getStayGt5ConversionByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        List stays = getAnalyseStayDayRecords(startDate, endDate, groupId);
        if(stays != null && stays.size() > 0)
        {
            int indoorCount = 0;
            int stayCount = 0;
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                AnalyseStayExt ext = extDao.getAnalyseStayExtRecordByAnaId(stay.getId().intValue());
                indoorCount += stay.getRepeatCount().intValue();
                stayCount += ext.getGt5().intValue();
            }

            if(indoorCount != 0)
                sum = (stayCount * 100) / indoorCount;
        }
        return sum;
    }

    public int getBusinessStayConversionByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        int sum = 0;
        List stays = getBusinessAnalyseStayDayRecords(startDate, endDate, businessId);
        if(stays != null && stays.size() > 0)
        {
            int indoorCount = 0;
            int stayCount = 0;
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                indoorCount += stay.getRepeatCount().intValue();
                stayCount += stay.getStayCount().intValue();
            }

            if(indoorCount != 0)
                sum = (stayCount * 100) / indoorCount;
        }
        return sum;
    }

    public int getStayCountByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        List stays = getAnalyseStayDayRecords(startDate, endDate, groupId);
        if(stays != null && stays.size() > 0)
        {
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                sum += stay.getStayCount().intValue();
            }

        }
        return sum;
    }

    public int getBusinessStayCountByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        int sum = 0;
        List stays = getBusinessAnalyseStayDayRecords(startDate, endDate, businessId);
        if(stays != null && stays.size() > 0)
        {
            for(Iterator iterator = stays.iterator(); iterator.hasNext();)
            {
                AnalyseStay stay = (AnalyseStay)iterator.next();
                sum += stay.getStayCount().intValue();
            }

        }
        return sum;
    }

    public List getResidenceTimeListByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getAnalyseStayDayRecords(indexDate, nextDate, groupId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStay)stays.get(0)).getAvgStayTime());
            else
                result.add(null);
        }

        return result;
    }

    public List getBusinessResidenceTimeListByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result = new ArrayList();
        List stays = new ArrayList();
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            stays = getBusinessAnalyseStayDayRecords(indexDate, nextDate, businessId);
            if(stays != null && stays.size() > 0)
                result.add(((AnalyseStay)stays.get(0)).getAvgStayTime());
            else
                result.add(null);
        }

        return result;
    }

    public int getAvgResidenceTimeByDate(Date startDate, Date endDate, int groupId)
        throws Exception
    {
        int sum = 0;
        int avg = 0;
        int count = NaviaDateUtil.getDayCounts(startDate, endDate);
        if(count > 0)
        {
            List stays = getAnalyseStayDayRecords(startDate, endDate, groupId);
            if(stays != null && stays.size() > 0)
            {
                for(Iterator iterator = stays.iterator(); iterator.hasNext();)
                {
                    AnalyseStay stay = (AnalyseStay)iterator.next();
                    sum += stay.getAvgStayTime().intValue();
                }

                avg = sum / count;
            }
        }
        return avg;
    }

    public int getBusinessAvgResidenceTimeByDate(Date startDate, Date endDate, int businessId)
        throws Exception
    {
        int sum = 0;
        int avg = 0;
        int count = NaviaDateUtil.getDayCounts(startDate, endDate);
        if(count > 0)
        {
            List stays = getBusinessAnalyseStayDayRecords(startDate, endDate, businessId);
            if(stays != null && stays.size() > 0)
            {
                for(Iterator iterator = stays.iterator(); iterator.hasNext();)
                {
                    AnalyseStay stay = (AnalyseStay)iterator.next();
                    sum += stay.getAvgStayTime().intValue();
                }

                avg = sum / count;
            }
        }
        return avg;
    }

    public int getEntrysIndoorSumByDate(Date startDate, Date endDate, List areas)
        throws Exception
    {
        int sum = 0;
        if(areas.size() > 0)
        {
            for(Iterator iterator = areas.iterator(); iterator.hasNext();)
            {
                Area area = (Area)iterator.next();
                List stays = getAnalyseStayDayRecords(startDate, endDate, area.getGroupId().intValue());
                if(stays != null && stays.size() > 0)
                {
                    for(Iterator iterator1 = stays.iterator(); iterator1.hasNext();)
                    {
                        AnalyseStay stay = (AnalyseStay)iterator1.next();
                        sum += stay.getRepeatCount().intValue();
                    }

                }
            }

        }
        return sum;
    }

    public List getEntryIndoorRatesByDate(Date startDate, Date endDate, int entry_groupId, int mall_groupId, List areas)
        throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List result = new ArrayList();
        List mall_datas = new ArrayList();
        List entry_datas = new ArrayList();
        DecimalFormat df = new DecimalFormat("#.00");
        for(Date indexDate = startDate; indexDate.before(endDate); indexDate = DateUtils.addDays(indexDate, 1))
        {
            String nextDateStr = (new StringBuilder(String.valueOf(sdf1.format(indexDate)))).append(" 23:59:59").toString();
            Date nextDate = sdf2.parse(nextDateStr);
            int sum = getEntrysIndoorSumByDate(indexDate, nextDate, areas);
            entry_datas = getAnalyseStayDayRecords(indexDate, nextDate, entry_groupId);
            if(entry_datas != null && entry_datas.size() > 0)
            {
                if(sum != 0)
                    result.add(Double.valueOf(df.format((double)(((AnalyseStay)entry_datas.get(0)).getRepeatCount().intValue() * 100) / (double)sum)));
                else
                    result.add(Double.valueOf(0.0D));
            } else
            {
                result.add(null);
            }
        }

        return result;
    }

    private AnalyseStayMapper stayMapper;
}
