// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapAction.java

package cn.com.navia.ui.wink.action;

import cn.com.navia.ui.mybatis.model.*;
import cn.com.navia.ui.service.*;
import cn.com.navia.ui.wink.base.BaseAction;
import java.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

public class MapAction extends BaseAction
{

    public MapAction()
    {
    }

    public String getPoisByStoreyId(String storeyId)
    {
        JSONObject obj = new JSONObject();
        JSONObject result = new JSONObject();
        List pois = new ArrayList();
        AreaDaoImpl areaDao = (AreaDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaDaoImpl);
        MapDaoImpl mapDao = (MapDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MapDaoImpl);
        AreaPointDaoImpl pointDao = (AreaPointDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaPointDaoImpl);
        AreaCPointDaoImpl cPointDao = (AreaCPointDaoImpl)getCtx().getBean(cn/com/navia/ui/service/AreaCPointDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(storeyId))
            {
                Map map = mapDao.getMapByStoreyId(Integer.parseInt(storeyId));
                List areas = areaDao.getAreasByMapId(map.getId().intValue());
                if(areas != null && areas.size() > 0)
                {
                    JSONObject areaJson;
                    for(Iterator iterator = areas.iterator(); iterator.hasNext(); pois.add(areaJson))
                    {
                        Area area = (Area)iterator.next();
                        areaJson = new JSONObject();
                        areaJson.put("name", area.getName());
                        List points = pointDao.getPointsByAreaId(area.getId().intValue());
                        List pointsList = new ArrayList();
                        if(points != null && points.size() > 0)
                        {
                            JSONObject pointJson;
                            for(Iterator iterator1 = points.iterator(); iterator1.hasNext(); pointsList.add(pointJson))
                            {
                                AreaPoint point = (AreaPoint)iterator1.next();
                                pointJson = new JSONObject();
                                pointJson.put("lon", point.getLon());
                                pointJson.put("lat", point.getLat());
                            }

                        }
                        areaJson.put("polygon", pointsList);
                        List cpoints = cPointDao.getCPointByAreaId(area.getId().intValue());
                        if(cpoints != null && cpoints.size() > 0)
                        {
                            areaJson.put("clon", ((AreaCPoint)cpoints.get(0)).getLon());
                            areaJson.put("clat", ((AreaCPoint)cpoints.get(0)).getLat());
                        }
                    }

                    result.put("poi_list", pois);
                }
                obj.put("code", Integer.valueOf(0));
                obj.put("storeyid", storeyId);
                obj.put("result", result);
            } else
            {
                obj.put("code", Integer.valueOf(-1));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("code", Integer.valueOf(-9));
        }
        return obj.toString();
    }

    public String getStoreyConfigs(String mallId)
    {
        JSONObject obj = new JSONObject();
        JSONObject result = new JSONObject();
        StoreyDaoImpl storeyDao = (StoreyDaoImpl)getCtx().getBean(cn/com/navia/ui/service/StoreyDaoImpl);
        MallDaoImpl mallDao = (MallDaoImpl)getCtx().getBean(cn/com/navia/ui/service/MallDaoImpl);
        try
        {
            if(StringUtils.isNotEmpty(mallId))
            {
                List storeys = storeyDao.getStoreysByMallId(Integer.parseInt(mallId));
                Mall mall = mallDao.getMallById(Integer.parseInt(mallId));
                if(storeys != null && storeys.size() > 0)
                {
                    Storey s;
                    JSONObject conf;
                    for(Iterator iterator = storeys.iterator(); iterator.hasNext(); result.put(s.getFloorId(), conf))
                    {
                        s = (Storey)iterator.next();
                        conf = new JSONObject();
                        conf.put("dataName", (new StringBuilder(String.valueOf(s.getFloorId()))).append("_DATA").toString());
                        conf.put("srcPrefix", (new StringBuilder("../")).append(s.getFloorId().split("_")[0]).append("/data").toString());
                        conf.put("dirName", s.getMdbId());
                        conf.put("serFloor", s.getMdbMapname());
                    }

                    obj.put("code", Integer.valueOf(0));
                    obj.put("result", result);
                    obj.put("cplat", mall.getCpLat());
                    obj.put("cplon", mall.getCpLon());
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
        }
        return obj.toString();
    }
}
