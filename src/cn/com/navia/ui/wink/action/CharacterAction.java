// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharacterAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.Business;
import cn.com.navia.ui.service.BusinessDaoImpl;
import cn.com.navia.ui.utils.NaviaDateUtil;
import cn.com.navia.ui.wink.base.BaseAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.ApplicationContext;

public class CharacterAction extends BaseAction
{

    public CharacterAction()
    {
    }

    public String getNoCompareChart(String startDate, String endDate, String dateTab, String groupId, String businessType)
    {
        JSONObject obj = new JSONObject();
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        List dates = new ArrayList();
        int requestId = 0;
        try
        {
            if(StringUtils.isNotEmpty(businessType) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(dateTab))
            {
                if(Integer.parseInt(dateTab) == 0)
                    dates = NaviaDateUtil.getDateList(startDate, endDate);
                else
                    dates = NaviaDateUtil.getCharacterDatesByType(startDate, endDate, Integer.parseInt(dateTab));
                if(Integer.parseInt(businessType) == 0)
                {
                    requestId = Integer.parseInt(groupId);
                } else
                {
                    List bList = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                    if(bList != null && bList.size() > 0)
                        requestId = ((Business)bList.get(0)).getbId().intValue();
                }
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("requestId", Integer.valueOf(requestId));
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

    public String getCompareChart(String startDate, String endDate, String dateTab, String groupId, String businessType, String compareStartDate)
    {
        JSONObject obj = new JSONObject();
        BusinessDaoImpl businessDao = (BusinessDaoImpl)getCtx().getBean(cn/com/navia/ui/service/BusinessDaoImpl);
        List dates = new ArrayList();
        List compareDates = new ArrayList();
        int requestId = 0;
        try
        {
            if(StringUtils.isNotEmpty(businessType) && StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate) && StringUtils.isNotEmpty(groupId) && StringUtils.isNotEmpty(dateTab) && StringUtils.isNotEmpty(compareStartDate))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                dates = NaviaDateUtil.getDateList(startDate, endDate);
                String compareEndDate = format.format(DateUtils.addDays(format.parse(compareStartDate), dates.size() - 1));
                compareDates = NaviaDateUtil.getDateList(compareStartDate, compareEndDate);
                if(Integer.parseInt(dateTab) != 0)
                {
                    dates = NaviaDateUtil.getCharacterDatesByType(startDate, endDate, Integer.parseInt(dateTab));
                    compareDates = NaviaDateUtil.getCharacterDatesByType(compareStartDate, compareEndDate, Integer.parseInt(dateTab));
                }
                if(Integer.parseInt(businessType) == 0)
                {
                    requestId = Integer.parseInt(groupId);
                } else
                {
                    List bList = businessDao.getBusinessByGidandType(Integer.parseInt(groupId), Integer.parseInt(businessType));
                    if(bList != null && bList.size() > 0)
                        requestId = ((Business)bList.get(0)).getbId().intValue();
                }
                obj.put("code", Integer.valueOf(0));
                obj.put("dates", dates);
                obj.put("comapreDates", compareDates);
                obj.put("requestId", Integer.valueOf(requestId));
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
