// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeviceAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.service.*;
import cn.com.navia.ui.wink.base.BaseAction;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

public class DeviceAction extends BaseAction
{

    public DeviceAction()
    {
    }

    public String getDevices(String mallId)
    {
        JSONObject obj = new JSONObject();
        DeviceDaoImpl deviceDao = (DeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/DeviceDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                List devices = deviceDao.getDevicesByMallId(Integer.parseInt(mallId));
                obj.put("code", Integer.valueOf(0));
                obj.put("devices", devices);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String addDevice(String mallId, String sn, String mac, String description)
    {
        JSONObject obj = new JSONObject();
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        DeviceDaoImpl deviceDao = (DeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/DeviceDaoImpl);
        ReferDeviceDaoImpl referDeviceDao = (ReferDeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ReferDeviceDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId) && StringUtils.isNotEmpty(sn) && StringUtils.isNotEmpty(mac))
            {
                List devices = deviceDao.getDeviceByMac(mac);
                if(devices != null && devices.size() > 0)
                {
                    obj.put("code", Integer.valueOf(-4));
                } else
                {
                    int refer_id = 0;
                    int gid = mallDao.getMallById(Integer.parseInt(mallId)).getGroupId().intValue();
                    ReferDevice rd = new ReferDevice();
                    rd.setgId(Integer.valueOf(gid));
                    rd.setMac(mac);
                    int ri = referDeviceDao.addReferDevice(rd);
                    if(ri != 0)
                    {
                        refer_id = rd.getId().intValue();
                        Device d = new Device();
                        d.setSn(sn);
                        d.setMac(mac);
                        d.setMallId(Integer.valueOf(Integer.parseInt(mallId)));
                        d.setDescription(description);
                        d.setReferId(Integer.valueOf(refer_id));
                        int i = deviceDao.addDevice(d);
                        if(i != 0)
                            obj.put("code", Integer.valueOf(0));
                        else
                            obj.put("code", Integer.valueOf(-9));
                    } else
                    {
                        obj.put("code", Integer.valueOf(-9));
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
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String deviceInfo(String deviceId)
    {
        JSONObject obj = new JSONObject();
        DeviceDaoImpl deviceDao = (DeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/DeviceDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(deviceId))
            {
                Device d = deviceDao.getDeviceById(Integer.parseInt(deviceId));
                obj.put("code", Integer.valueOf(0));
                obj.put("info", d);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String editDevice(String deviceId, String sn, String mac, String description)
    {
        JSONObject obj = new JSONObject();
        DeviceDaoImpl deviceDao = (DeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/DeviceDaoImpl);
        ReferDeviceDaoImpl referDao = (ReferDeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ReferDeviceDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(deviceId) && StringUtils.isNotEmpty(sn) && StringUtils.isNotEmpty(mac))
            {
                Device d = deviceDao.getDeviceById(Integer.parseInt(deviceId));
                if(d != null)
                {
                    d.setSn(sn);
                    d.setMac(mac);
                    d.setDescription(description);
                    int i = deviceDao.updateDevice(d);
                    ReferDevice rd = referDao.getDeviceById(d.getReferId().intValue());
                    rd.setMac(mac);
                    int ri = referDao.updateDevice(rd);
                    if(i != 0 && ri != 0)
                        obj.put("code", Integer.valueOf(0));
                    else
                        obj.put("code", Integer.valueOf(-9));
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
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }

    public String deleteDevice(String deviceId)
    {
        JSONObject obj = new JSONObject();
        DeviceDaoImpl deviceDao = (DeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/DeviceDaoImpl);
        ReferDeviceDaoImpl referDao = (ReferDeviceDaoImpl)getCtx().getBean(cn/com/navia/ui/service/ReferDeviceDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(deviceId))
            {
                Device d = deviceDao.getDeviceById(Integer.parseInt(deviceId));
                if(d != null)
                {
                    int ri = referDao.deleteDevice(d.getReferId().intValue());
                    int i = deviceDao.deleteDevice(d.getId().intValue());
                    if(i != 0 && ri != 0)
                        obj.put("code", Integer.valueOf(0));
                    else
                        obj.put("code", Integer.valueOf(-9));
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
            obj.put("code", Integer.valueOf(-9));
            obj.put("desc", "\u670D\u52A1\u5668\u5F02\u5E38");
        }
        return obj.toString();
    }
}
