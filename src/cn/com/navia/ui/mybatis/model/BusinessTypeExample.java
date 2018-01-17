// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BusinessTypeExample.java

package cn.com.navia.ui.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class BusinessTypeExample
{
    public static class Criteria extends GeneratedCriteria
    {

        public volatile Criteria andIdLessThan(Integer integer)
        {
            return super.andIdLessThan(integer);
        }

        public volatile Criteria andBTypeLessThan(Integer integer)
        {
            return super.andBTypeLessThan(integer);
        }

        public volatile Criteria andIdIn(List list)
        {
            return super.andIdIn(list);
        }

        public volatile Criteria andBNameIn(List list)
        {
            return super.andBNameIn(list);
        }

        public volatile List getAllCriteria()
        {
            return super.getAllCriteria();
        }

        public volatile Criteria andBTypeNotIn(List list)
        {
            return super.andBTypeNotIn(list);
        }

        public volatile Criteria andIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andIdNotBetween(integer, integer1);
        }

        public volatile Criteria andBNameIsNotNull()
        {
            return super.andBNameIsNotNull();
        }

        public volatile Criteria andBNameLessThan(String s)
        {
            return super.andBNameLessThan(s);
        }

        public volatile Criteria andBNameNotEqualTo(String s)
        {
            return super.andBNameNotEqualTo(s);
        }

        public volatile Criteria andBNameLike(String s)
        {
            return super.andBNameLike(s);
        }

        public volatile Criteria andBNameBetween(String s, String s1)
        {
            return super.andBNameBetween(s, s1);
        }

        public volatile Criteria andBTypeEqualTo(Integer integer)
        {
            return super.andBTypeEqualTo(integer);
        }

        public volatile boolean isValid()
        {
            return super.isValid();
        }

        public volatile Criteria andIdBetween(Integer integer, Integer integer1)
        {
            return super.andIdBetween(integer, integer1);
        }

        public volatile Criteria andBTypeGreaterThan(Integer integer)
        {
            return super.andBTypeGreaterThan(integer);
        }

        public volatile Criteria andBNameNotLike(String s)
        {
            return super.andBNameNotLike(s);
        }

        public volatile Criteria andBTypeNotBetween(Integer integer, Integer integer1)
        {
            return super.andBTypeNotBetween(integer, integer1);
        }

        public volatile Criteria andBTypeIsNull()
        {
            return super.andBTypeIsNull();
        }

        public volatile Criteria andIdIsNotNull()
        {
            return super.andIdIsNotNull();
        }

        public volatile Criteria andBNameGreaterThan(String s)
        {
            return super.andBNameGreaterThan(s);
        }

        public volatile Criteria andIdIsNull()
        {
            return super.andIdIsNull();
        }

        public volatile Criteria andBNameNotIn(List list)
        {
            return super.andBNameNotIn(list);
        }

        public volatile Criteria andIdNotEqualTo(Integer integer)
        {
            return super.andIdNotEqualTo(integer);
        }

        public volatile Criteria andBNameIsNull()
        {
            return super.andBNameIsNull();
        }

        public volatile Criteria andBNameLessThanOrEqualTo(String s)
        {
            return super.andBNameLessThanOrEqualTo(s);
        }

        public volatile Criteria andBTypeIsNotNull()
        {
            return super.andBTypeIsNotNull();
        }

        public volatile Criteria andBTypeLessThanOrEqualTo(Integer integer)
        {
            return super.andBTypeLessThanOrEqualTo(integer);
        }

        public volatile Criteria andIdGreaterThan(Integer integer)
        {
            return super.andIdGreaterThan(integer);
        }

        public volatile Criteria andIdLessThanOrEqualTo(Integer integer)
        {
            return super.andIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andBNameEqualTo(String s)
        {
            return super.andBNameEqualTo(s);
        }

        public volatile Criteria andBTypeNotEqualTo(Integer integer)
        {
            return super.andBTypeNotEqualTo(integer);
        }

        public volatile Criteria andIdNotIn(List list)
        {
            return super.andIdNotIn(list);
        }

        public volatile List getCriteria()
        {
            return super.getCriteria();
        }

        public volatile Criteria andBNameNotBetween(String s, String s1)
        {
            return super.andBNameNotBetween(s, s1);
        }

        public volatile Criteria andBNameGreaterThanOrEqualTo(String s)
        {
            return super.andBNameGreaterThanOrEqualTo(s);
        }

        public volatile Criteria andBTypeGreaterThanOrEqualTo(Integer integer)
        {
            return super.andBTypeGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andBTypeBetween(Integer integer, Integer integer1)
        {
            return super.andBTypeBetween(integer, integer1);
        }

        public volatile Criteria andBTypeIn(List list)
        {
            return super.andBTypeIn(list);
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

        public Criteria andBTypeIsNull()
        {
            addCriterion("b_type is null");
            return (Criteria)this;
        }

        public Criteria andBTypeIsNotNull()
        {
            addCriterion("b_type is not null");
            return (Criteria)this;
        }

        public Criteria andBTypeEqualTo(Integer value)
        {
            addCriterion("b_type =", value, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeNotEqualTo(Integer value)
        {
            addCriterion("b_type <>", value, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeGreaterThan(Integer value)
        {
            addCriterion("b_type >", value, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("b_type >=", value, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeLessThan(Integer value)
        {
            addCriterion("b_type <", value, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeLessThanOrEqualTo(Integer value)
        {
            addCriterion("b_type <=", value, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeIn(List values)
        {
            addCriterion("b_type in", values, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeNotIn(List values)
        {
            addCriterion("b_type not in", values, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeBetween(Integer value1, Integer value2)
        {
            addCriterion("b_type between", value1, value2, "bType");
            return (Criteria)this;
        }

        public Criteria andBTypeNotBetween(Integer value1, Integer value2)
        {
            addCriterion("b_type not between", value1, value2, "bType");
            return (Criteria)this;
        }

        public Criteria andBNameIsNull()
        {
            addCriterion("b_name is null");
            return (Criteria)this;
        }

        public Criteria andBNameIsNotNull()
        {
            addCriterion("b_name is not null");
            return (Criteria)this;
        }

        public Criteria andBNameEqualTo(String value)
        {
            addCriterion("b_name =", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameNotEqualTo(String value)
        {
            addCriterion("b_name <>", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameGreaterThan(String value)
        {
            addCriterion("b_name >", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameGreaterThanOrEqualTo(String value)
        {
            addCriterion("b_name >=", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameLessThan(String value)
        {
            addCriterion("b_name <", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameLessThanOrEqualTo(String value)
        {
            addCriterion("b_name <=", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameLike(String value)
        {
            addCriterion("b_name like", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameNotLike(String value)
        {
            addCriterion("b_name not like", value, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameIn(List values)
        {
            addCriterion("b_name in", values, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameNotIn(List values)
        {
            addCriterion("b_name not in", values, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameBetween(String value1, String value2)
        {
            addCriterion("b_name between", value1, value2, "bName");
            return (Criteria)this;
        }

        public Criteria andBNameNotBetween(String value1, String value2)
        {
            addCriterion("b_name not between", value1, value2, "bName");
            return (Criteria)this;
        }

        protected List criteria;

        protected GeneratedCriteria()
        {
            criteria = new ArrayList();
        }
    }


    public BusinessTypeExample()
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
