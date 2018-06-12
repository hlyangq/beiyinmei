/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.customer.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.ningpai.customer.dao.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ningpai.customer.bean.*;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.customer.service.PointLevelServiceMapper;
import com.ningpai.other.bean.IntegralSet;
import com.ningpai.util.PageBean;

/**
 * @see com.ningpai.customer.service.CustomerPointServiceMapper
 * @author NINGPAI-zhangqiang
 * @since 2014年3月20日 上午11:54:12
 * @version 0.0.1
 */
@Service("customerPointServiceMapper")
public class CustomerPointServiceMapperImpl implements CustomerPointServiceMapper {

    // spring注解
    private CustomerPointMapper customerPointMapper;
    private IntegralSetMapper integralSetmapper;
    private CustomerInfoMapper customerInfoMapper;
    private PointLevelServiceMapper pointLevelServiceMapper;

    @Resource(name = "customerPointLevelMapper")
    private CustomerPointLevelMapper customerPointLevelMapper;

    /*
     * 
     * 
     * @see com.ningpai.customer.service.CustomerPointServiceMapper#
     * selectCustPointByCustPoint (com.ningpai.customer.bean.CustomerPoint,
     * com.ningpai.util.PageBean)
     */
    @Override
    public PageBean queryregisterpoint(RegisterPoint point, PageBean pageBean) {
        // 装在查询条件
        Map<String, Object> paramMap = new HashMap<String, Object>();
        int no = 0;
        try {
            // 设置查询条件
            if (point != null) {
                paramMap.put("regPointReferee", point.getRegPointReferee());// 推荐人
                paramMap.put("regPointRecom", point.getRegPointRecom());// 被推荐人
                if(!"".equals(point.getCreateTimeF()) && point.getCreateTimeF()!=null){
                    paramMap.put("startTime", point.getStartTime());// 开始时间

                }
                if(!"".equals(point.getCreateTimeT()) && point.getCreateTimeT()!=null){
                    paramMap.put("endTime", point.getEndTime());// 开始时间
                }
            }
            // 设置总行数
            pageBean.setRows(customerPointMapper.selectRegisterPointSize(paramMap));
            no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean.getRows() / pageBean.getPageSize() : (pageBean.getRows() / pageBean.getPageSize() + 1);
            no = no == 0 ? 1 : no;
            // 若页码超过最大页码 则显示最后一个
            if (pageBean.getPageNo() >= no) {
                pageBean.setPageNo(no);
                pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
            }
            // 查询会员信息
            pageBean.setList(customerPointMapper.selectRegisterPont(paramMap));
        } finally {
            paramMap = null;
        }
        return pageBean;
    }

    /*
     * 查询所有会员积分记录 
     * 
     * @see com.ningpai.customer.service.CustomerPointServiceMapper#
     * selectAllCustomerPoint(java.util.Map)
     */
    @Override
    public PageBean selectAllCustomerPoint(PageBean pageBean) {
        Map<String, Integer> paramMap = null;
        int no = 0;
        try {
            pageBean.setRows(customerPointMapper.selectAllCustomerCount());
            no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean.getRows() / pageBean.getPageSize() : (pageBean.getRows() / pageBean.getPageSize() + 1);
            no = no == 0 ? 1 : no;
            if (pageBean.getPageNo() >= no) {
                pageBean.setPageNo(no);
                pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
            }
            paramMap = new HashMap<String, Integer>();
            paramMap.put("startRowNum", pageBean.getStartRowNum());
            paramMap.put("endRowNum", pageBean.getEndRowNum());
            pageBean.setList(customerPointMapper.selectAllCustomerPoint(paramMap));
        } finally {
            paramMap = null;
        }
        return pageBean;
    }

    /*
     * 删除会员积分记录
     * 
     * @see
     * com.ningpai.customer.service.CustomerPointServiceMapper#deleteCustomerPoint
     * (java.lang.String[])
     */
    @Override
    public int deleteCustomerPoint(String[] parameterValues) {
        Integer count = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("parameterValues", parameterValues);
            count = customerPointMapper.deleteCustomerPointByPids(paramMap);
        } finally {
            paramMap = null;
        }

