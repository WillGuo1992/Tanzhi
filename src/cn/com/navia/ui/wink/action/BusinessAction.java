// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinessAction.java

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

public class BusinessAction extends BaseAction
{
    public class ComparatorJSON
        implements Comparator
    {

        public int compare(Object arg0, Object arg1)
        {
            JSONObject obj0 = (JSONObject)arg0;
            JSONObject obj1 = (JSONObject)arg1;
            int count0 = obj0.getInt("all");
            int count1 = obj1.getInt("all");
            return count0 <= count1 ? 1 : -1;
        }

        final BusinessAction this$0;

        public ComparatorJSON()
        {
            this$0 = BusinessAction.this;
            super();
        }
    }


    public BusinessAction()
    {
    }

    public String getBusinessListByGid(String groupId)
    {
        JSONObject obj = new JSONObject();
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId))
            {
                List list = businessDao.getAllBusinessByGroupId(Integer.parseInt(groupId));
                if(list != null && list.size() > 0)
                {
                    obj.put("code", Integer.valueOf(0));
                    obj.put("info", list);
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

    public String getBusinessListByType(String mallId, String businessType)
    {
        JSONObject obj = new JSONObject();
        List result = new ArrayList();
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(businessType))
            {
                int mBid = 0;
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    List blist = businessDao.getBusinessByGidandType(mall.getGroupId().intValue(), Integer.parseInt(businessType));
                    if(blist != null && blist.size() > 0)
                        mBid = ((Business)blist.get(0)).getbId().intValue();
                }
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                if(storeys != null && storeys.size() > 0)
                {
                    for(Iterator iterator = storeys.iterator(); iterator.hasNext();)
                    {
                        Storey storey = (Storey)iterator.next();
                        List businesses = businessDao.getBusinessByGidandType(storey.getGroupId().intValue(), Integer.parseInt(businessType));
                        if(businesses != null && businesses.size() > 0)
                            result.add((Business)businesses.get(0));
                    }

                    obj.put("code", Integer.valueOf(0));
                    obj.put("info", result);
                    obj.put("mbid", Integer.valueOf(mBid));
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

    public String getBusinessIdByTypeAndGid(String groupId, String businessType)
    {
        JSONObject obj = new JSONObject();
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType))
            {
                List businesses = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(businesses != null && businesses.size() > 0)
                {
                    obj.put("code", Integer.valueOf(0));
                    obj.put("info", ((Business)businesses.get(0)).getbId());
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

    public String getBusinessTypeListByGid(String groupId)
    {
        JSONObject obj = new JSONObject();
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(groupId))
            {
                List types = businessDao.getAllBusinessTypeByGroupId(Integer.parseInt(groupId));
                if(types != null && types.size() > 0)
                {
                    obj.put("code", Integer.valueOf(0));
                    obj.put("types", types);
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

    public String getBusinessIndoorNoCompareBar(String startDate, String endDate, String groupId, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List types = businessDao.getAllBusinessTypeByGroupId(mallDao.getMallById(Integer.parseInt(mallId)).getGroupId().intValue());
                if(types != null && types.size() > 0)
                {
                    List datas = new ArrayList();
                    for(Iterator iterator = types.iterator(); iterator.hasNext();)
                    {
                        BusinessType type = (BusinessType)iterator.next();
                        JSONObject data = new JSONObject();
                        data.put("name", type.getbName());
                        List businessList = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), type.getbType().intValue());
                        log.info("business sum stay : businessid : {}", ((Business)businessList.get(0)).getbId());
                        if(businessList != null && businessList.size() > 0)
                        {
                            data.put("all", Integer.valueOf(stayDao.getBusinessIndoorCountByDate(format.parse(startDate), format.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("lt10", Integer.valueOf(stayExtDao.getBusinessLess10StayCountByDate(format.parse(startDate), format.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("gt10", Integer.valueOf(stayExtDao.getBusinessGt10StayCountByDate(format.parse(startDate), format.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            datas.add(data);
                        }
                    }

                    ComparatorJSON comparator = new ComparatorJSON();
                    Collections.sort(datas, comparator);
                    obj.put("code", Integer.valueOf(0));
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
        log.info("business/summ/indoor/nocompare/bar results : {}", obj.toString());
        return obj.toString();
    }

    public String getBusinessIndoorCompareBar(String startDate, String endDate, String groupId, String compareStartDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
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
                List types = businessDao.getAllBusinessTypeByGroupId(mallDao.getMallById(Integer.parseInt(mallId)).getGroupId().intValue());
                if(types != null && types.size() > 0)
                {
                    List datas = new ArrayList();
                    for(Iterator iterator = types.iterator(); iterator.hasNext();)
                    {
                        BusinessType type = (BusinessType)iterator.next();
                        JSONObject data = new JSONObject();
                        data.put("name", type.getbName());
                        List businessList = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), type.getbType().intValue());
                        if(businessList != null && businessList.size() > 0)
                        {
                            data.put("lt10", Integer.valueOf(stayExtDao.getBusinessLess10StayCountByDate(format1.parse(startDate), format1.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("gt10", Integer.valueOf(stayExtDao.getBusinessGt10StayCountByDate(format1.parse(startDate), format1.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("all", Integer.valueOf(stayDao.getBusinessIndoorCountByDate(format1.parse(startDate), format1.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("com_lt10", Integer.valueOf(stayExtDao.getBusinessLess10StayCountByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("com_gt10", Integer.valueOf(stayExtDao.getBusinessGt10StayCountByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)businessList.get(0)).getbId().intValue())));
                            data.put("com_all", Integer.valueOf(stayDao.getBusinessIndoorCountByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)businessList.get(0)).getbId().intValue())));
                            datas.add(data);
                        }
                    }

                    ComparatorJSON comparator = new ComparatorJSON();
                    Collections.sort(datas, comparator);
                    obj.put("code", Integer.valueOf(0));
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

    public String getBusinessResidenceNoCompareBar(String startDate, String endDate, String groupId, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List types = businessDao.getAllBusinessTypeByGroupId(mallDao.getMallById(Integer.parseInt(mallId)).getGroupId().intValue());
                if(types != null && types.size() > 0)
                {
                    List names = new ArrayList();
                    List datas = new ArrayList();
                    for(Iterator iterator = types.iterator(); iterator.hasNext();)
                    {
                        BusinessType type = (BusinessType)iterator.next();
                        List businessList = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), type.getbType().intValue());
                        if(businessList != null && businessList.size() > 0)
                        {
                            names.add(type.getbName());
                            datas.add(Integer.valueOf(stayDao.getBusinessAvgResidenceTimeByDate(format.parse(startDate), format.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                        }
                    }

                    obj.put("code", Integer.valueOf(0));
                    obj.put("names", names);
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

    public String getBusinessResidenceCompareBar(String startDate, String endDate, String groupId, String compareStartDate, String mallId)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(mallId))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List types = businessDao.getAllBusinessTypeByGroupId(mallDao.getMallById(Integer.parseInt(mallId)).getGroupId().intValue());
                if(types != null && types.size() > 0)
                {
                    List names = new ArrayList();
                    List datas = new ArrayList();
                    List compareDatas = new ArrayList();
                    for(Iterator iterator = types.iterator(); iterator.hasNext();)
                    {
                        BusinessType type = (BusinessType)iterator.next();
                        List businessList = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), type.getbType().intValue());
                        if(businessList != null && businessList.size() > 0)
                        {
                            names.add(type.getbName());
                            datas.add(Integer.valueOf(stayDao.getBusinessAvgResidenceTimeByDate(format1.parse(startDate), format1.parse(endDate), ((Business)businessList.get(0)).getbId().intValue())));
                            compareDatas.add(Integer.valueOf(stayDao.getBusinessAvgResidenceTimeByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)businessList.get(0)).getbId().intValue())));
                        }
                    }

                    obj.put("code", Integer.valueOf(0));
                    obj.put("names", names);
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

    public String getNoComparedIndoorTrafficChart(String startDate, String endDate, String mallId, String businessType)
    {
        JSONObject obj = new JSONObject();
        List results = new ArrayList();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                if(storeys != null && storeys.size() > 0)
                {
                    for(Iterator iterator = storeys.iterator(); iterator.hasNext();)
                    {
                        Storey storey = (Storey)iterator.next();
                        JSONObject storeyData = new JSONObject();
                        List datas = new ArrayList();
                        List businessList = businessDao.getBusinessByGidandType(storey.getGroupId().intValue(), Integer.parseInt(businessType));
                        if(businessList != null && businessList.size() > 0)
                        {
                            datas = stayDao.getBusinessIndoorCountListByDate(format.parse(startDate), format.parse(endDate), ((Business)businessList.get(0)).getbId().intValue());
                            storeyData.put("name", (new StringBuilder(String.valueOf(storey.getName()))).append(((Business)businessList.get(0)).getbName()).toString());
                            storeyData.put("data", datas);
                            results.add(storeyData);
                        }
                    }

                    obj.put("code", Integer.valueOf(0));
                    obj.put("dates", dates);
                    obj.put("results", results);
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

    public String getComparedIndoorTrafficChart(String startDate, String endDate, String mallId, String businessType, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        List results = new ArrayList();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                if(storeys != null && storeys.size() > 0)
                {
                    for(Iterator iterator = storeys.iterator(); iterator.hasNext();)
                    {
                        Storey storey = (Storey)iterator.next();
                        JSONObject storeyData = new JSONObject();
                        JSONObject com_storeyData = new JSONObject();
                        List datas = new ArrayList();
                        List compareDatas = new ArrayList();
                        List businessList = businessDao.getBusinessByGidandType(storey.getGroupId().intValue(), Integer.parseInt(businessType));
                        if(businessList != null && businessList.size() > 0)
                        {
                            datas = stayDao.getBusinessIndoorCountListByDate(format1.parse(startDate), format1.parse(endDate), ((Business)businessList.get(0)).getbId().intValue());
                            compareDatas = stayDao.getBusinessIndoorCountListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)businessList.get(0)).getbId().intValue());
                            storeyData.put("name", (new StringBuilder(String.valueOf(storey.getName()))).append(((Business)businessList.get(0)).getbName()).toString());
                            storeyData.put("data", datas);
                            storeyData.put("stack", Integer.valueOf(2));
                            com_storeyData.put("name", (new StringBuilder("\u5BF9\u6BD4\u65F6\u6BB5")).append(storey.getName()).append(((Business)businessList.get(0)).getbName()).toString());
                            com_storeyData.put("data", compareDatas);
                            com_storeyData.put("stack", Integer.valueOf(1));
                            results.add(storeyData);
                            results.add(com_storeyData);
                        }
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
        return obj.toString();
    }

    public String getIndoorStatistics(String startDateStr, String endDateStr, String groupId, String businessType)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDateStr = (new StringBuilder(String.valueOf(startDateStr))).append(" 00:00:00").toString();
                endDateStr = (new StringBuilder(String.valueOf(endDateStr))).append(" 23:59:59").toString();
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);
                int this_count = 0;
                int oneday_ago_count = 0;
                int week_ago_count = 0;
                int month_ago_count = 0;
                List list = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(list != null && list.size() > 0)
                {
                    this_count = stayDao.getBusinessIndoorCountByDate(startDate, endDate, ((Business)list.get(0)).getbId().intValue());
                    oneday_ago_count = stayDao.getBusinessIndoorCountByDate(DateUtils.addDays(startDate, -1), DateUtils.addDays(endDate, -1), ((Business)list.get(0)).getbId().intValue());
                    week_ago_count = stayDao.getBusinessIndoorCountByDate(DateUtils.addDays(startDate, -7), DateUtils.addDays(endDate, -7), ((Business)list.get(0)).getbId().intValue());
                    month_ago_count = stayDao.getBusinessIndoorCountByDate(DateUtils.addDays(startDate, -30), DateUtils.addDays(endDate, -30), ((Business)list.get(0)).getbId().intValue());
                }
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

    public String getNoComparedBusinessStayCountChart(String startDate, String endDate, String mallId, String businessType)
    {
        JSONObject obj = new JSONObject();
        List results = new ArrayList();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                if(storeys != null && storeys.size() > 0)
                {
                    for(Iterator iterator = storeys.iterator(); iterator.hasNext();)
                    {
                        Storey storey = (Storey)iterator.next();
                        List blist = businessDao.getBusinessByGidandType(storey.getGroupId().intValue(), Integer.parseInt(businessType));
                        if(blist != null && blist.size() > 0)
                        {
                            List datas = extDao.getBusinessGt10StayListByDate(format.parse(startDate), format.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                            JSONObject storeyData = new JSONObject();
                            storeyData.put("name", storey.getName());
                            storeyData.put("data", datas);
                            results.add(storeyData);
                        }
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
        return obj.toString();
    }

    public String getComparedBusinessStayCountChart(String startDate, String endDate, String groupId, String businessType, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl extDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    List datas = extDao.getBusinessGt10StayListByDate(format1.parse(startDate), format1.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                    List compareDatas = extDao.getBusinessGt10StayListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)blist.get(0)).getbId().intValue());
                    obj.put("code", Integer.valueOf(0));
                    obj.put("dates", dates);
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

    public String getNoComparedBusinessStayConversionChart(String startDate, String endDate, String mallId, String businessType)
    {
        JSONObject obj = new JSONObject();
        List results = new ArrayList();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                if(storeys != null && storeys.size() > 0)
                {
                    for(Iterator iterator = storeys.iterator(); iterator.hasNext();)
                    {
                        Storey storey = (Storey)iterator.next();
                        List blist = businessDao.getBusinessByGidandType(storey.getGroupId().intValue(), Integer.parseInt(businessType));
                        if(blist != null && blist.size() > 0)
                        {
                            List datas = stayDao.getBusinessStayConversionListByDate(format.parse(startDate), format.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                            JSONObject storeyData = new JSONObject();
                            storeyData.put("name", storey.getName());
                            storeyData.put("data", datas);
                            results.add(storeyData);
                        }
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
        return obj.toString();
    }

    public String getComparedBusinessConversionCountChart(String startDate, String endDate, String groupId, String businessType, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    List datas = stayDao.getBusinessStayConversionListByDate(format1.parse(startDate), format1.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                    List compareDatas = stayDao.getBusinessStayConversionListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)blist.get(0)).getbId().intValue());
                    obj.put("code", Integer.valueOf(0));
                    obj.put("dates", dates);
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

    public String getStayStatistics(String startDateStr, String endDateStr, String groupId, String businessType)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDateStr = (new StringBuilder(String.valueOf(startDateStr))).append(" 00:00:00").toString();
                endDateStr = (new StringBuilder(String.valueOf(endDateStr))).append(" 23:59:59").toString();
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);
                int this_count = 0;
                int this_conversion = 0;
                int oneday_ago_conversion = 0;
                int week_ago_conversion = 0;
                int month_ago_conversion = 0;
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    this_count = stayDao.getBusinessStayCountByDate(startDate, endDate, ((Business)blist.get(0)).getbId().intValue());
                    this_conversion = stayDao.getBusinessStayConversionByDate(startDate, endDate, ((Business)blist.get(0)).getbId().intValue());
                    oneday_ago_conversion = stayDao.getBusinessStayConversionByDate(DateUtils.addDays(startDate, -1), DateUtils.addDays(endDate, -1), ((Business)blist.get(0)).getbId().intValue());
                    week_ago_conversion = stayDao.getBusinessStayConversionByDate(DateUtils.addDays(startDate, -7), DateUtils.addDays(endDate, -7), ((Business)blist.get(0)).getbId().intValue());
                    month_ago_conversion = stayDao.getBusinessStayConversionByDate(DateUtils.addDays(startDate, -30), DateUtils.addDays(endDate, -30), ((Business)blist.get(0)).getbId().intValue());
                }
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

    public String getNoComparedResideceDistribute(String startDateStr, String endDateStr, String groupId, String businessType)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDateStr) && StringUtils.isNotEmpty(endDateStr) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                startDateStr = (new StringBuilder(String.valueOf(startDateStr))).append(" 00:00:00").toString();
                endDateStr = (new StringBuilder(String.valueOf(endDateStr))).append(" 23:59:59").toString();
                Date startDate = format.parse(startDateStr);
                Date endDate = format.parse(endDateStr);
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    int less_10 = 0;
                    int range_10_30 = 0;
                    int range_30_60 = 0;
                    int range_60_120 = 0;
                    int more_120 = 0;
                    List exts = stayExtDao.getBusinessAnalyseStayExtDayRecords(startDate, endDate, ((Business)blist.get(0)).getbId().intValue());
                    if(exts != null && exts.size() > 0)
                    {
                        for(Iterator iterator = exts.iterator(); iterator.hasNext();)
                        {
                            AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                            less_10 += ext.getLt10().intValue();
                            range_10_30 += ext.getRange1030().intValue();
                            range_30_60 += ext.getRange3060().intValue();
                            range_60_120 += ext.getRange60120().intValue();
                            more_120 += ext.getGt120().intValue();
                        }

                    }
                    obj.put("code", Integer.valueOf(0));
                    obj.put("less_10", Integer.valueOf(less_10));
                    obj.put("range_10_30", Integer.valueOf(range_10_30));
                    obj.put("range_30_60", Integer.valueOf(range_30_60));
                    obj.put("range_60_120", Integer.valueOf(range_60_120));
                    obj.put("more_120", Integer.valueOf(more_120));
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

    public String getComparedResideceDistribute(String startDate, String endDate, String compareStartDate, String groupId, String businessType)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayExtDaoImpl stayExtDao = (AnalyseStayExtDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayExtDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format2.format(DateUtils.addDays(format2.parse(compareStartDate), dates.size() - 1));
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                compareStartDate = (new StringBuilder(String.valueOf(compareStartDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                compareEndDate = (new StringBuilder(String.valueOf(compareEndDate))).append(" 23:59:59").toString();
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    int less_10 = 0;
                    int range_10_30 = 0;
                    int range_30_60 = 0;
                    int range_60_120 = 0;
                    int more_120 = 0;
                    int less_10_com = 0;
                    int range_10_30_com = 0;
                    int range_30_60_com = 0;
                    int range_60_120_com = 0;
                    int more_120_com = 0;
                    List exts = stayExtDao.getBusinessAnalyseStayExtDayRecords(format1.parse(startDate), format1.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                    List com_exts = stayExtDao.getBusinessAnalyseStayExtDayRecords(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)blist.get(0)).getbId().intValue());
                    if(exts != null && exts.size() > 0)
                    {
                        for(Iterator iterator = exts.iterator(); iterator.hasNext();)
                        {
                            AnalyseStayExt ext = (AnalyseStayExt)iterator.next();
                            less_10 += ext.getLt10().intValue();
                            range_10_30 += ext.getRange1030().intValue();
                            range_30_60 += ext.getRange3060().intValue();
                            range_60_120 += ext.getRange60120().intValue();
                            more_120 += ext.getGt120().intValue();
                        }

                    }
                    if(com_exts != null && com_exts.size() > 0)
                    {
                        for(Iterator iterator1 = com_exts.iterator(); iterator1.hasNext();)
                        {
                            AnalyseStayExt com_ext = (AnalyseStayExt)iterator1.next();
                            less_10_com += com_ext.getLt10().intValue();
                            range_10_30_com += com_ext.getRange1030().intValue();
                            range_30_60_com += com_ext.getRange3060().intValue();
                            range_60_120_com += com_ext.getRange60120().intValue();
                            more_120_com += com_ext.getGt120().intValue();
                        }

                    }
                    obj.put("code", Integer.valueOf(0));
                    obj.put("less_10", Integer.valueOf(less_10));
                    obj.put("range_10_30", Integer.valueOf(range_10_30));
                    obj.put("range_30_60", Integer.valueOf(range_30_60));
                    obj.put("range_60_120", Integer.valueOf(range_60_120));
                    obj.put("more_120", Integer.valueOf(more_120));
                    obj.put("less_10_com", Integer.valueOf(less_10_com));
                    obj.put("range_10_30_com", Integer.valueOf(range_10_30_com));
                    obj.put("range_30_60_com", Integer.valueOf(range_30_60_com));
                    obj.put("range_60_120_com", Integer.valueOf(range_60_120_com));
                    obj.put("more_120_com", Integer.valueOf(more_120_com));
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

    public String getNoCompareResidenceTimeChart(String startDate, String endDate, String groupId, String businessType)
    {
        log.info("business/residence/nocompare/chart params : startDate : {},endDate : {},groupId : {},businessType : {}", new Object[] {
            startDate, endDate, groupId, businessType
        });
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(businessType))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List dates = NaviaDateUtil.getDateList(startDate, endDate);
                startDate = (new StringBuilder(String.valueOf(startDate))).append(" 00:00:00").toString();
                endDate = (new StringBuilder(String.valueOf(endDate))).append(" 23:59:59").toString();
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    List datas = stayDao.getBusinessResidenceTimeListByDate(format.parse(startDate), format.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                    obj.put("code", Integer.valueOf(0));
                    obj.put("dates", dates);
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
        log.info("business/residence/nocompare/chart results : {}", obj.toString());
        return obj.toString();
    }

    public String getCompareResidenceTimeChart(String startDate, String endDate, String groupId, String compareStartDate, String businessType)
    {
        JSONObject obj = new JSONObject();
        AnalyseStayDaoImpl stayDao = (AnalyseStayDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AnalyseStayDaoImpl);
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
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
                List blist = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                if(blist != null && blist.size() > 0)
                {
                    List datas = stayDao.getBusinessResidenceTimeListByDate(format1.parse(startDate), format1.parse(endDate), ((Business)blist.get(0)).getbId().intValue());
                    List compareDatas = stayDao.getBusinessResidenceTimeListByDate(format1.parse(compareStartDate), format1.parse(compareEndDate), ((Business)blist.get(0)).getbId().intValue());
                    obj.put("code", Integer.valueOf(0));
                    obj.put("dates", dates);
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
}
