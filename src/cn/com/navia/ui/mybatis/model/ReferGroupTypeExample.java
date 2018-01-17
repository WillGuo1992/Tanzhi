// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferGroupTypeExample.java

package cn.com.navia.ui.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class ReferGroupTypeExample
{
    public static class Criteria extends GeneratedCriteria
    {

        public volatile Criteria andIdLessThan(Integer integer)
        {
            return super.andIdLessThan(integer);
        }

        public volatile Criteria andIdIn(List list)
        {
            return super.andIdIn(list);
        }

        public volatile Criteria andNameIn(List list)
        {
            return super.andNameIn(list);
        }

        public volatile Criteria andNameLike(String s)
        {
            return super.andNameLike(s);
        }

        public volatile Criteria andNameIsNotNull()
        {
            return super.andNameIsNotNull();
        }

        public volatile List getAllCriteria()
        {
            return super.getAllCriteria();
        }

        public volatile Criteria andGTypeNotIn(List list)
        {
            return super.andGTypeNotIn(list);
        }

        public volatile Criteria andIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andIdNotBetween(integer, integer1);
        }

        public volatile Criteria andGTypeIn(List list)
        {
            return super.andGTypeIn(list);
        }

        public volatile Criteria andGTypeLessThan(Integer integer)
        {
            return super.andGTypeLessThan(integer);
        }

        public volatile Criteria andNameBetween(String s, String s1)
        {
            return super.andNameBetween(s, s1);
        }

        public volatile Criteria andNameNotLike(String s)
        {
            return super.andNameNotLike(s);
        }

        public volatile Criteria andGTypeEqualTo(Integer integer)
        {
            return super.andGTypeEqualTo(integer);
        }

        public volatile Criteria andGTypeGreaterThan(Integer integer)
        {
            return super.andGTypeGreaterThan(integer);
        }

        public volatile boolean isValid()
        {
            return super.isValid();
        }

        public volatile Criteria andIdBetween(Integer integer, Integer integer1)
        {
            return super.andIdBetween(integer, integer1);
        }

        public volatile Criteria andGTypeNotBetween(Integer integer, Integer integer1)
        {
            return super.andGTypeNotBetween(integer, integer1);
        }

        public volatile Criteria andNameIsNull()
        {
            return super.andNameIsNull();
        }

        public volatile Criteria andNameNotBetween(String s, String s1)
        {
            return super.andNameNotBetween(s, s1);
        }

        public volatile Criteria andNameNotIn(List list)
        {
            return super.andNameNotIn(list);
        }

        public volatile Criteria andIdIsNotNull()
        {
            return super.andIdIsNotNull();
        }

        public volatile Criteria andNameGreaterThan(String s)
        {
            return super.andNameGreaterThan(s);
        }

        public volatile Criteria andIdIsNull()
        {
            return super.andIdIsNull();
        }

        public volatile Criteria andIdNotEqualTo(Integer integer)
        {
            return super.andIdNotEqualTo(integer);
        }

        public volatile Criteria andGTypeIsNotNull()
        {
            return super.andGTypeIsNotNull();
        }

        public volatile Criteria andGTypeLessThanOrEqualTo(Integer integer)
        {
            return super.andGTypeLessThanOrEqualTo(integer);
        }

        public volatile Criteria andNameLessThan(String s)
        {
            return super.andNameLessThan(s);
        }

        public volatile Criteria andIdGreaterThan(Integer integer)
        {
            return super.andIdGreaterThan(integer);
        }

        public volatile Criteria andIdLessThanOrEqualTo(Integer integer)
        {
            return super.andIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andNameLessThanOrEqualTo(String s)
        {
            return super.andNameLessThanOrEqualTo(s);
        }

        public volatile Criteria andGTypeIsNull()
        {
            return super.andGTypeIsNull();
        }

        public volatile Criteria andGTypeNotEqualTo(Integer integer)
        {
            return super.andGTypeNotEqualTo(integer);
        }

        public volatile Criteria andNameEqualTo(String s)
        {
            return super.andNameEqualTo(s);
        }

        public volatile Criteria andNameNotEqualTo(String s)
        {
            return super.andNameNotEqualTo(s);
        }

        public volatile Criteria andIdNotIn(List list)
        {
            return super.andIdNotIn(list);
        }

        public volatile List getCriteria()
        {
            return super.getCriteria();
        }

        public volatile Criteria andGTypeGreaterThanOrEqualTo(Integer integer)
        {
            return super.andGTypeGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andNameGreaterThanOrEqualTo(String s)
        {
            return super.andNameGreaterThanOrEqualTo(s);
        }

        public volatile Criteria andGTypeBetween(Integer integer, Integer integer1)
        {
            return super.andGTypeBetween(integer, integer1);
        }

        public volatile Criteria andIdEqualTo(Integer integer)
        {
            return super.andIdEqualTo(integer);
        }

        protected Criteria()
        {
        }
    }

    public static class Criterion
    {

        public String getCondition()
        {
            return condition;
        }

        public Object getValue()
        {
            return value;
        }

        public Object getSecondValue()
        {
            return secondValue;
        }

        public boolean isNoValue()
        {
            return noValue;
        }

        public boolean isSingleValue()
        {
            return singleValue;
        }

        public boolean isBetweenValue()
        {
            return betweenValue;
        }

        public boolean isListValue()
        {
            return listValue;
        }

        public String getTypeHandler()
        {
            return typeHandler;
        }

        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;

        protected Criterion(String condition)
        {
            this.condition = condition;
            typeHandler = null;
            noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler)
        {
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if(value instanceof List)
                listValue = true;
            else
                singleValue = true;
        }

        protected Criterion(String condition, Object value)
        {
            this(condition, value, ((String) (null)));
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler)
        {
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue)
        {
            this(condition, value, secondValue, null);
        }
    }

    protected static abstract class GeneratedCriteria
    {

        public boolean isValid()
        {
            return criteria.size() > 0;
        }

        public List getAllCriteria()
        {
            return criteria;
        }

        public List getCriteria()
        {
            return criteria;
        }

        protected void addCriterion(String condition)
        {
            if(condition == null)
            {
                throw new RuntimeException("Value for condition cannot be null");
            } else
            {
                criteria.add(new Criterion(condition));
                return;
            }
        }

        protected void addCriterion(String condition, Object value, String property)
        {
            if(value == null)
            {
                throw new RuntimeException((new StringBuilder("Value for ")).append(property).append(" cannot be null").toString());
            } else
            {
                criteria.add(new Criterion(condition, value));
                return;
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property)
        {
            if(value1 == null || value2 == null)
            {
                throw new RuntimeException((new StringBuilder("Between values for ")).append(property).append(" cannot be null").toString());
            } else
            {
                criteria.add(new Criterion(condition, value1, value2));
                return;
            }
        }

        public Criteria andIdIsNull()
        {
            addCriterion("id is null");
            return (Criteria)this;
        }

        public Criteria andIdIsNotNull()
        {
            addCriterion("id is not null");
            return (Criteria)this;
        }

        public Criteria andIdEqualTo(Integer value)
        {
            addCriterion("id =", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotEqualTo(Integer value)
        {
            addCriterion("id <>", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThan(Integer value)
        {
            addCriterion("id >", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("id >=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThan(Integer value)
        {
            addCriterion("id <", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value)
        {
            addCriterion("id <=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdIn(List values)
        {
            addCriterion("id in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotIn(List values)
        {
            addCriterion("id not in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2)
        {
            addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2)
        {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andGTypeIsNull()
        {
            addCriterion("g_type is null");
            return (Criteria)this;
        }

        public Criteria andGTypeIsNotNull()
        {
            addCriterion("g_type is not null");
            return (Criteria)this;
        }

        public Criteria andGTypeEqualTo(Integer value)
        {
            addCriterion("g_type =", value, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeNotEqualTo(Integer value)
        {
            addCriterion("g_type <>", value, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeGreaterThan(Integer value)
        {
            addCriterion("g_type >", value, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("g_type >=", value, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeLessThan(Integer value)
        {
            addCriterion("g_type <", value, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeLessThanOrEqualTo(Integer value)
        {
            addCriterion("g_type <=", value, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeIn(List values)
        {
            addCriterion("g_type in", values, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeNotIn(List values)
        {
            addCriterion("g_type not in", values, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeBetween(Integer value1, Integer value2)
        {
            addCriterion("g_type between", value1, value2, "gType");
            return (Criteria)this;
        }

        public Criteria andGTypeNotBetween(Integer value1, Integer value2)
        {
            addCriterion("g_type not between", value1, value2, "gType");
            return (Criteria)this;
        }

        public Criteria andNameIsNull()
        {
            addCriterion("name is null");
            return (Criteria)this;
        }

        public Criteria andNameIsNotNull()
        {
            addCriterion("name is not null");
            return (Criteria)this;
        }

        public Criteria andNameEqualTo(String value)
        {
            addCriterion("name =", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotEqualTo(String value)
        {
            addCriterion("name <>", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameGreaterThan(String value)
        {
            addCriterion("name >", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value)
        {
            addCriterion("name >=", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLessThan(String value)
        {
            addCriterion("name <", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLessThanOrEqualTo(String value)
        {
            addCriterion("name <=", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLike(String value)
        {
            addCriterion("name like", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotLike(String value)
        {
            addCriterion("name not like", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameIn(List values)
        {
            addCriterion("name in", values, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotIn(List values)
        {
            addCriterion("name not in", values, "name");
            return (Criteria)this;
        }

        public Criteria andNameBetween(String value1, String value2)
        {
            addCriterion("name between", value1, value2, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotBetween(String value1, String value2)
        {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria)this;
        }

        protected List criteria;

        protected GeneratedCriteria()
        {
            criteria = new ArrayList();
        }
    }


    public ReferGroupTypeExample()
    {
        oredCriteria = new ArrayList();
    }

    public void setOrderByClause(String orderByClause)
    {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause()
    {
        return orderByClause;
    }

    public void setDistinct(boolean distinct)
    {
        this.distinct = distinct;
    }

    public boolean isDistinct()
    {
        return distinct;
    }

    public List getOredCriteria()
    {
        return oredCriteria;
    }

    public void or(Criteria criteria)
    {
        oredCriteria.add(criteria);
    }

    public Criteria or()
    {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria()
    {
        Criteria criteria = createCriteriaInternal();
        if(oredCriteria.size() == 0)
            oredCriteria.add(criteria);
        return criteria;
    }

    protected Criteria createCriteriaInternal()
    {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear()
    {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected String orderByClause;
    protected boolean distinct;
    protected List oredCriteria;
}
