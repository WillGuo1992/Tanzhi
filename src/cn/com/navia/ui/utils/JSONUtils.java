// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JSONUtils.java

package cn.com.navia.ui.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.PrintStream;
import org.json.*;

public class JSONUtils
{

    public JSONUtils()
    {
    }

    public static JSONObject toJSONObject(String xml)
        throws JSONException
    {
        return XML.toJSONObject(xml);
    }

    public static ObjectNode toObjectNode(String xml)
        throws JSONException
    {
        return (ObjectNode)fromJsonSafe(com/fasterxml/jackson/databind/node/ObjectNode, toJSONObject(xml).toString());
    }

    public static Object convertValue(Object obj, Class clazz)
    {
        return mapper.convertValue(obj, clazz);
    }

    public static String toJson(Object obj)
    {
        try
        {
            return mapper.writeValueAsString(obj);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJson(Class clazz, String json)
        throws JsonParseException, JsonMappingException, IOException
    {
        Object obj = mapper.readValue(json, clazz);
        return obj;
    }

    public static Object fromJsonSafe(Class clazz, String json)
    {
        Object obj = null;
        try
        {
            obj = fromJson(clazz, json);
        }
        catch(Exception e)
        {
            System.err.println((new StringBuilder("fromJson error:")).append(json).toString());
        }
        return obj;
    }

    public static JSONObject fromJson(String json)
        throws JSONException
    {
        return new JSONObject(json);
    }

    public static ObjectMapper mapper;

    static 
    {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