        return count;
    }

    /*
     * 按条件查找积分记录
     * 
     * @see com.ningpai.customer.service.CustomerPointServiceMapper#
     * selectCustPointByCustPoint (com.ningpai.customer.bean.CustomerPoint,
     * com.ningpai.util.PageBean)
     */
    @Override
    public PageBean selectCustPointByCustPoint(CustomerPoint point, PageBean pageBean) {
        Map<String, Integer> paramMap = null;
        int no = 0;
        try {
            // 设置总行数
            pageBean.setRows(customerPointMapper.selectCustmerPointSize(point));
            no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean.getRows() / pageBean.getPageSize() : (pageBean.getRows() / pageBean.getPageSize() + 1);
            no = no == 0 ? 1 : no;
            // 若页码超过最大页码 则显示最后一个
            if (pageBean.getPageNo() >= no) {
                pageBean.setPageNo(no);
                pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
            }
            // 设置查询条件
            if (point != null) {
                point.setStartRowNum(pageBean.getStartRowNum());
                point.setEndRowNum(pageBean.getEndRowNum());
            }
            paramMap = new HashMap<String, Integer>();
            paramMap.put("startRowNum", pageBean.getStartRowNum());
            paramMap.put("endRowNum", pageBean.getEndRowNum());
            // 查询会员信息
            pageBean.setList(customerPointMapper.selectCustPointByCustPoint(point));
        } finally {
            paramMap = null;
        }
        return pageBean;
    }

    /*
     * 根据类型添加积分 会员编号
     *  KKK 加积分
     * @see
     * com.ningpai.customer.service.CustomerPointServiceMapper#addIntegralByType
     * (java.lang.Long, java.lang.String)
     */
    @Override
    public int addIntegralByType(Long customerId, String type) {
        IntegralSet inte = integralSetmapper.findPointSet();
        // 积分类型不为空 并且boss端积分状态是开启的
        if (type != null && "1".equals(inte.getIsOpen())) {
            CustomerPoint customerPoint = new CustomerPoint();
            if ("0".equals(type)) {
                customerPoint.setPointDetail("注册赠送");
                customerPoint.setPoint(inte.getPsetRegister());
            } else if ("1".equals(type)) {
                customerPoint.setPointDetail("登录赠送");
                customerPoint.setPoint(inte.getPsetLogin());
            } else if ("2".equals(type)) {
                customerPoint.setPointDetail("邮箱验证赠送");
                customerPoint.setPoint(inte.getPsetEmail());
            } else if ("4".equals(type)) {
                customerPoint.setPointDetail("发表评论赠送");
                customerPoint.setPoint(inte.getPsetComment());
            } else if ("5".equals(type)) {
                customerPoint.setPointDetail("推荐用户赠送");
                customerPoint.setPoint(inte.getPsetUser());
            } else if ("7".equals(type)) {
                customerPoint.setPointDetail("发表话题赠送");
                customerPoint.setPoint(inte.getPsetPubtopic());
            } else if ("8".equals(type)) {
                customerPoint.setPointDetail("热门话题赠送");
                customerPoint.setPoint(inte.getPsetHottopic());
            } else if ("9".equals(type)) {
                customerPoint.setPointDetail("精选话题赠送");
                customerPoint.setPoint(inte.getPsetEssencetopic());
            } else if ("10".equals(type)) {
                customerPoint.setPointDetail("推荐到首页话题赠送");
                customerPoint.setPoint(inte.getPsetEssencetopic());
            } else if ("11".equals(type)) {
                customerPoint.setPointDetail("晒单送积分");
                customerPoint.setPoint(inte.getPsetOnline());
            }
            customerPoint.setPointType("1");
            customerPoint.setDelFlag("0");
            customerPoint.setCreateTime(new Date());
            customerPoint.setCustomerId(customerId);
            customerPointMapper.insertSelective(customerPoint);
            CustomerInfo info = customerInfoMapper.selectCustInfoById(customerId);
            info.setInfoPointSum(info.getInfoPointSum() + customerPoint.getPoint());
            info.setCustomerId(customerId);
            // 积分增加完成后 判断积分是否满足升级要求 是否在下一等级的区间中
            // 若是在下一个升级区间中则自动升级等级
            // 遗留问题:按照积分升级是否会造成积分减少时,等级降低问题,要是等级不降低,那么怎么样处理积分减少造成的问题??
            for (CustomerPointLevel level : pointLevelServiceMapper.selectAllPointLevel()) {
                String[] points = level.getPointNeed().split("~");
                if (Integer.valueOf(points[0]) <= info.getInfoPointSum() && info.getInfoPointSum() <= Integer.valueOf(points[1])) {
                    info.setPointLevelName(level.getPointLevelName());
                    info.setPointLevelId(level.getPointLelvelId());
                }
            }

            customerInfoMapper.updateInfoByCustId(info);
        }
        return 0;
    }

    /*
     * 添加或扣除会员积分
     * 
     * @see com.ningpai.customer.service.CustomerPointServiceMapper#saveCustomerPoint(java.lang.Long, com.ningpai.customer.bean.CustomerPoint)
     */
    @Override
    public int saveCustomerPoint(Long customerId, CustomerPoint customerpoint,String mgUserName) {

        Integer point=0;
        point=customerpoint.getPoint();
        if (customerpoint.getPoint() < 0) {
            // 积分扣除
            customerpoint.setPointType("0");
        } else {
            // 积分奖励
            customerpoint.setPointType("1");
        }

        customerpoint.setDelFlag("0");
        customerpoint.setCreateTime(new Date());
        customerpoint.setCustomerId(customerId);
        customerpoint.setCreateUser(mgUserName);
        //取绝对值 保证与其他罚款一致
        customerpoint.setPoint(Math.abs(point));
        CustomerInfo info = customerInfoMapper.selectByCustomerId(customerId);
        info.setInfoPointSum(this.getCustomerAllPointWhitReduce(customerId + ""));
        info.setInfoPointSum(info.getInfoPointSum() + point);
        customerPointMapper.insertSelective(customerpoint);
        // 添加积分时,检测是否满足升级要求
        for (CustomerPointLevel level : pointLevelServiceMapper.selectAllPointLevel()) {
            String[] points = level.getPointNeed().split("~");
            if("1".equals(customerpoint.getPointType())){
                if (Integer.valueOf(points[0]) <= info.getInfoPointSum() && info.getInfoPointSum() <= Integer.valueOf(points[1])) {
                    info.setPointLevelName(level.getPointLevelName());
                    info.setPointLevelId(level.getPointLelvelId());
                }
            }

        }
        int count = customerInfoMapper.updateInfoByCustId(info);
        if (count == 1) {
            return info.getInfoPointSum();
        } else {
            return -1;
        }

    }

    /*
     * 根据类型添加积分 会员编号
     */
    @Override
    public int addIntegralByType(Long customerId, String type, Double orderPrice,String orderId) {
        if (type != null) {
            CustomerPoint customerPoint = new CustomerPoint();
            IntegralSet inte = integralSetmapper.findPointSet();
            // 每100元兑换积分
            double ex = 100;
            if ("6".equals(type)) {
                customerPoint.setPointDetail("购物订单完成奖励");
                customerPoint.setPoint(((Double) (orderPrice * (inte.getExchange() / ex))).intValue());
            }
            customerPoint.setPointType("1");
            customerPoint.setDelFlag("0");
            customerPoint.setCreateTime(new Date());
            customerPoint.setCustomerId(customerId);
            customerPointMapper.insertSelective(customerPoint);
            CustomerInfo info = customerInfoMapper.selectByCustomerId(customerId);
            info.setInfoPointSum(info.getInfoPointSum() + customerPoint.getPoint());
            info.setCustomerId(customerId);
            // 添加积分时,检测是否满足升级要求
            for (CustomerPointLevel level : pointLevelServiceMapper.selectAllPointLevel()) {
                String[] points = level.getPointNeed().split("~");
                if (Integer.valueOf(points[0]) <= info.getInfoPointSum() && info.getInfoPointSum() <= Integer.valueOf(points[1])) {
                    info.setPointLevelName(level.getPointLevelName());
                    info.setPointLevelId(level.getPointLelvelId());
                }
            }

            // 如果是订单完成 则修改订单表的积分赠送
            if ("6".equals(type)) {
                Map<String, Object> params = new HashMap<>();
                params.put("orderId", orderId);
                params.put("point", customerPoint.getPoint());
                customerPointMapper.updateOrderPoint(params);
            }
            customerInfoMapper.updateInfoByCustId(info);
        }
        return 0;
    }

    @Override
    public int updateIntegralById(Integer psetRegister) {
        return integralSetmapper.updateIntegralById(psetRegister);
    }



    /**
     * 获得会员的总积分 (注意 不减去用户 用掉的积分  只算用户获得的积分)
     *
     * @param customerId 会员id
     * @return 返回会员的总积分
     */
    @Override
    public Integer getCustomerAllPoint(String customerId) {

        if (StringUtils.isEmpty(customerId)) {
            return 0;
        }

        return calcCustomerAllPont(queryCustomerAllPonits(customerId));
    }

    /**
     * 获得会员的总积分(注意 不减去用户 用掉的积分  只算用户获得的积分)
     *
     * @param customerId 会员id
     * @return 返回会员的总积分
     */
    @Override
    public Integer getCustomerAllPointSimple(String customerId) {
        return customerPointMapper.queryCustomerAllPointsSimple(customerId);
    }

    /**
     * 获得 用户的总积分 （获得的-扣除的）
     *
     * @param customerId
     * @return
     */
    private Integer getCustomerAllPointWhitReduce(String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            return 0;
        }
        return calcCustomerAllPontWithReduce(queryCustomerAllPonits(customerId));
    }


    /**
     * 获得会员总的用掉的积分
     *
     * @param customerId 会员id
     * @return 返回会员的总积分
     */
    @Override
    public Integer getCustomerReducePoint(String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            return 0;
        }

        return calcCustomerAllReducePont(queryCustomerAllPonits(customerId));
    }

    /**
     * 根据会用总积分计算会员的折扣
     *
     * @param point 会员总积分
     * @return 返回 总积分对应的折扣
     */
    @Override
    public BigDecimal getCustomerDiscountByPoint(int point) {

        // 得到 总积分对应的会员等级
        CustomerPointLevel customerPointLevel = getCustomerPointLevelByPoint(point);

        if (null == customerPointLevel)
        {
            return new BigDecimal(1);
        }

        return customerPointLevel.getPointDiscount();
    }

    /**
     * 根据会员 总积分获得会员等级
     *
     * @param point
     * @return
     */
    @Override
    public CustomerPointLevel getCustomerPointLevelByPoint(int point) {

        // 查询出所有的会员等级
        List<CustomerPointLevel> allCustomerPointLevel = getAllCustomerPointLevelSort();

        if (CollectionUtils.isEmpty(allCustomerPointLevel)) {
            return null;
        }

        CustomerPointLevel result = null;

        for (CustomerPointLevel customerPointLevel : allCustomerPointLevel) {
            //判断 输入的积分是否在当前区间内
            if (customerPointLevel.isPointInThisRegion(point)) {
                result = customerPointLevel;
            }
        }

        if (null != result) {
            return result;
        }

        // 根据用户的积分 获得最小的会员等级 或者最大的会员等级
        return getCustomerPointLevelBySort(point, allCustomerPointLevel);
    }

    /**
     * 根据会员 总积分获得会员等级
     *
     * @param point               总积分
     * @param customerPointLevels 会员等级
     * @return 返回会员等级
     */
    @Override
    public CustomerPointLevel getCustomerPointLevelByPoint(int point, List<CustomerPointLevel> customerPointLevels) {
        if (CollectionUtils.isEmpty(customerPointLevels)) {
            return null;
        }

        CustomerPointLevel result = null;

        for (CustomerPointLevel customerPointLevel : customerPointLevels) {
            //判断 输入的积分是否在当前区间内
            if (customerPointLevel.isPointInThisRegion(point)) {
                result = customerPointLevel;
            }
        }

        if (null != result) {
            return result;
        }

        // 根据用户的积分 获得最小的会员等级 或者最大的会员等级
        return getCustomerPointLevelBySort(point, customerPointLevels);
    }

    /**
     * 获取最大会员等级的积分
     *
     * @return 返回最大会员等级的积分
     */
    @Override
    public Long getMaxCustomerPointLevelPoint() {

        List<CustomerPointLevel> customerPointLevels = this.getAllCustomerPointLevelSort();

        if (CollectionUtils.isEmpty(customerPointLevels)) {
            return 0L;
        }


        return customerPointLevels.get(customerPointLevels.size()-1).getMaxPoint();
    }

    /**
     * 删除所有的会员等级
     *
     * @return 失败返回0
     */
    @Override
    public int deleteAllCustomerPointLevel() {
        return customerPointLevelMapper.deleteAllCustomerPointLevel();
    }

    /**
     * 根据用户的积分 获得最小的会员等级 或者最大的会员等级
     * @param point 积分
     * @param allCustomerPointLevel  会员等级
     * @return
     */
    private CustomerPointLevel getCustomerPointLevelBySort(int point, List<CustomerPointLevel> allCustomerPointLevel) {

        // 首先拿出最小的会员等级
        CustomerPointLevel minCustomerPointLevel = allCustomerPointLevel.get(0);

        // 如果会员的积分 比最小的会员等级 还小  则设置会最小的会员等级
        if (point < minCustomerPointLevel.getMinPoint()) {
            return minCustomerPointLevel;
        }

        // 如果 会员的积分 比最大的会员等级还大 则设置为最大的会员等级
        CustomerPointLevel maxCustomerPointLevel = allCustomerPointLevel.get(allCustomerPointLevel.size()-1);

        if (point > minCustomerPointLevel.getMaxPoint())
        {
            return maxCustomerPointLevel;
        }

        return null;
    }


    /**
     * 获得所有的积分等级
     *
     * @return
     */
    private List<CustomerPointLevel> getAllCustomerPointLevel() {
        return customerPointLevelMapper.selectAllPointLevel();
    }


    /**
     * 获得所有的积分等级 （排过序的 安装等级的从小到大排序）
     *
     * @return
     */
    public List<CustomerPointLevel> getAllCustomerPointLevelSort() {

        List<CustomerPointLevel> levels = getAllCustomerPointLevel();

        if (CollectionUtils.isEmpty(levels)) {
            return levels;
        }

        for (CustomerPointLevel customerPointLevel : levels) {
            customerPointLevel.setMinAndMaxPoint();
        }

        Collections.sort(levels, new CustomerPointLevelCompare());

        return levels;
    }



    /**
     * 根据用户的积分记录 (获得的-用掉的)
     *
     * @param customerPoints
     * @return
     */
    private Integer calcCustomerAllPontWithReduce(List<CustomerPoint> customerPoints) {
        if (CollectionUtils.isEmpty(customerPoints)) {
            return 0;
        }

        // 总积分
        int pointResult = 0;

        for (CustomerPoint customerPoint : customerPoints) {
            if ("1".equals(customerPoint.getPointType())) {
                pointResult += customerPoint.getPoint();
            } else {
                pointResult -= customerPoint.getPoint();
            }
        }

        return pointResult;
    }

    /**
     * 根据用户的积分记录 计算用户的总积分
     *
     * @param customerPoints
     * @return
     */
    private Integer calcCustomerAllPont(List<CustomerPoint> customerPoints) {
        if (CollectionUtils.isEmpty(customerPoints)) {
            return 0;
        }

        // 总积分
        int pointResult = 0;

        for (CustomerPoint customerPoint : customerPoints) {
            if ("1".equals(customerPoint.getPointType())) {
                pointResult += customerPoint.getPoint();
            }
        }

        return pointResult;
    }

    /**
     * 根据用户的积分记录 计算用户的总共用掉的积分
     *
     * @param customerPoints
     * @return
     */
    private Integer calcCustomerAllReducePont(List<CustomerPoint> customerPoints) {
        if (CollectionUtils.isEmpty(customerPoints)) {
            return 0;
        }

        // 总积分
        int pointResult = 0;

        for (CustomerPoint customerPoint : customerPoints) {
            if ("0".equals(customerPoint.getPointType())) {
                pointResult += customerPoint.getPoint();
            }
        }

        return pointResult;
    }

    private List<CustomerPoint> queryCustomerAllPonits(String customerId) {

        return customerPointMapper.queryCustomerAllPoints(customerId);
    }


    public PointLevelServiceMapper getPointLevelServiceMapper() {
        return pointLevelServiceMapper;
    }

    @Resource(name = "pointLevelServiceMapper")
    public void setPointLevelServiceMapper(PointLevelServiceMapper pointLevelServiceMapper) {
        this.pointLevelServiceMapper = pointLevelServiceMapper;
    }

    public CustomerPointMapper getCustomerPointMapper() {
        return customerPointMapper;
    }

    @Resource(name = "customerPointMapper")
    public void setCustomerPointMapper(CustomerPointMapper customerPointMapper) {
        this.customerPointMapper = customerPointMapper;
    }

    public IntegralSetMapper getIntegralSetmapper() {
        return integralSetmapper;
    }

    @Resource(name = "integralSetMapper")
    public void setIntegralSetmapper(IntegralSetMapper integralSetmapper) {
        this.integralSetmapper = integralSetmapper;
    }

    public CustomerInfoMapper getCustomerInfoMapper() {
        return customerInfoMapper;
    }

    @Resource(name = "customerInfoMapper")
    public void setCustomerInfoMapper(CustomerInfoMapper customerInfoMapper) {
        this.customerInfoMapper = customerInfoMapper;
    }

    @Override
    public int insertRegisterPoint(RegisterPoint point) {
        return this.customerInfoMapper.insertRegisterPoint(point);
    }

    @Override
    public Customer selectCusById(Long cusId) {
        return this.customerInfoMapper.selectCusById(cusId);
    }

    @Override
    public IntegralSet findPointSet() {
        return this.integralSetmapper.findPointSet();
    }

}
