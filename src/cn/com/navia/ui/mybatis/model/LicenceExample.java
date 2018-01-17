// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LicenceExample.java

package cn.com.navia.ui.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class LicenceExample
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

        public volatile Criteria andCodeIsNotNull()
        {
            return super.andCodeIsNotNull();
        }

        public volatile List getAllCriteria()
        {
            return super.getAllCriteria();
        }

        public volatile Criteria andCodeIn(List list)
        {
            return super.andCodeIn(list);
        }

        public volatile Criteria andIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andIdNotBetween(integer, integer1);
        }

        public volatile Criteria andCodeLike(String s)
        {
            return super.andCodeLike(s);
        }

        public volatile Criteria andCodeBetween(String s, String s1)
        {
            return super.andCodeBetween(s, s1);
        }

        public volatile Criteria andCodeNotLike(String s)
        {
            return super.andCodeNotLike(s);
        }

        public volatile boolean isValid()
        {
            return super.isValid();
        }

        public volatile Criteria andIdBetween(Integer integer, Integer integer1)
        {
            return super.andIdBetween(integer, integer1);
        }

        public volatile Criteria andCodeNotIn(List list)
        {
            return super.andCodeNotIn(list);
        }

        public volatile Criteria andIdIsNotNull()
        {
            return super.andIdIsNotNull();
        }

        public volatile Criteria andCodeIsNull()
        {
            return super.andCodeIsNull();
        }

        public volatile Criteria andIdIsNull()
        {
            return super.andIdIsNull();
        }

        public volatile Criteria andCodeNotBetween(String s, String s1)
        {
            return super.andCodeNotBetween(s, s1);
        }

        public volatile Criteria andIdNotEqualTo(Integer integer)
        {
            return super.andIdNotEqualTo(integer);
        }

        public volatile Criteria andCodeLessThanOrEqualTo(String s)
        {
            return super.andCodeLessThanOrEqualTo(s);
        }

        public volatile Criteria andIdGreaterThan(Integer integer)
        {
            return super.andIdGreaterThan(integer);
        }

        public volatile Criteria andIdLessThanOrEqualTo(Integer integer)
        {
            return super.andIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andCodeEqualTo(String s)
        {
            return super.andCodeEqualTo(s);
        }

        public volatile Criteria andCodeGreaterThan(String s)
        {
            return super.andCodeGreaterThan(s);
        }

        public volatile Criteria andCodeLessThan(String s)
        {
            return super.andCodeLessThan(s);
        }

        public volatile Criteria andIdNotIn(List list)
        {
            return super.andIdNotIn(list);
        }

        public volatile List getCriteria()
        {
            return super.getCriteria();
        }

        public volatile Criteria andCodeGreaterThanOrEqualTo(String s)
        {
            return super.andCodeGreaterThanOrEqualTo(s);
        }

        public volatile Criteria andIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andCodeNotEqualTo(String s)
        {
            return super.andCodeNotEqualTo(s);
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

        public Criteria andCodeIsNull()
        {
            addCriterion("code is null");
            return (Criteria)this;
        }

        public Criteria andCodeIsNotNull()
        {
            addCriterion("code is not null");
            return (Criteria)this;
        }

        public Criteria andCodeEqualTo(String value)
        {
            addCriterion("code =", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotEqualTo(String value)
        {
            addCriterion("code <>", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeGreaterThan(String value)
        {
            addCriterion("code >", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value)
        {
            addCriterion("code >=", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeLessThan(String value)
        {
            addCriterion("code <", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value)
        {
            addCriterion("code <=", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeLike(String value)
        {
            addCriterion("code like", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotLike(String value)
        {
            addCriterion("code not like", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeIn(List values)
        {
            addCriterion("code in", values, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotIn(List values)
        {
            addCriterion("code not in", values, "code");
            return (Criteria)this;
        }

        public Criteria andCodeBetween(String value1, String value2)
        {
            addCriterion("code between", value1, value2, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotBetween(String value1, String value2)
        {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria)this;
        }

        protected List criteria;

        protected GeneratedCriteria()
        {
            criteria = new ArrayList();
        }
    }


    public LicenceExample()
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
