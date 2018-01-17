// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BgRelationExample.java

package cn.com.navia.ui.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class BgRelationExample
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

        public volatile Criteria andBIdEqualTo(Integer integer)
        {
            return super.andBIdEqualTo(integer);
        }

        public volatile Criteria andGIdEqualTo(Integer integer)
        {
            return super.andGIdEqualTo(integer);
        }

        public volatile List getAllCriteria()
        {
            return super.getAllCriteria();
        }

        public volatile Criteria andIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andIdNotBetween(integer, integer1);
        }

        public volatile Criteria andBIdLessThan(Integer integer)
        {
            return super.andBIdLessThan(integer);
        }

        public volatile Criteria andGIdIsNull()
        {
            return super.andGIdIsNull();
        }

        public volatile Criteria andGIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andGIdNotBetween(integer, integer1);
        }

        public volatile Criteria andBIdNotEqualTo(Integer integer)
        {
            return super.andBIdNotEqualTo(integer);
        }

        public volatile boolean isValid()
        {
            return super.isValid();
        }

        public volatile Criteria andIdBetween(Integer integer, Integer integer1)
        {
            return super.andIdBetween(integer, integer1);
        }

        public volatile Criteria andGIdLessThan(Integer integer)
        {
            return super.andGIdLessThan(integer);
        }

        public volatile Criteria andBIdBetween(Integer integer, Integer integer1)
        {
            return super.andBIdBetween(integer, integer1);
        }

        public volatile Criteria andBIdIn(List list)
        {
            return super.andBIdIn(list);
        }

        public volatile Criteria andGIdBetween(Integer integer, Integer integer1)
        {
            return super.andGIdBetween(integer, integer1);
        }

        public volatile Criteria andIdIsNotNull()
        {
            return super.andIdIsNotNull();
        }

        public volatile Criteria andGIdNotEqualTo(Integer integer)
        {
            return super.andGIdNotEqualTo(integer);
        }

        public volatile Criteria andIdIsNull()
        {
            return super.andIdIsNull();
        }

        public volatile Criteria andBIdIsNotNull()
        {
            return super.andBIdIsNotNull();
        }

        public volatile Criteria andIdNotEqualTo(Integer integer)
        {
            return super.andIdNotEqualTo(integer);
        }

        public volatile Criteria andGIdIsNotNull()
        {
            return super.andGIdIsNotNull();
        }

        public volatile Criteria andBIdLessThanOrEqualTo(Integer integer)
        {
            return super.andBIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andGIdLessThanOrEqualTo(Integer integer)
        {
            return super.andGIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andIdGreaterThan(Integer integer)
        {
            return super.andIdGreaterThan(integer);
        }

        public volatile Criteria andIdLessThanOrEqualTo(Integer integer)
        {
            return super.andIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andGIdIn(List list)
        {
            return super.andGIdIn(list);
        }

        public volatile Criteria andBIdGreaterThan(Integer integer)
        {
            return super.andBIdGreaterThan(integer);
        }

        public volatile Criteria andGIdGreaterThan(Integer integer)
        {
            return super.andGIdGreaterThan(integer);
        }

        public volatile Criteria andIdNotIn(List list)
        {
            return super.andIdNotIn(list);
        }

        public volatile List getCriteria()
        {
            return super.getCriteria();
        }

        public volatile Criteria andBIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andBIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andGIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andGIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andBIdIsNull()
        {
            return super.andBIdIsNull();
        }

        public volatile Criteria andBIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andBIdNotBetween(integer, integer1);
        }

        public volatile Criteria andBIdNotIn(List list)
        {
            return super.andBIdNotIn(list);
        }

        public volatile Criteria andGIdNotIn(List list)
        {
            return super.andGIdNotIn(list);
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

        public Criteria andGIdIsNull()
        {
            addCriterion("g_id is null");
            return (Criteria)this;
        }

        public Criteria andGIdIsNotNull()
        {
            addCriterion("g_id is not null");
            return (Criteria)this;
        }

        public Criteria andGIdEqualTo(Integer value)
        {
            addCriterion("g_id =", value, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdNotEqualTo(Integer value)
        {
            addCriterion("g_id <>", value, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdGreaterThan(Integer value)
        {
            addCriterion("g_id >", value, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("g_id >=", value, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdLessThan(Integer value)
        {
            addCriterion("g_id <", value, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdLessThanOrEqualTo(Integer value)
        {
            addCriterion("g_id <=", value, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdIn(List values)
        {
            addCriterion("g_id in", values, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdNotIn(List values)
        {
            addCriterion("g_id not in", values, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdBetween(Integer value1, Integer value2)
        {
            addCriterion("g_id between", value1, value2, "gId");
            return (Criteria)this;
        }

        public Criteria andGIdNotBetween(Integer value1, Integer value2)
        {
            addCriterion("g_id not between", value1, value2, "gId");
            return (Criteria)this;
        }

        public Criteria andBIdIsNull()
        {
            addCriterion("b_id is null");
            return (Criteria)this;
        }

        public Criteria andBIdIsNotNull()
        {
            addCriterion("b_id is not null");
            return (Criteria)this;
        }

        public Criteria andBIdEqualTo(Integer value)
        {
            addCriterion("b_id =", value, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdNotEqualTo(Integer value)
        {
            addCriterion("b_id <>", value, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdGreaterThan(Integer value)
        {
            addCriterion("b_id >", value, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("b_id >=", value, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdLessThan(Integer value)
        {
            addCriterion("b_id <", value, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdLessThanOrEqualTo(Integer value)
        {
            addCriterion("b_id <=", value, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdIn(List values)
        {
            addCriterion("b_id in", values, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdNotIn(List values)
        {
            addCriterion("b_id not in", values, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdBetween(Integer value1, Integer value2)
        {
            addCriterion("b_id between", value1, value2, "bId");
            return (Criteria)this;
        }

        public Criteria andBIdNotBetween(Integer value1, Integer value2)
        {
            addCriterion("b_id not between", value1, value2, "bId");
            return (Criteria)this;
        }

        protected List criteria;

        protected GeneratedCriteria()
        {
            criteria = new ArrayList();
        }
    }


    public BgRelationExample()
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
