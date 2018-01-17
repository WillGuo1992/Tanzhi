// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MallAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.Mall;
import cn.com.navia.ui.mybatis.model.ReferGroup;
import cn.com.navia.ui.service.*;
import cn.com.navia.ui.wink.base.BaseAction;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

public class MallAction extends BaseAction
{

    public MallAction()
    {
    }

    public String saveMallInfo(String mallId, String mallname, String licence, String address, String stime, String etime, String contacts, 
            String telephone)
    {
        JSONObject obj = new JSONObject();
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        GroupDaoImpl groupDao = (GroupDaoImpl)getCtx().getBean(cn/com/navia/ui/service/GroupDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    ReferGroup group = groupDao.getGroupById(mall.getGroupId().intValue());
                    group.setName(mallname);
                    mall.setName(mallname);
                    mall.setLicenceCode(licence);
                    mall.setAddress(address);
                    mall.setOpenTime(stime);
                    mall.setCloseTime(etime);
                    mall.setContactName(contacts);
                    mall.setContactNum(telephone);
                    int i = mallDao.updateMallInfo(mall);
                    int j = groupDao.updateGroup(group);
                    if(i != 0 && j != 0)
                    {
                        HttpSession session = request.getSession();
                        session.setAttribute("mall_name", mallname);
                        Cookie login_name_cookie = new Cookie("mallname", URLEncoder.encode(mallname, "utf-8"));
                        login_name_cookie.setMaxAge(0x9450c00);
                        login_name_cookie.setPath("/");
                        response.addCookie(login_name_cookie);
                        obj.put("code", Integer.valueOf(0));
                    } else
                    {
                        obj.put("code", Integer.valueOf(-9));
                    }
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
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String loadMallInfo(String mallId)
    {
        JSONObject obj = new JSONObject();
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    obj.put("code", Integer.valueOf(0));
                    obj.put("info", mall);
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
            e.printStackTrace();
        }
        return obj.toString();
    }

    public String getEntrysList(String mallId)
    {
        JSONObject obj = new JSONObject();
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(mall != null)
                {
                    List entrys = areaDao.getEntrysByMallId(mall.getId().intValue());
                    if(entrys != null && entrys.size() > 0)
                    {
                        obj.put("code", Integer.valueOf(0));
                        obj.put("info", entrys);
                    } else
                    {
                        obj.put("code", Integer.valueOf(-2));
                    }
                }
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return obj.toString();
    }
}
