// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.service.*;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

public class CustomAction extends BaseAction
{

    public CustomAction()
    {
    }

    public String getSummStatistics(String groupId)
    {
        log.info("custom/summ/statistics params : groupId : {}", groupId);
        JSONObject obj = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId))
            {
                Date today = format.parse(format.format(new Date()));
                Date yesterday = DateUtils.addDays(today, -1);
                Date oneday_ago = DateUtils.addDays(yesterday, -1);
                Date week_ago = DateUtils.addDays(yesterday, -7);
                Date month_ago = DateUtils.addDays(yesterday, -30);
                int history_count = activeDao.getHistoryCountByDate(yesterday, Integer.parseInt(groupId));
                int this_count = activeDao.getActiveCountByDate(yesterday, Integer.parseInt(groupId));
                int oneday_ago_count = activeDao.getActiveCountByDate(oneday_ago, Integer.parseInt(groupId));
                int week_ago_count = activeDao.getActiveCountByDate(week_ago, Integer.parseInt(groupId));
                int month_ago_count = activeDao.getActiveCountByDate(month_ago, Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("history_count", Integer.valueOf(history_count));
                obj.put("this_count", Integer.valueOf(this_count));
                obj.put("oneday_ago_count", Integer.valueOf(oneday_ago_count));
                obj.put("week_ago_count", Integer.valueOf(week_ago_count));
                obj.put("month_ago_count", Integer.valueOf(month_ago_count));
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        log.info("custom/summ/statistics params : result : {}", obj.toString());
        return obj.toString();
    }

    public String getNoCompareHistoryChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List historys = activeDao.getHistoryCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", historys);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareHistoryChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List historys = activeDao.getHistoryCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareHistorys = activeDao.getHistoryCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", historys);
                obj.put("compareDatas", compareHistorys);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareCountChart(String startDate, String endDate, String groupId, String interval)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                List weeks = NaviaDateUtil.getWeekList(startDate, endDate);
                List months = NaviaDateUtil.getMonthList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                switch(Integer.parseInt(interval))
                {
                case 0: // '\0'
                    if(dates != null && dates.size() > 0)
                    {
                        List results = activeDao.getNewCustomCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", dates);
                        obj.put("datas", results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 1: // '\001'
                    if(weeks != null && weeks.size() > 0)
                    {
                        List results = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator = weeks.iterator(); iterator.hasNext(); results.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format.parse(s_date), format.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String week_area = (String)iterator.next();
                            s_date = (new StringBuilder(String.valueOf(week_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(week_area.split("~")[1]))).append(" 23:59:59").toString();
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", weeks);
                        obj.put("datas", results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 2: // '\002'
                    if(months != null && months.size() > 0)
                    {
                        List results = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator1 = months.iterator(); iterator1.hasNext(); results.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format.parse(s_date), format.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String month_area = (String)iterator1.next();
                            s_date = (new StringBuilder(String.valueOf(month_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(month_area.split("~")[1]))).append(" 23:59:59").toString();
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", months);
                        obj.put("datas", results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareCountChart(String startDate, String endDate, String groupId, String compareStartDate, String interval)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                List weeks = NaviaDateUtil.getWeekList(startDate, endDate);
                List months = NaviaDateUtil.getMonthList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                List com_weeks = NaviaDateUtil.getWeekList(compareStartDate, compareEndDate);
                List com_months = NaviaDateUtil.getMonthList(compareStartDate, compareEndDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                switch(Integer.parseInt(interval))
                {
                case 0: // '\0'
                    if(dates != null && dates.size() > 0)
                    {
                        List results = activeDao.getNewCustomCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                        List com_results = activeDao.getNewCustomCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", dates);
                        obj.put("datas", results);
                        obj.put("compareDatas", com_results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 1: // '\001'
                    if(weeks != null && weeks.size() > 0)
                    {
                        List results = new ArrayList();
                        List com_results = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator = weeks.iterator(); iterator.hasNext(); results.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(s_date), format1.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String week_area = (String)iterator.next();
                            s_date = (new StringBuilder(String.valueOf(week_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(week_area.split("~")[1]))).append(" 23:59:59").toString();
                        }

                        String com_s_date;
                        String com_e_date;
                        for(Iterator iterator1 = com_weeks.iterator(); iterator1.hasNext(); com_results.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(com_s_date), format1.parse(com_e_date), Integer.parseInt(groupId)))))
                        {
                            String com_week_area = (String)iterator1.next();
                            com_s_date = (new StringBuilder(String.valueOf(com_week_area.split("~")[0]))).append(" 00:00:00").toString();
                            com_e_date = (new StringBuilder(String.valueOf(com_week_area.split("~")[1]))).append(" 23:59:59").toString();
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", weeks);
                        obj.put("datas", results);
                        obj.put("compareDatas", com_results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 2: // '\002'
                    if(months != null && months.size() > 0)
                    {
                        List results = new ArrayList();
                        List com_results = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator2 = months.iterator(); iterator2.hasNext(); results.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(s_date), format1.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String month_area = (String)iterator2.next();
                            s_date = (new StringBuilder(String.valueOf(month_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(month_area.split("~")[1]))).append(" 23:59:59").toString();
                        }

                        String com_s_date;
                        String com_e_date;
                        for(Iterator iterator3 = com_months.iterator(); iterator3.hasNext(); com_results.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(com_s_date), format1.parse(com_e_date), Integer.parseInt(groupId)))))
                        {
                            String com_month_area = (String)iterator3.next();
                            com_s_date = (new StringBuilder(String.valueOf(com_month_area.split("~")[0]))).append(" 00:00:00").toString();
                            com_e_date = (new StringBuilder(String.valueOf(com_month_area.split("~")[1]))).append(" 23:59:59").toString();
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", months);
                        obj.put("datas", results);
                        obj.put("compareDatas", com_results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareRateChart(String startDate, String endDate, String groupId, String interval)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayWDaoImpl stayWDao = (AnalyseStayWDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayWDaoImpl);
        AnalyseStayMDaoImpl stayMDao = (AnalyseStayMDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayMDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                List weeks = NaviaDateUtil.getWeekList(startDate, endDate);
                List months = NaviaDateUtil.getMonthList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                switch(Integer.parseInt(interval))
                {
                case 0: // '\0'
                    if(dates != null && dates.size() > 0)
                    {
                        List results = new ArrayList();
                        List news = activeDao.getNewCustomCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                        List counts = stayDao.getUnrepeatCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                        for(int i = 0; i < counts.size(); i++)
                            if(((Integer)counts.get(i)).intValue() != 0)
                                results.add(Integer.valueOf((((Integer)news.get(i)).intValue() * 100) / ((Integer)counts.get(i)).intValue()));
                            else
                                results.add(Integer.valueOf(0));

                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", dates);
                        obj.put("datas", results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 1: // '\001'
                    if(weeks != null && weeks.size() > 0)
                    {
                        List results = new ArrayList();
                        List news = new ArrayList();
                        List counts = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator = weeks.iterator(); iterator.hasNext(); counts.add(Integer.valueOf(stayWDao.getWeekUnrepeatCountByDate(format.parse(s_date), format.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String week_area = (String)iterator.next();
                            s_date = (new StringBuilder(String.valueOf(week_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(week_area.split("~")[1]))).append(" 23:59:59").toString();
                            news.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format.parse(s_date), format.parse(e_date), Integer.parseInt(groupId))));
                        }

                        if(counts.size() > 0)
                        {
                            for(int i = 0; i < counts.size(); i++)
                                if(((Integer)counts.get(i)).intValue() != 0)
                                    results.add(Integer.valueOf((((Integer)news.get(i)).intValue() * 100) / ((Integer)counts.get(i)).intValue()));
                                else
                                    results.add(Integer.valueOf(0));

                            obj.put("code", Integer.valueOf(0));
                            obj.put("dates", weeks);
                            obj.put("datas", results);
                        } else
                        {
                            obj.put("code", Integer.valueOf(-2));
                        }
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 2: // '\002'
                    if(months != null && months.size() > 0)
                    {
                        List results = new ArrayList();
                        List news = new ArrayList();
                        List counts = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator1 = months.iterator(); iterator1.hasNext(); counts.add(Integer.valueOf(stayMDao.getMonthUnrepeatCountByDate(format.parse(s_date), format.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String month_area = (String)iterator1.next();
                            s_date = (new StringBuilder(String.valueOf(month_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(month_area.split("~")[1]))).append(" 23:59:59").toString();
                            news.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format.parse(s_date), format.parse(e_date), Integer.parseInt(groupId))));
                        }

                        if(counts.size() > 0)
                        {
                            for(int i = 0; i < counts.size(); i++)
                                if(((Integer)counts.get(i)).intValue() != 0)
                                    results.add(Integer.valueOf((((Integer)news.get(i)).intValue() * 100) / ((Integer)counts.get(i)).intValue()));
                                else
                                    results.add(Integer.valueOf(0));

                            obj.put("code", Integer.valueOf(0));
                            obj.put("dates", months);
                            obj.put("datas", results);
                        } else
                        {
                            obj.put("code", Integer.valueOf(-2));
                        }
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareRateChart(String startDate, String endDate, String groupId, String compareStartDate, String interval)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayWDaoImpl stayWDao = (AnalyseStayWDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayWDaoImpl);
        AnalyseStayMDaoImpl stayMDao = (AnalyseStayMDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayMDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                List weeks = NaviaDateUtil.getWeekList(startDate, endDate);
                List months = NaviaDateUtil.getMonthList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                List com_weeks = NaviaDateUtil.getWeekList(compareStartDate, compareEndDate);
                List com_months = NaviaDateUtil.getMonthList(compareStartDate, compareEndDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                switch(Integer.parseInt(interval))
                {
                case 0: // '\0'
                    if(dates != null && dates.size() > 0)
                    {
                        List results = new ArrayList();
                        List com_results = new ArrayList();
                        List news = activeDao.getNewCustomCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                        List com_news = activeDao.getNewCustomCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                        List counts = stayDao.getUnrepeatCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                        List com_counts = stayDao.getUnrepeatCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                        for(int i = 0; i < counts.size(); i++)
                            if(((Integer)counts.get(i)).intValue() != 0)
                                results.add(Integer.valueOf((((Integer)news.get(i)).intValue() * 100) / ((Integer)counts.get(i)).intValue()));
                            else
                                results.add(Integer.valueOf(0));

                        for(int j = 0; j < com_counts.size(); j++)
                            if(((Integer)com_counts.get(j)).intValue() != 0)
                                com_results.add(Integer.valueOf((((Integer)com_news.get(j)).intValue() * 100) / ((Integer)com_counts.get(j)).intValue()));
                            else
                                com_results.add(Integer.valueOf(0));

                        obj.put("code", Integer.valueOf(0));
                        obj.put("dates", dates);
                        obj.put("datas", results);
                        obj.put("compareDatas", com_results);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 1: // '\001'
                    if(weeks != null && weeks.size() > 0)
                    {
                        List results = new ArrayList();
                        List news = new ArrayList();
                        List counts = new ArrayList();
                        List com_results = new ArrayList();
                        List com_news = new ArrayList();
                        List com_counts = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator = weeks.iterator(); iterator.hasNext(); counts.add(Integer.valueOf(stayWDao.getWeekUnrepeatCountByDate(format1.parse(s_date), format1.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String week_area = (String)iterator.next();
                            s_date = (new StringBuilder(String.valueOf(week_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(week_area.split("~")[1]))).append(" 23:59:59").toString();
                            news.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(s_date), format1.parse(e_date), Integer.parseInt(groupId))));
                        }

                        String com_s_date;
                        String com_e_date;
                        for(Iterator iterator1 = com_weeks.iterator(); iterator1.hasNext(); com_counts.add(Integer.valueOf(stayWDao.getWeekUnrepeatCountByDate(format1.parse(com_s_date), format1.parse(com_e_date), Integer.parseInt(groupId)))))
                        {
                            String com_week_area = (String)iterator1.next();
                            com_s_date = (new StringBuilder(String.valueOf(com_week_area.split("~")[0]))).append(" 00:00:00").toString();
                            com_e_date = (new StringBuilder(String.valueOf(com_week_area.split("~")[1]))).append(" 23:59:59").toString();
                            com_news.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(com_s_date), format1.parse(com_e_date), Integer.parseInt(groupId))));
                        }

                        if(counts.size() > 0 && com_counts.size() > 0)
                        {
                            for(int i = 0; i < counts.size(); i++)
                                if(((Integer)counts.get(i)).intValue() != 0)
                                    results.add(Integer.valueOf((((Integer)news.get(i)).intValue() * 100) / ((Integer)counts.get(i)).intValue()));
                                else
                                    results.add(Integer.valueOf(0));

                            for(int i = 0; i < com_counts.size(); i++)
                                if(((Integer)com_counts.get(i)).intValue() != 0)
                                    com_results.add(Integer.valueOf((((Integer)com_news.get(i)).intValue() * 100) / ((Integer)com_counts.get(i)).intValue()));
                                else
                                    com_results.add(Integer.valueOf(0));

                            obj.put("code", Integer.valueOf(0));
                            obj.put("dates", weeks);
                            obj.put("datas", results);
                            obj.put("compareDatas", com_results);
                        } else
                        {
                            obj.put("code", Integer.valueOf(-2));
                        }
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;

                case 2: // '\002'
                    if(months != null && months.size() > 0)
                    {
                        List results = new ArrayList();
                        List news = new ArrayList();
                        List counts = new ArrayList();
                        List com_results = new ArrayList();
                        List com_news = new ArrayList();
                        List com_counts = new ArrayList();
                        String s_date;
                        String e_date;
                        for(Iterator iterator2 = months.iterator(); iterator2.hasNext(); counts.add(Integer.valueOf(stayMDao.getMonthUnrepeatCountByDate(format1.parse(s_date), format1.parse(e_date), Integer.parseInt(groupId)))))
                        {
                            String month_area = (String)iterator2.next();
                            s_date = (new StringBuilder(String.valueOf(month_area.split("~")[0]))).append(" 00:00:00").toString();
                            e_date = (new StringBuilder(String.valueOf(month_area.split("~")[1]))).append(" 23:59:59").toString();
                            news.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(s_date), format1.parse(e_date), Integer.parseInt(groupId))));
                        }

                        String com_s_date;
                        String com_e_date;
                        for(Iterator iterator3 = com_months.iterator(); iterator3.hasNext(); com_counts.add(Integer.valueOf(stayMDao.getMonthUnrepeatCountByDate(format1.parse(com_s_date), format1.parse(com_e_date), Integer.parseInt(groupId)))))
                        {
                            String com_month_area = (String)iterator3.next();
                            com_s_date = (new StringBuilder(String.valueOf(com_month_area.split("~")[0]))).append(" 00:00:00").toString();
                            com_e_date = (new StringBuilder(String.valueOf(com_month_area.split("~")[1]))).append(" 23:59:59").toString();
                            com_news.add(Integer.valueOf(activeDao.getNewCustomCountByDate(format1.parse(com_s_date), format1.parse(com_e_date), Integer.parseInt(groupId))));
                        }

                        if(counts.size() > 0 && com_counts.size() > 0)
                        {
                            for(int i = 0; i < counts.size(); i++)
                                if(((Integer)counts.get(i)).intValue() != 0)
                                    results.add(Integer.valueOf((((Integer)news.get(i)).intValue() * 100) / ((Integer)counts.get(i)).intValue()));
                                else
                                    results.add(Integer.valueOf(0));

                            for(int i = 0; i < com_counts.size(); i++)
                                if(((Integer)com_counts.get(i)).intValue() != 0)
                                    com_results.add(Integer.valueOf((((Integer)com_news.get(i)).intValue() * 100) / ((Integer)com_counts.get(i)).intValue()));
                                else
                                    com_results.add(Integer.valueOf(0));

                            obj.put("code", Integer.valueOf(0));
                            obj.put("dates", months);
                            obj.put("datas", results);
                            obj.put("compareDatas", com_results);
                        } else
                        {
                            obj.put("code", Integer.valueOf(-2));
                        }
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                    break;
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareLostChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List losts = activeDao.getLostCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", losts);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareReactiveChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List reactives = activeDao.getReActiveCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", reactives);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareLostChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List losts = activeDao.getLostCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareLosts = activeDao.getLostCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", losts);
                obj.put("compareDatas", compareLosts);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareReActiveChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List reactives = activeDao.getReActiveCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareReactives = activeDao.getReActiveCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", reactives);
                obj.put("compareDatas", compareReactives);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareCurrentChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List results = new ArrayList();
                List actives = activeDao.getCurrentActiveCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                List unrepeats = stayDao.getUnrepeatCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                if(unrepeats != null && unrepeats.size() > 0 && actives != null && actives.size() > 0 && unrepeats.size() == actives.size())
                {
                    for(int i = 0; i < unrepeats.size(); i++)
                        if(((Integer)unrepeats.get(i)).intValue() != 0)
                            results.add(Integer.valueOf((((Integer)actives.get(i)).intValue() * 100) / ((Integer)unrepeats.get(i)).intValue()));
                        else
                            results.add(Integer.valueOf(0));

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareThirtyDaysChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List results = activeDao.getActiveCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareIncreaseChart(String startDate, String endDate, String groupId, String dayTab)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(dayTab))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                List results = new ArrayList();
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                Date startDate_a = new Date();
                Date endDate_a = new Date();
                List actives = new ArrayList();
                List actives_a = new ArrayList();
                switch(Integer.parseInt(dayTab))
                {
                case 0: // '\0'
                    startDate_a = DateUtils.addDays(format.parse(startDate), -1);
                    endDate_a = DateUtils.addDays(format.parse(endDate), -1);
                    break;

                case 1: // '\001'
                    startDate_a = DateUtils.addDays(format.parse(startDate), -7);
                    endDate_a = DateUtils.addDays(format.parse(endDate), -7);
                    break;

                case 2: // '\002'
                    startDate_a = DateUtils.addDays(format.parse(startDate), -30);
                    endDate_a = DateUtils.addDays(format.parse(endDate), -30);
                    break;
                }
                actives = activeDao.getActiveCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                actives_a = activeDao.getActiveCountListByDate(startDate_a, endDate_a, Integer.parseInt(groupId));
                if(actives.size() > 0 && actives_a.size() > 0 && actives.size() == actives_a.size())
                {
                    for(int i = 0; i < actives.size(); i++)
                        results.add(Integer.valueOf(((Integer)actives.get(i)).intValue() - ((Integer)actives_a.get(i)).intValue()));

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareCurrentChart(String startDate, String endDate, String groupId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List results = new ArrayList();
                List com_results = new ArrayList();
                List actives = activeDao.getCurrentActiveCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List unrepeats = stayDao.getUnrepeatCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_actives = activeDao.getCurrentActiveCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                List com_unrepeats = stayDao.getUnrepeatCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                if(unrepeats != null && unrepeats.size() > 0 && actives != null && actives.size() > 0 && unrepeats.size() == actives.size())
                {
                    for(int i = 0; i < unrepeats.size(); i++)
                        if(((Integer)unrepeats.get(i)).intValue() != 0)
                            results.add(Integer.valueOf((((Integer)actives.get(i)).intValue() * 100) / ((Integer)unrepeats.get(i)).intValue()));
                        else
                            results.add(Integer.valueOf(0));

                }
                if(com_actives.size() > 0 && com_unrepeats.size() > 0 && com_actives.size() == com_unrepeats.size())
                {
                    for(int i = 0; i < com_actives.size(); i++)
                        if(((Integer)com_unrepeats.get(i)).intValue() != 0)
                            com_results.add(Integer.valueOf((((Integer)com_actives.get(i)).intValue() * 100) / ((Integer)com_unrepeats.get(i)).intValue()));
                        else
                            com_results.add(Integer.valueOf(0));

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", results);
                obj.put("compareDatas", com_results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareThirtyDaysChart(String startDate, String endDate, String groupId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List results = activeDao.getActiveCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_results = activeDao.getActiveCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", results);
                obj.put("compareDatas", com_results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareIncreaseChart(String startDate, String endDate, String groupId, String dayTab, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveDaoImpl activeDao = (AnalyseActiveDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(dayTab) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                Date startDate_a = new Date();
                Date endDate_a = new Date();
                Date compareStartDate_a = new Date();
                Date compareEndDate_a = new Date();
                List results = new ArrayList();
                List com_results = new ArrayList();
                List actives = new ArrayList();
                List actives_a = new ArrayList();
                List compareActives = new ArrayList();
                List compareActives_a = new ArrayList();
                switch(Integer.parseInt(dayTab))
                {
                case 0: // '\0'
                    startDate_a = DateUtils.addDays(format1.parse(startDate), -1);
                    endDate_a = DateUtils.addDays(format1.parse(endDate), -1);
                    compareStartDate_a = DateUtils.addDays(format1.parse(compareStartDate), -1);
                    compareEndDate_a = DateUtils.addDays(format1.parse(compareEndDate), -1);
                    break;

                case 1: // '\001'
                    startDate_a = DateUtils.addDays(format1.parse(startDate), -7);
                    endDate_a = DateUtils.addDays(format1.parse(endDate), -7);
                    compareStartDate_a = DateUtils.addDays(format1.parse(compareStartDate), -7);
                    compareEndDate_a = DateUtils.addDays(format1.parse(compareEndDate), -7);
                    break;

                case 2: // '\002'
                    startDate_a = DateUtils.addDays(format1.parse(startDate), -30);
                    endDate_a = DateUtils.addDays(format1.parse(endDate), -30);
                    compareStartDate_a = DateUtils.addDays(format1.parse(compareStartDate), -30);
                    compareEndDate_a = DateUtils.addDays(format1.parse(compareEndDate), -30);
                    break;
                }
                actives = activeDao.getActiveCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                actives_a = activeDao.getActiveCountListByDate(startDate_a, endDate_a, Integer.parseInt(groupId));
                compareActives = activeDao.getActiveCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                compareActives_a = activeDao.getActiveCountListByDate(compareStartDate_a, compareEndDate_a, Integer.parseInt(groupId));
                if(actives.size() > 0 && actives_a.size() > 0 && actives.size() == actives_a.size())
                {
                    for(int i = 0; i < actives.size(); i++)
                        results.add(Integer.valueOf(((Integer)actives.get(i)).intValue() - ((Integer)actives_a.get(i)).intValue()));

                }
                if(compareActives.size() > 0 && compareActives_a.size() > 0 && compareActives.size() == compareActives_a.size())
                {
                    for(int i = 0; i < compareActives.size(); i++)
                        com_results.add(Integer.valueOf(((Integer)compareActives.get(i)).intValue() - ((Integer)compareActives_a.get(i)).intValue()));

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", results);
                obj.put("compareDatas", com_results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getNoCompareDistributeChart(String date, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveExtDaoImpl activeExtDao = (AnalyseActiveExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveExtDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(date))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startDate = (new StringBuilder(String.valueOf(date))).append(" 00:00:00").toString();
                String endDate = (new StringBuilder(String.valueOf(date))).append(" 23:59:59").toString();
                List results = new ArrayList();
                results.add(Integer.valueOf(activeExtDao.getEq4ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq5ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq6ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq7ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq8ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq9ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq10ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getGt10ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                obj.put("code", Integer.valueOf(0));
                obj.put("datas", results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getCompareDistributeChart(String date, String compareDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseActiveExtDaoImpl activeExtDao = (AnalyseActiveExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseActiveExtDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(compareDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startDate = (new StringBuilder(String.valueOf(date))).append(" 00:00:00").toString();
                String endDate = (new StringBuilder(String.valueOf(date))).append(" 23:59:59").toString();
                String compareStartDate = (new StringBuilder(String.valueOf(date))).append(" 00:00:00").toString();
                String compareEndDate = (new StringBuilder(String.valueOf(date))).append(" 23:59:59").toString();
                List results = new ArrayList();
                List com_results = new ArrayList();
                results.add(Integer.valueOf(activeExtDao.getEq4ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq5ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq6ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq7ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq8ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq9ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getEq10ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                results.add(Integer.valueOf(activeExtDao.getGt10ActiveCountByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq4ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq5ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq6ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq7ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq8ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq9ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getEq10ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                com_results.add(Integer.valueOf(activeExtDao.getGt10ActiveCountByDate(format.parse(compareStartDate), format.parse(compareEndDate), Integer.parseInt(groupId))));
                obj.put("code", Integer.valueOf(0));
                obj.put("datas", results);
                obj.put("compareDatas", com_results);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            obj.put("code", Integer.valueOf(-9));
            e.printStackTrace();
        }
        return obj.toString();
    }
}
