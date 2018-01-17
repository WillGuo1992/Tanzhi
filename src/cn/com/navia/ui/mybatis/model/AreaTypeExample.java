// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AreaTypeExample.java

package cn.com.navia.ui.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class AreaTypeExample
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

        public volatile Criteria andTypeNameIsNotNull()
        {
            return super.andTypeNameIsNotNull();
        }

        public volatile List getAllCriteria()
        {
            return super.getAllCriteria();
        }

        public volatile Criteria andATypeNotIn(List list)
        {
            return super.andATypeNotIn(list);
        }

        public volatile Criteria andIdNotBetween(Integer integer, Integer integer1)
        {
            return super.andIdNotBetween(integer, integer1);
        }

        public volatile Criteria andTypeNameIsNull()
        {
            return super.andTypeNameIsNull();
        }

        public volatile Criteria andTypeNameBetween(String s, String s1)
        {
            return super.andTypeNameBetween(s, s1);
        }

        public volatile Criteria andTypeNameNotLike(String s)
        {
            return super.andTypeNameNotLike(s);
        }

        public volatile Criteria andATypeIn(List list)
        {
            return super.andATypeIn(list);
        }

        public volatile Criteria andATypeLessThan(Integer integer)
        {
            return super.andATypeLessThan(integer);
        }

        public volatile Criteria andATypeEqualTo(Integer integer)
        {
            return super.andATypeEqualTo(integer);
        }

        public volatile boolean isValid()
        {
            return super.isValid();
        }

        public volatile Criteria andIdBetween(Integer integer, Integer integer1)
        {
            return super.andIdBetween(integer, integer1);
        }

        public volatile Criteria andATypeGreaterThan(Integer integer)
        {
            return super.andATypeGreaterThan(integer);
        }

        public volatile Criteria andTypeNameNotBetween(String s, String s1)
        {
            return super.andTypeNameNotBetween(s, s1);
        }

        public volatile Criteria andATypeNotBetween(Integer integer, Integer integer1)
        {
            return super.andATypeNotBetween(integer, integer1);
        }

        public volatile Criteria andIdIsNotNull()
        {
            return super.andIdIsNotNull();
        }

        public volatile Criteria andTypeNameGreaterThan(String s)
        {
            return super.andTypeNameGreaterThan(s);
        }

        public volatile Criteria andTypeNameNotIn(List list)
        {
            return super.andTypeNameNotIn(list);
        }

        public volatile Criteria andTypeNameEqualTo(String s)
        {
            return super.andTypeNameEqualTo(s);
        }

        public volatile Criteria andIdIsNull()
        {
            return super.andIdIsNull();
        }

        public volatile Criteria andTypeNameLike(String s)
        {
            return super.andTypeNameLike(s);
        }

        public volatile Criteria andIdNotEqualTo(Integer integer)
        {
            return super.andIdNotEqualTo(integer);
        }

        public volatile Criteria andATypeIsNotNull()
        {
            return super.andATypeIsNotNull();
        }

        public volatile Criteria andATypeLessThanOrEqualTo(Integer integer)
        {
            return super.andATypeLessThanOrEqualTo(integer);
        }

        public volatile Criteria andIdGreaterThan(Integer integer)
        {
            return super.andIdGreaterThan(integer);
        }

        public volatile Criteria andIdLessThanOrEqualTo(Integer integer)
        {
            return super.andIdLessThanOrEqualTo(integer);
        }

        public volatile Criteria andTypeNameLessThanOrEqualTo(String s)
        {
            return super.andTypeNameLessThanOrEqualTo(s);
        }

        public volatile Criteria andTypeNameIn(List list)
        {
            return super.andTypeNameIn(list);
        }

        public volatile Criteria andTypeNameNotEqualTo(String s)
        {
            return super.andTypeNameNotEqualTo(s);
        }

        public volatile Criteria andATypeGreaterThanOrEqualTo(Integer integer)
        {
            return super.andATypeGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andATypeNotEqualTo(Integer integer)
        {
            return super.andATypeNotEqualTo(integer);
        }

        public volatile Criteria andIdNotIn(List list)
        {
            return super.andIdNotIn(list);
        }

        public volatile List getCriteria()
        {
            return super.getCriteria();
        }

        public volatile Criteria andATypeIsNull()
        {
            return super.andATypeIsNull();
        }

        public volatile Criteria andTypeNameLessThan(String s)
        {
            return super.andTypeNameLessThan(s);
        }

        public volatile Criteria andIdGreaterThanOrEqualTo(Integer integer)
        {
            return super.andIdGreaterThanOrEqualTo(integer);
        }

        public volatile Criteria andTypeNameGreaterThanOrEqualTo(String s)
        {
            return super.andTypeNameGreaterThanOrEqualTo(s);
        }

        public volatile Criteria andATypeBetween(Integer integer, Integer integer1)
        {
            return super.andATypeBetween(integer, integer1);
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

        public Criteria andATypeIsNull()
        {
            addCriterion("a_type is null");
            return (Criteria)this;
        }

        public Criteria andATypeIsNotNull()
        {
            addCriterion("a_type is not null");
            return (Criteria)this;
        }

        public Criteria andATypeEqualTo(Integer value)
        {
            addCriterion("a_type =", value, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeNotEqualTo(Integer value)
        {
            addCriterion("a_type <>", value, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeGreaterThan(Integer value)
        {
            addCriterion("a_type >", value, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("a_type >=", value, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeLessThan(Integer value)
        {
            addCriterion("a_type <", value, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeLessThanOrEqualTo(Integer value)
        {
            addCriterion("a_type <=", value, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeIn(List values)
        {
            addCriterion("a_type in", values, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeNotIn(List values)
        {
            addCriterion("a_type not in", values, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeBetween(Integer value1, Integer value2)
        {
            addCriterion("a_type between", value1, value2, "aType");
            return (Criteria)this;
        }

        public Criteria andATypeNotBetween(Integer value1, Integer value2)
        {
            addCriterion("a_type not between", value1, value2, "aType");
            return (Criteria)this;
        }

        public Criteria andTypeNameIsNull()
        {
            addCriterion("type_name is null");
            return (Criteria)this;
        }

        public Criteria andTypeNameIsNotNull()
        {
            addCriterion("type_name is not null");
            return (Criteria)this;
        }

        public Criteria andTypeNameEqualTo(String value)
        {
            addCriterion("type_name =", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameNotEqualTo(String value)
        {
            addCriterion("type_name <>", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameGreaterThan(String value)
        {
            addCriterion("type_name >", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameGreaterThanOrEqualTo(String value)
        {
            addCriterion("type_name >=", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameLessThan(String value)
        {
            addCriterion("type_name <", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameLessThanOrEqualTo(String value)
        {
            addCriterion("type_name <=", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameLike(String value)
        {
            addCriterion("type_name like", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameNotLike(String value)
        {
            addCriterion("type_name not like", value, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameIn(List values)
        {
            addCriterion("type_name in", values, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameNotIn(List values)
        {
            addCriterion("type_name not in", values, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameBetween(String value1, String value2)
        {
            addCriterion("type_name between", value1, value2, "typeName");
            return (Criteria)this;
        }

        public Criteria andTypeNameNotBetween(String value1, String value2)
        {
            addCriterion("type_name not between", value1, value2, "typeName");
            return (Criteria)this;
        }

        protected List criteria;

        protected GeneratedCriteria()
        {
            criteria = new ArrayList();
        }
    }


    public AreaTypeExample()
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
