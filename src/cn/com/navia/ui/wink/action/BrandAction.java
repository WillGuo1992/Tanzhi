// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BrandAction.java

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
import org.springframework.context.ApplicationContext;

public class BrandAction extends BaseAction
{

    public BrandAction()
    {
    }

    public String getAllBrandsByMallId(String mallId)
    {
        JSONObject obj = new JSONObject();
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                List brands = areaDao.getImportantAreasByMallId(Integer.parseInt(mallId));
                obj.put("code", Integer.valueOf(0));
                obj.put("brands", brands);
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

   

    public String getBrandIndoorNoCompareBar(String startDate, String endDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
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
                    List brands = areaDao.getImportantAreasByMallId(mall.getId().intValue());
                    if(brands != null && brands.size() > 0)
                    {
                        List datas = new ArrayList();
                        List lt5Datas = new ArrayList();
                        Area brand;
                        for(Iterator iterator = brands.iterator(); iterator.hasNext(); lt5Datas.add(Integer.valueOf(stayExtDao.getLess5StayCountByDate(format.parse(startDate), format.parse(endDate), brand.getGroupId().intValue()))))
                        {
                            brand = (Area)iterator.next();
                            datas.add(Integer.valueOf(stayDao.getIndoorCountByDate(format.parse(startDate), format.parse(endDate), brand.getGroupId().intValue())));
                        }

                        obj.put("code", Integer.valueOf(0));
                        obj.put("brands", brands);
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

    public String getBrandResidenceNoCompareBar(String startDate, String endDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
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
                    List brands = areaDao.getImportantAreasByMallId(mall.getId().intValue());
                    if(brands != null && brands.size() > 0)
                    {
                        List datas = new ArrayList();
                        Area brand;
                        for(Iterator iterator = brands.iterator(); iterator.hasNext(); datas.add(Integer.valueOf(stayDao.getAvgResidenceTimeByDate(format.parse(startDate), format.parse(endDate), brand.getGroupId().intValue()))))
                            brand = (Area)iterator.next();

                        obj.put("code", Integer.valueOf(0));
                        obj.put("brands", brands);
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

   

    public String getNoComparedBrandStayCountChart(String startDate, String endDate, String groupId)
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

    public String getNoComparedBrandStayChart(String startDate, String endDate, String groupId)
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
