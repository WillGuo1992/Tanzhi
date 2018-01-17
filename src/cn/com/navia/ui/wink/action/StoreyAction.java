// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StoreyAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.service.*;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

public class StoreyAction extends BaseAction
{

    public StoreyAction()
    {
    }

    public String getAllStoreysByMallId(String mallId)
    {
        JSONObject obj = new JSONObject();
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                obj.put("code", Integer.valueOf(0));
                obj.put("storeys", storeys);
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

    public String getStoreyIndoorCompareBar(String startDate, String endDate, String mallId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    List storeys = storeyDao.getStoreysByMallId(mall.getId().intValue());
                    if(storeys != null && storeys.size() > 0)
                    {
                        List datas = new ArrayList();
                        List lt5Datas = new ArrayList();
                        List compareDatas = new ArrayList();
                        List compareLt5Datas = new ArrayList();
                        Storey storey;
                        for(Iterator iterator = storeys.iterator(); iterator.hasNext(); compareLt5Datas.add(Integer.valueOf(stayExtDao.getLess5StayCountByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), storey.getGroupId().intValue()))))
                        {
                            storey = (Storey)iterator.next();
                            datas.add(Integer.valueOf(stayDao.getIndoorCountByDate(format1.parse(startDate), format1.parse(endDate), storey.getGroupId().intValue())));
                            compareDatas.add(Integer.valueOf(stayDao.getIndoorCountByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), storey.getGroupId().intValue())));
                            lt5Datas.add(Integer.valueOf(stayExtDao.getLess5StayCountByDate(format1.parse(startDate), format1.parse(endDate), storey.getGroupId().intValue())));
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("storeys", storeys);
                        obj.put("datas", datas);
                        obj.put("compareDatas", compareDatas);
                        obj.put("lt5Datas", lt5Datas);
                        obj.put("compareLt5Datas", compareLt5Datas);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-1));
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

    public String getStoreyIndoorNoCompareBar(String startDate, String endDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    List storeys = storeyDao.getStoreysByMallId(mall.getId().intValue());
                    if(storeys != null && storeys.size() > 0)
                    {
                        List datas = new ArrayList();
                        List lt5Datas = new ArrayList();
                        Storey storey;
                        for(Iterator iterator = storeys.iterator(); iterator.hasNext(); lt5Datas.add(Integer.valueOf(stayExtDao.getLess5StayCountByDate(format.parse(startDate), format.parse(endDate), storey.getGroupId().intValue()))))
                        {
                            storey = (Storey)iterator.next();
                            datas.add(Integer.valueOf(stayExtDao.getGt5StayCountByDate(format.parse(startDate), format.parse(endDate), storey.getGroupId().intValue())));
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("storeys", storeys);
                        obj.put("datas", datas);
                        obj.put("lt5Datas", lt5Datas);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-1));
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

    public String getStoreyResidenceNoCompareBar(String startDate, String endDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    List storeys = storeyDao.getStoreysByMallId(mall.getId().intValue());
                    if(storeys != null && storeys.size() > 0)
                    {
                        List datas = new ArrayList();
                        Storey storey;
                        for(Iterator iterator = storeys.iterator(); iterator.hasNext(); datas.add(Integer.valueOf(stayDao.getAvgResidenceTimeByDate(format.parse(startDate), format.parse(endDate), storey.getGroupId().intValue()))))
                            storey = (Storey)iterator.next();

                        obj.put("code", Integer.valueOf(0));
                        obj.put("storeys", storeys);
                        obj.put("datas", datas);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                } else
                {
                    obj.put("code", Integer.valueOf(-1));
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

    public String getStoreyResidenceCompareBar(String startDate, String endDate, String mallId, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    List storeys = storeyDao.getStoreysByMallId(mall.getId().intValue());
                    if(storeys != null && storeys.size() > 0)
                    {
                        List datas = new ArrayList();
                        List compareDatas = new ArrayList();
                        Storey storey;
                        for(Iterator iterator = storeys.iterator(); iterator.hasNext(); compareDatas.add(Integer.valueOf(stayDao.getAvgResidenceTimeByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), storey.getGroupId().intValue()))))
                        {
                            storey = (Storey)iterator.next();
                            datas.add(Integer.valueOf(stayDao.getAvgResidenceTimeByDate(format1.parse(startDate), format1.parse(endDate), storey.getGroupId().intValue())));
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("storeys", storeys);
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

    public String getComparedIndoorTrafficChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        log.info("storey/indoor/compare/chart params : startDate : {},endDate : {},compareDate : {},groupId : {}", new Object[] {
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
        log.info("storey/indoor/compare/chart results : {}", obj.toString());
        return obj.toString();
    }

    public String getNoComparedIndoorTrafficChart(String startDate, String endDate, String groupId)
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
        return obj.toString();
    }

    public String getIndoorStatistics(String startDateStr, String endDateStr, String groupId)
    {
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

    public String getComparedStoreyStayCountChart(String startDate, String endDate, String compareStartDate, String groupId)
    {
        log.info("storey/stay/count/compare/chart params : startDate : {},endDate : {},compareDate : {},groupId : {}", new Object[] {
            startDate, endDate, compareStartDate, groupId
        });
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
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
                List datas = extDao.getGt5StayListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareDatas = extDao.getGt5StayListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
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
        log.info("storey/stay/count/compare/chart results : {}", obj.toString());
        return obj.toString();
    }

    public String getNoComparedStoreyStayCountChart(String startDate, String endDate, String groupId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List datas = extDao.getGt5StayListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
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

    public String getComparedStoreyStayConverseChart(String startDate, String endDate, String compareStartDate, String groupId)
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
                List datas = stayDao.getStayGt5ConversionListByDate(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List compareDatas = stayDao.getStayGt5ConversionListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
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

    public String getNoComparedStoreyStayChart(String startDate, String endDate, String groupId)
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
                List datas = stayDao.getStayGt5ConversionListByDate(format.parse(startDate), format.parse(endDate), Integer.parseInt(groupId));
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

    public String getStayStatistics(String startDateStr, String endDateStr, String groupId)
    {
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
                int this_count = extDao.getGt5StayCountByDate(startDate, endDate, Integer.parseInt(groupId));
                int this_conversion = stayDao.getStayGt5ConversionByDate(startDate, endDate, Integer.parseInt(groupId));
                int oneday_ago_conversion = stayDao.getStayGt5ConversionByDate(DateUtils.addDays(startDate, -1), DateUtils.addDays(endDate, -1), Integer.parseInt(groupId));
                int week_ago_conversion = stayDao.getStayGt5ConversionByDate(DateUtils.addDays(startDate, -7), DateUtils.addDays(endDate, -7), Integer.parseInt(groupId));
                int month_ago_conversion = stayDao.getStayGt5ConversionByDate(DateUtils.addDays(startDate, -30), DateUtils.addDays(endDate, -30), Integer.parseInt(groupId));
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
        return obj.toString();
    }

    public String getNoComparedResideceDistribute(String startDateStr, String endDateStr, String groupId)
    {
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
                int less_5 = 0;
                int range_30_60 = 0;
                int range_5_30 = 0;
                int more_60 = 0;
                List exts = stayExtDao.getAnalyseStayExtDayRecords(startDate, endDate, Integer.parseInt(groupId));
                if(exts != null && exts.size() > 0)
                {
                    for(Iterator iterator = exts.iterator(); iterator.hasNext();)
                    {
                        AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                        less_5 += ext.getLt5().intValue();
                        range_30_60 += ext.getRange3060().intValue();
                        range_5_30 += ext.getRange530().intValue();
                        more_60 += ext.getGt60().intValue();
                    }

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("less_5", Integer.valueOf(less_5));
                obj.put("range_30_60", Integer.valueOf(range_30_60));
                obj.put("range_5_30", Integer.valueOf(range_5_30));
                obj.put("more_60", Integer.valueOf(more_60));
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
                int less_5 = 0;
                int range_30_60 = 0;
                int range_5_30 = 0;
                int more_60 = 0;
                int less_5_com = 0;
                int range_30_60_com = 0;
                int range_5_30_com = 0;
                int more_60_com = 0;
                List exts = stayExtDao.getAnalyseStayExtDayRecords(format1.parse(startDate), format1.parse(endDate), Integer.parseInt(groupId));
                List com_exts = stayExtDao.getAnalyseStayExtDayRecords(format1.parse(compareStartDate), format1.parse(compareEndDate), Integer.parseInt(groupId));
                if(exts != null && exts.size() > 0)
                {
                    for(Iterator iterator = exts.iterator(); iterator.hasNext();)
                    {
                        AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                        less_5 += ext.getLt5().intValue();
                        range_30_60 += ext.getRange3060().intValue();
                        range_5_30 += ext.getRange530().intValue();
                        more_60 += ext.getGt60().intValue();
                    }

                }
                if(com_exts != null && com_exts.size() > 0)
                {
                    for(Iterator iterator1 = com_exts.iterator(); iterator1.hasNext();)
                    {
                        AnalyseStayExt com_ext = (AnalyseStayExt)iterator1.next();
                        less_5_com += com_ext.getLt5().intValue();
                        range_30_60_com += com_ext.getRange3060().intValue();
                        range_5_30_com += com_ext.getRange530().intValue();
                        more_60_com += com_ext.getGt60().intValue();
                    }

                }
                obj.put("code", Integer.valueOf(0));
                obj.put("less_5", Integer.valueOf(less_5));
                obj.put("range_30_60", Integer.valueOf(range_30_60));
                obj.put("range_5_30", Integer.valueOf(range_5_30));
                obj.put("more_60", Integer.valueOf(more_60));
                obj.put("less_5_com", Integer.valueOf(less_5_com));
                obj.put("range_30_60_com", Integer.valueOf(range_30_60_com));
                obj.put("range_5_30_com", Integer.valueOf(range_5_30_com));
                obj.put("more_60_com", Integer.valueOf(more_60_com));
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
}
