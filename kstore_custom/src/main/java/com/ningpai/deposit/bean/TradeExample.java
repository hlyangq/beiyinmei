package com.ningpai.deposit.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TradeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(Long value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(Long value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(Long value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(Long value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(Long value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<Long> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<Long> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(Long value1, Long value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(Long value1, Long value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("order_type like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("order_type not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }

        public Criteria andOrderStatusEqualTo(String value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotEqualTo(String value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThan(String value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(String value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThan(String value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(String value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusLike(String value) {
            addCriterion("order_status like", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotLike(String value) {
            addCriterion("order_status not like", value, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusIn(List<String> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotIn(List<String> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusBetween(String value1, String value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderStatusNotBetween(String value1, String value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNull() {
            addCriterion("order_price is null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIsNotNull() {
            addCriterion("order_price is not null");
            return (Criteria) this;
        }

        public Criteria andOrderPriceEqualTo(BigDecimal value) {
            addCriterion("order_price =", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotEqualTo(BigDecimal value) {
            addCriterion("order_price <>", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThan(BigDecimal value) {
            addCriterion("order_price >", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_price >=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThan(BigDecimal value) {
            addCriterion("order_price <", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_price <=", value, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceIn(List<BigDecimal> values) {
            addCriterion("order_price in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotIn(List<BigDecimal> values) {
            addCriterion("order_price not in", values, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_price between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andOrderPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_price not between", value1, value2, "orderPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceIsNull() {
            addCriterion("current_price is null");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceIsNotNull() {
            addCriterion("current_price is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceEqualTo(BigDecimal value) {
            addCriterion("current_price =", value, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceNotEqualTo(BigDecimal value) {
            addCriterion("current_price <>", value, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceGreaterThan(BigDecimal value) {
            addCriterion("current_price >", value, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("current_price >=", value, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceLessThan(BigDecimal value) {
            addCriterion("current_price <", value, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("current_price <=", value, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceIn(List<BigDecimal> values) {
            addCriterion("current_price in", values, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceNotIn(List<BigDecimal> values) {
            addCriterion("current_price not in", values, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current_price between", value1, value2, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andCurrentPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("current_price not between", value1, value2, "currentPrice");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkIsNull() {
            addCriterion("trade_remark is null");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkIsNotNull() {
            addCriterion("trade_remark is not null");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkEqualTo(String value) {
            addCriterion("trade_remark =", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkNotEqualTo(String value) {
            addCriterion("trade_remark <>", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkGreaterThan(String value) {
            addCriterion("trade_remark >", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("trade_remark >=", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkLessThan(String value) {
            addCriterion("trade_remark <", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkLessThanOrEqualTo(String value) {
            addCriterion("trade_remark <=", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkLike(String value) {
            addCriterion("trade_remark like", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkNotLike(String value) {
            addCriterion("trade_remark not like", value, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkIn(List<String> values) {
            addCriterion("trade_remark in", values, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkNotIn(List<String> values) {
            addCriterion("trade_remark not in", values, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkBetween(String value1, String value2) {
            addCriterion("trade_remark between", value1, value2, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andTradeRemarkNotBetween(String value1, String value2) {
            addCriterion("trade_remark not between", value1, value2, "tradeRemark");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {
            addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {
            addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNull() {
            addCriterion("create_person is null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIsNotNull() {
            addCriterion("create_person is not null");
            return (Criteria) this;
        }

        public Criteria andCreatePersonEqualTo(Long value) {
            addCriterion("create_person =", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotEqualTo(Long value) {
            addCriterion("create_person <>", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThan(Long value) {
            addCriterion("create_person >", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonGreaterThanOrEqualTo(Long value) {
            addCriterion("create_person >=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThan(Long value) {
            addCriterion("create_person <", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonLessThanOrEqualTo(Long value) {
            addCriterion("create_person <=", value, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonIn(List<Long> values) {
            addCriterion("create_person in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotIn(List<Long> values) {
            addCriterion("create_person not in", values, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonBetween(Long value1, Long value2) {
            addCriterion("create_person between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andCreatePersonNotBetween(Long value1, Long value2) {
            addCriterion("create_person not between", value1, value2, "createPerson");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkIsNull() {
            addCriterion("reviewed_remark is null");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkIsNotNull() {
            addCriterion("reviewed_remark is not null");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkEqualTo(String value) {
            addCriterion("reviewed_remark =", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkNotEqualTo(String value) {
            addCriterion("reviewed_remark <>", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkGreaterThan(String value) {
            addCriterion("reviewed_remark >", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("reviewed_remark >=", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkLessThan(String value) {
            addCriterion("reviewed_remark <", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkLessThanOrEqualTo(String value) {
            addCriterion("reviewed_remark <=", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkLike(String value) {
            addCriterion("reviewed_remark like", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkNotLike(String value) {
            addCriterion("reviewed_remark not like", value, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkIn(List<String> values) {
            addCriterion("reviewed_remark in", values, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkNotIn(List<String> values) {
            addCriterion("reviewed_remark not in", values, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkBetween(String value1, String value2) {
            addCriterion("reviewed_remark between", value1, value2, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andReviewedRemarkNotBetween(String value1, String value2) {
            addCriterion("reviewed_remark not between", value1, value2, "reviewedRemark");
            return (Criteria) this;
        }

        public Criteria andCertificateImgIsNull() {
            addCriterion("certificate_img is null");
            return (Criteria) this;
        }

        public Criteria andCertificateImgIsNotNull() {
            addCriterion("certificate_img is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateImgEqualTo(String value) {
            addCriterion("certificate_img =", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgNotEqualTo(String value) {
            addCriterion("certificate_img <>", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgGreaterThan(String value) {
            addCriterion("certificate_img >", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_img >=", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgLessThan(String value) {
            addCriterion("certificate_img <", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgLessThanOrEqualTo(String value) {
            addCriterion("certificate_img <=", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgLike(String value) {
            addCriterion("certificate_img like", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgNotLike(String value) {
            addCriterion("certificate_img not like", value, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgIn(List<String> values) {
            addCriterion("certificate_img in", values, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgNotIn(List<String> values) {
            addCriterion("certificate_img not in", values, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgBetween(String value1, String value2) {
            addCriterion("certificate_img between", value1, value2, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andCertificateImgNotBetween(String value1, String value2) {
            addCriterion("certificate_img not between", value1, value2, "certificateImg");
            return (Criteria) this;
        }

        public Criteria andPayBillNumIsNull() {
            addCriterion("pay_bill_num is null");
            return (Criteria) this;
        }

        public Criteria andPayBillNumIsNotNull() {
            addCriterion("pay_bill_num is not null");
            return (Criteria) this;
        }

        public Criteria andPayBillNumEqualTo(String value) {
            addCriterion("pay_bill_num =", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumNotEqualTo(String value) {
            addCriterion("pay_bill_num <>", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumGreaterThan(String value) {
            addCriterion("pay_bill_num >", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumGreaterThanOrEqualTo(String value) {
            addCriterion("pay_bill_num >=", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumLessThan(String value) {
            addCriterion("pay_bill_num <", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumLessThanOrEqualTo(String value) {
            addCriterion("pay_bill_num <=", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumLike(String value) {
            addCriterion("pay_bill_num like", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumNotLike(String value) {
            addCriterion("pay_bill_num not like", value, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumIn(List<String> values) {
            addCriterion("pay_bill_num in", values, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumNotIn(List<String> values) {
            addCriterion("pay_bill_num not in", values, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumBetween(String value1, String value2) {
            addCriterion("pay_bill_num between", value1, value2, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andPayBillNumNotBetween(String value1, String value2) {
            addCriterion("pay_bill_num not between", value1, value2, "payBillNum");
            return (Criteria) this;
        }

        public Criteria andTradeSourceIsNull() {
            addCriterion("trade_source is null");
            return (Criteria) this;
        }

        public Criteria andTradeSourceIsNotNull() {
            addCriterion("trade_source is not null");
            return (Criteria) this;
        }

        public Criteria andTradeSourceEqualTo(String value) {
            addCriterion("trade_source =", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotEqualTo(String value) {
            addCriterion("trade_source <>", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceGreaterThan(String value) {
            addCriterion("trade_source >", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceGreaterThanOrEqualTo(String value) {
            addCriterion("trade_source >=", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceLessThan(String value) {
            addCriterion("trade_source <", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceLessThanOrEqualTo(String value) {
            addCriterion("trade_source <=", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceLike(String value) {
            addCriterion("trade_source like", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotLike(String value) {
            addCriterion("trade_source not like", value, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceIn(List<String> values) {
            addCriterion("trade_source in", values, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotIn(List<String> values) {
            addCriterion("trade_source not in", values, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceBetween(String value1, String value2) {
            addCriterion("trade_source between", value1, value2, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andTradeSourceNotBetween(String value1, String value2) {
            addCriterion("trade_source not between", value1, value2, "tradeSource");
            return (Criteria) this;
        }

        public Criteria andPayRemarkIsNull() {
            addCriterion("pay_remark is null");
            return (Criteria) this;
        }

        public Criteria andPayRemarkIsNotNull() {
            addCriterion("pay_remark is not null");
            return (Criteria) this;
        }

        public Criteria andPayRemarkEqualTo(String value) {
            addCriterion("pay_remark =", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkNotEqualTo(String value) {
            addCriterion("pay_remark <>", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkGreaterThan(String value) {
            addCriterion("pay_remark >", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("pay_remark >=", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkLessThan(String value) {
            addCriterion("pay_remark <", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkLessThanOrEqualTo(String value) {
            addCriterion("pay_remark <=", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkLike(String value) {
            addCriterion("pay_remark like", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkNotLike(String value) {
            addCriterion("pay_remark not like", value, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkIn(List<String> values) {
            addCriterion("pay_remark in", values, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkNotIn(List<String> values) {
            addCriterion("pay_remark not in", values, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkBetween(String value1, String value2) {
            addCriterion("pay_remark between", value1, value2, "payRemark");
            return (Criteria) this;
        }

        public Criteria andPayRemarkNotBetween(String value1, String value2) {
            addCriterion("pay_remark not between", value1, value2, "payRemark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}