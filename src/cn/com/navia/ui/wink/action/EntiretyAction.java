// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntiretyAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.service.*;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

public class EntiretyAction extends BaseAction
{

    public EntiretyAction()
    {
    }

    public String getComparedEntiretyTrafficChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        log.info("entirety/indoor/compare/chart params : startDate:{},endDate:{},compareDate:{},groupId:{}", new Object[] {
            startDate, endDate, compareStartDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                log.info((new StringBuilder(String.valueOf(compareStartDate))).append("----").append(compareEndDate).toString());
                List datas = stayDao.getIndoorCountListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareDatas = stayDao.getIndoorCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
                obj.put("compareDatas", compareDatas);
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
        log.info("entirety/indoor/compare/chart results:{}", obj.toString());
        return obj.toString();
    }

    public String getNoComparedEntiretyTrafficChart(String startDate, String endDate, String groupId)
    {
        log.info("entirety/indoor/nocompare/chart params : startDate:{},endDate:{},groupId:{}", new Object[] {
            startDate, endDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List datas = stayDao.getIndoorCountListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
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
        log.info("entirety/indoor/nocompare/chart results:{}", obj.toString());
        return obj.toString();
    }

    public String getIndoorStatistics(String startDateStr, String endDateStr, String groupId)
    {
        log.info("entirety/indoor/statistics params : startDate:{},endDate:{},groupId:{}", new Object[] {
            startDateStr, endDateStr, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDateStr = (new StringBuilder(String.valueOf(startDateStr))).append(" 00:00:00").toString();
                endDateStr = (new StringBuilder(String.valueOf(endDateStr))).append(" 23:59:59").toString();
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);
                int this_count = stayDao.getIndoorCountByDate(startDate, endDate, Integer.parseInt(groupId));
                int oneday_ago_count = stayDao.getIndoorCountByDate(DateUtils.addDays(startDate, -1), DateUtils.addDays(endDate, -1), Integer.parseInt(groupId));
                int week_ago_count = stayDao.getIndoorCountByDate(DateUtils.addDays(startDate, -7), DateUtils.addDays(endDate, -7), Integer.parseInt(groupId));
                int month_ago_count = stayDao.getIndoorCountByDate(DateUtils.addDays(startDate, -30), DateUtils.addDays(endDate, -30), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("this_count", Integer.valueOf(this_count));
                obj.put("oneday_ago_count", Integer.valueOf(oneday_ago_count));
                obj.put("week_ago_count", Integer.valueOf(week_ago_count));
                obj.put("month_ago_count", Integer.valueOf(month_ago_count));
                log.info("entirety/indoor/statistics results:{}", obj.toString());
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

    public String getComparedEntiretyStayCountChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        log.info("entirety/stay/count/compare/chart params : startDate : {},endDate : {},compareDate : {},groupId : {}", new Object[] {
            startDate, endDate, compareStartDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List datas = stayExtDao.getGt10StayListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareDatas = stayExtDao.getGt10StayListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
                obj.put("compareDatas", compareDatas);
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
        log.info("entirety/stay/count/compare/chart results : {}", obj.toString());
        return obj.toString();
    }

    public String getNoComparedEntiretyStayCountChart(String startDate, String endDate, String groupId)
    {
        log.info("entirety/stay/count/nocompare/chart params : startDate : {},endDate : {},groupId : {}", new Object[] {
            startDate, endDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List datas = stayExtDao.getGt10StayListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
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
        log.info("entirety/stay/count/nocompare/chart results : {}", obj.toString());
        return obj.toString();
    }

    public String getComparedEntiretyStayConverseChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        log.info("entirety/stay/conversion/compare/chart params : startDate : {},endDate : {},compareDate : {},groupId : {}", new Object[] {
            startDate, endDate, compareStartDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List datas = stayDao.getStayGt10ConversionListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareDatas = stayDao.getStayGt10ConversionListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("dates", dates);
                obj.put("datas", datas);
                obj.put("compareDatas", compareDatas);
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
        log.info("entirety/stay/conversion/compare/chart results :  {}", obj.toString());
        return obj.toString();
    }

    public String getNoComparedEntiretyStayChart(String startDate, String endDate, String groupId)
    {
        log.info("entirety/stay/conversion/nocompare/chart params : startDate : {},endDate : {},groupId : {}", new Object[] {
            startDate, endDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List datas = stayDao.getStayGt10ConversionListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
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
        log.info("entirety/stay/conversion/nocompare/chart results : {}", obj.toString());
        return obj.toString();
    }

    public String getStayStatistics(String startDateStr, String endDateStr, String groupId)
    {
        log.info("entirety/stay/statistics params : startDate : {},endDate : {},compareDate : {},groupId : {}", new Object[] {
            startDateStr, endDateStr, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDateStr = (new StringBuilder(String.valueOf(startDateStr))).append(" 00:00:00").toString();
                endDateStr = (new StringBuilder(String.valueOf(endDateStr))).append(" 23:59:59").toString();
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);
                int this_count = extDao.getGt10StayCountByDate(startDate, endDate, Integer.parseInt(groupId));
                int this_conversion = stayDao.getStayGt10ConversionByDate(startDate, endDate, Integer.parseInt(groupId));
                int oneday_ago_conversion = stayDao.getStayGt10ConversionByDate(DateUtils.addDays(startDate, -1), DateUtils.addDays(endDate, -1), Integer.parseInt(groupId));
                int week_ago_conversion = stayDao.getStayGt10ConversionByDate(DateUtils.addDays(startDate, -7), DateUtils.addDays(endDate, -7), Integer.parseInt(groupId));
                int month_ago_conversion = stayDao.getStayGt10ConversionByDate(DateUtils.addDays(startDate, -30), DateUtils.addDays(endDate, -30), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("this_count", Integer.valueOf(this_count));
                obj.put("this_conversion", Integer.valueOf(this_conversion));
                obj.put("oneday_ago_conversion", Integer.valueOf(oneday_ago_conversion));
                obj.put("week_ago_conversion", Integer.valueOf(week_ago_conversion));
                obj.put("month_ago_conversion", Integer.valueOf(month_ago_conversion));
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
        log.info("entirety/stay/statistics results : {}", obj.toString());
        return obj.toString();
    }

    public String getNoComparedResideceDistribute(String startDateStr, String endDateStr, String groupId)
    {
        log.info("entirety/residence/nocompare/bar params : startDate : {},endDate : {},compareDate : {},groupId : {}", new Object[] {
            startDateStr, endDateStr, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDateStr = (new StringBuilder(String.valueOf(startDateStr))).append(" 00:00:00").toString();
                endDateStr = (new StringBuilder(String.valueOf(endDateStr))).append(" 23:59:59").toString();
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);
                int less_60 = 0;
                int range_60_120 = 0;
                int range_120_180 = 0;
                int more_180 = 0;
                List exts = stayExtDao.getAnalyseStayExtDayRecords(startDate, endDate, Integer.parseInt(groupId));
                if(exts != null && exts.size() > 0)
                {
                    for(Iterator iterator = exts.iterator(); iterator.hasNext();)
                    {
                        AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                        less_60 += ext.getLt60().intValue();
                        range_60_120 += ext.getRange60120().intValue();
                        range_120_180 += ext.getRange120180().intValue();
                        more_180 += ext.getGt180().intValue();
                    }

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("less_60", Integer.valueOf(less_60));
                obj.put("range_60_120", Integer.valueOf(range_60_120));
                obj.put("range_120_180", Integer.valueOf(range_120_180));
                obj.put("more_180", Integer.valueOf(more_180));
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
        log.info("entirety/residence/nocompare/bar results : {}", obj.toString());
        return obj.toString();
    }

    public String getComparedResideceDistribute(String startDate, String endDate, String compareStartDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                int less_60 = 0;
                int range_60_120 = 0;
                int range_120_180 = 0;
                int more_180 = 0;
                int less_60_com = 0;
                int range_60_120_com = 0;
                int range_120_180_com = 0;
                int more_180_com = 0;
                List exts = stayExtDao.getAnalyseStayExtDayRecords(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_exts = stayExtDao.getAnalyseStayExtDayRecords(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                if(exts != null && exts.size() > 0)
                {
                    for(Iterator iterator = exts.iterator(); iterator.hasNext();)
                    {
                        AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                        less_60 += ext.getLt60().intValue();
                        range_60_120 += ext.getRange60120().intValue();
                        range_120_180 += ext.getRange120180().intValue();
                        more_180 += ext.getGt180().intValue();
                    }

                }
                if(com_exts != null && com_exts.size() > 0)
                {
                    for(Iterator iterator1 = com_exts.iterator(); iterator1.hasNext();)
                    {
                        AnalyseStayExt com_ext = (AnalyseStayExt)iterator1.next();
                        less_60_com += com_ext.getLt60().intValue();
                        range_60_120_com += com_ext.getRange60120().intValue();
                        range_120_180_com += com_ext.getRange120180().intValue();
                        more_180_com += com_ext.getGt180().intValue();
                    }

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("less_60", Integer.valueOf(less_60));
                obj.put("range_60_120", Integer.valueOf(range_60_120));
                obj.put("range_120_180", Integer.valueOf(range_120_180));
                obj.put("more_180", Integer.valueOf(more_180));
                obj.put("less_60_com", Integer.valueOf(less_60_com));
                obj.put("range_60_120_com", Integer.valueOf(range_60_120_com));
                obj.put("range_120_180_com", Integer.valueOf(range_120_180_com));
                obj.put("more_180_com", Integer.valueOf(more_180_com));
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

    public String getCompareResidenceTimeChart(String startDate, String endDate, String groupId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(compareStartDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List datas = stayDao.getResidenceTimeListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareDatas = stayDao.getResidenceTimeListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
                obj.put("compareDatas", compareDatas);
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

    public String getNoCompareResidenceTimeChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List datas = stayDao.getResidenceTimeListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("datas", datas);
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

    public String getEntryNoCompareRate(String startDate, String endDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DecimalFormat df = new DecimalFormat("#.00");
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                List entrys = areaDao.getEntrysByMallId(Integer.parseInt(mallId));
                if(entrys != null && entrys.size() > 0)
                {
                    int entry_sum = 0;
                    List datas = new ArrayList();
                    List enDatas = new ArrayList();
                    for(Iterator iterator = entrys.iterator(); iterator.hasNext();)
                    {
                        Area entry = (Area)iterator.next();
                        int area_in = stayDao.getIndoorCountByDate(format.parse(startDate), format.parse(endDate), entry.getGroupId().intValue());
                        enDatas.add(Integer.valueOf(area_in));
                        entry_sum += area_in;
                    }

                    for(int i = 0; i < enDatas.size(); i++)
                        if(entry_sum != 0)
                            datas.add(Double.valueOf(df.format((double)(((Integer)enDatas.get(i)).intValue() * 100) / (double)entry_sum)));
                        else
                            datas.add(Double.valueOf(0.0D));

                    obj.put("code", Integer.valueOf(0));
                    obj.put("entrys", entrys);
                    obj.put("datas", datas);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
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

    public String getEntryCompareRate(String startDate, String endDate, String mallId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                DecimalFormat df = new DecimalFormat("#.00");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                List entrys = areaDao.getEntrysByMallId(Integer.parseInt(mallId));
                if(entrys != null && entrys.size() > 0)
                {
                    List datas = new ArrayList();
                    List compareDatas = new ArrayList();
                    List enDatas = new ArrayList();
                    List enCdatas = new ArrayList();
                    int en_sum = 0;
                    int en_csum = 0;
                    int com_in;
                    for(Iterator iterator = entrys.iterator(); iterator.hasNext(); enCdatas.add(Integer.valueOf(com_in)))
                    {
                        Area entry = (Area)iterator.next();
                        int area_in = stayDao.getIndoorCountByDate(format1.parse(startDate), format1.parse(endDate), entry.getGroupId().intValue());
                        com_in = stayDao.getIndoorCountByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), entry.getGroupId().intValue());
                        en_sum += area_in;
                        enDatas.add(Integer.valueOf(area_in));
                        en_csum += com_in;
                    }

                    for(int i = 0; i < enDatas.size(); i++)
                        if(en_sum != 0)
                            datas.add(Double.valueOf(df.format((double)(((Integer)enDatas.get(i)).intValue() * 100) / (double)en_sum)));
                        else
                            datas.add(Double.valueOf(0.0D));

                    for(int i = 0; i < enCdatas.size(); i++)
                        if(en_csum != 0)
                            compareDatas.add(Double.valueOf(df.format((double)(((Integer)enCdatas.get(i)).intValue() * 100) / (double)en_csum)));
                        else
                            compareDatas.add(Double.valueOf(0.0D));

                    obj.put("code", Integer.valueOf(0));
                    obj.put("entrys", entrys);
                    obj.put("datas", datas);
                    obj.put("compareDatas", compareDatas);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
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

    public String getEntryNoCompareChart(String startDate, String endDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        List results = new ArrayList();
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                List entrys = areaDao.getEntrysByMallId(Integer.parseInt(mallId));
                if(entrys != null && entrys.size() > 0)
                {
                    JSONObject entryData;
                    for(Iterator iterator = entrys.iterator(); iterator.hasNext(); results.add(entryData))
                    {
                        Area entry = (Area)iterator.next();
                        List datas = stayDao.getEntryIndoorRatesByDate(format.parse(startDate), format.parse(endDate), entry.getGroupId().intValue(), mall.getGroupId().intValue(), entrys);
                        entryData = new JSONObject();
                        entryData.put("name", entry.getName());
                        entryData.put("data", datas);
                    }

                    obj.put("code", Integer.valueOf(0));
                    obj.put("dates", dates);
                    obj.put("datas", results);
                } else
                {
                    obj.put("code", Integer.valueOf(-2));
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
        log.info("entirety/entry/nocampare/chart results : {}", obj.toString());
        return obj.toString();
    }
}
