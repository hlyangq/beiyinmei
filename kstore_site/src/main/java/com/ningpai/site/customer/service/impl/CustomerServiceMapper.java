/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.site.customer.service.impl;

import com.ningpai.common.bean.Sms;
import com.ningpai.common.dao.SmsMapper;
import com.ningpai.common.util.ConstantUtil;
import com.ningpai.common.util.GenerateLinkUtils;
import com.ningpai.common.util.SmsPost;
import com.ningpai.coupon.service.CouponService;
import com.ningpai.customer.bean.*;
import com.ningpai.customer.bean.CustomerConsume;
import com.ningpai.customer.dao.CustomerConsumeMapper;
import com.ningpai.customer.dao.CustomerInfoMapper;
import com.ningpai.customer.dao.CustomerPointMapper;
import com.ningpai.customer.dao.InsideLetterMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.goods.vo.GoodsProductVo;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.Order;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.dao.BackOrderMapper;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.dao.OrderMapper;
import com.ningpai.site.customer.bean.*;
import com.ningpai.site.customer.bean.CustomerPointLevel;
import com.ningpai.site.customer.mapper.CustomerFollowMapper;
import com.ningpai.site.customer.mapper.CustomerMapper;
import com.ningpai.site.customer.mapper.CustomerMapperSite;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.customer.vo.*;
import com.ningpai.site.order.bean.ExchangeCusmomerPoint;
import com.ningpai.system.bean.SystemsSet;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NINGPAI-zhangqiang
 * @version 0.0.1
 * @since 2014年1月13日 下午5:43:32
 */
@Service("customerServiceSite")
public class CustomerServiceMapper implements CustomerServiceInterface {

    public static final MyLogger LOGGER = new MyLogger(CustomerServiceMapper.class);

    private static final String CUSTOMERID = "customerId";

    /**
     * 会员Mapper
     */
    private CustomerMapper customerMapper;

    /**
     * 会员详细信息接口
     */
    private CustomerInfoMapper customerInfoMapper;

    /**
     * 会员积分接口
     */
    private CustomerPointServiceMapper customerPointServiceMapper;

    /**
     * 订单接口
     */
    private OrderMapper orderMapper;

    /**
     * 订单商品接口
     */
    @Resource(name = "OrderGoodsMapper")
    private OrderGoodsMapper orderGoodsMapper;

    /**
     * 优惠券接口
     */
    private CouponService couponService;

    /**
     * 邮箱服务器数据接口层
     */
    private SmsMapper mapper;

    /**
     * 会员MapperSite
     */
    private CustomerMapperSite customerMapperSite;

    @Resource(name ="customerConsumeMapper" )
    private CustomerConsumeMapper customerConsumeMapper;

    private CustomerPointMapper customerPointMapper;

    /**
     * spring注解站内信息
     */
    @Resource(name = "insideletter")
    private InsideLetterMapper insideletter;

    @Resource(name = "BackOrderMapper")
    private BackOrderMapper backOrderMapper;
    /**
     * 会员收藏Mapper
     */
    @Resource(name = "customerFollowMapperSite")
    private CustomerFollowMapper customerFollowMapper;

    /**
     * 按照主键编号查找
     *
     * @param customerId 会员编号
     * @return
     */
    public Customer queryCustomerById(Long customerId) {

        Customer customer = customerMapper.queryCustomerById(customerId);

        if (null != customer) {
            CustomerAllInfo customerAllInfo = (CustomerAllInfo) customer;
            customerAllInfo.setInfoPointSum(customerPointServiceMapper.getCustomerAllPoint(customerId + ""));
            com.ningpai.customer.bean.CustomerPointLevel customerPointLevel = customerPointServiceMapper.getCustomerPointLevelByPoint(customerAllInfo.getInfoPointSum());
            if (null != customerPointLevel) {
                customerAllInfo.setPointLevelName(customerPointLevel.getPointLevelName());
            }

            return customerAllInfo;
        } else {
            // 按照主键编号查找
            return customer;
        }

    }

    /**
     * 查询可以投诉的订单
     *
     * @param paramMap 条件查询参数
     * @param pb       分页参数
     * @return
     * @auther zhangsl
     */
    @Override
    public PageBean queryOrdersForComplain(Map<String, Object> paramMap, PageBean pb) {
        // 查询可以投诉的订单总数
        Long count = customerMapper.queryCountForComplain(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        if (pb.getPageNo() > pb.getLastPageNo()) {
            pb.setPageNo(pb.getLastPageNo());
        }
        if (pb.getPageNo() == 0) {
            pb.setPageNo(1);
        }
        paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 查询可以投诉的订单
        pb.setList(customerMapper.queryordersforcomplain(paramMap));
        return pb;
    }

    /**
     * 查询用户所有获得积分总和
     *
     * @param customerId 用户Id
     * @return 该用户所有获得的积分总和
     * @author houyichang 2015/9/25
     */
    @Override
    public int querySumByCustId(Long customerId) {
        return this.customerMapper.querySumByCustId(customerId);
    }

    /**
     * 根据客户id修改客户等级
     *
     * @param pointLevelId   等级id
     * @param pointLevelName 等级名称
     * @param customerId     客户id
     * @return 修改结果
     * @author houyichang 2015/9/25
     */
    @Override
    public int updCustLevel(Long pointLevelId, String pointLevelName, Long customerId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pointLevelId", pointLevelId);
        map.put("pointLevelName", pointLevelName);
        map.put("customerId", customerId);
        return this.customerMapper.updCustLevel(map);
    }

    /**
     * 查询会员基本信息
     *
     * @param customerId 会员编号
     * @return
     */
    @Override
    public CustomerAllInfo selectByPrimaryKey(Long customerId) {

        CustomerAllInfo customerAllInfo = customerMapper.selectByPrimaryKey(customerId);

        if (null != customerAllInfo) {
            com.ningpai.customer.bean.CustomerPointLevel customerPointLevel = customerPointServiceMapper.getCustomerPointLevelByPoint(customerPointServiceMapper.getCustomerAllPoint(customerId + ""));

            if (null != customerPointLevel) {
                customerAllInfo.setPointLevelName(customerPointLevel.getPointLevelName());
            }
        }
        return customerAllInfo;
    }

    /**
     * 验证邮箱有效
     *
     * @param request
     * @param d
     * @param checkCode
     * @return
     */
    @Override
    public int verifyCheckCode(HttpServletRequest request, Long d, String checkCode) {
        GenerateLinkUtils.md5(String.valueOf(new Date().getTime()));
        // 按照主键编号查找
        Customer customer = customerMapper.selectByPrimaryKey((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));

        if (!"1".equals(customer.getIsEmail())) {
            // 根据类型添加积分
            customerPointServiceMapper.addIntegralByType((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID), "2");
        }

        Customer cust = new Customer();
        cust.setCustomerId(d);
        cust.setIsEmail("1");
        cust.setCustomerId((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));

        // 按条件修改会员信息
        customerMapper.updateByPrimaryKeySelective(cust);

        return 0;
    }

    /**
     * 根据会员编号查找订单信息
     *
     * @param paramMap 查询条件
     * @param pb
     * @return
     */
    public PageBean queryMyOrders(Map<String, Object> paramMap, PageBean pb) {
        // 按条件查询所有订单数量
        Long count = customerMapper.searchTotalCountO(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 根据会员编号 和 订单状态 查找订单信息
        pb.setList(customerMapper.queryMyOrders(paramMap));
        return pb;
    }

    /**
     * 查询所有订单
     *
     * @param paramMap 查询订单条件
     * @param pb
     * @return
     */
    @Override
    public PageBean queryAllMyOrders(Map<String, Object> paramMap, PageBean pb) {
        // 查询所有订单数量
        Long count = customerMapper.searchTotalCount(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        if (pb.getPageNo() > pb.getLastPageNo()) {
            pb.setPageNo(pb.getLastPageNo());
        }
        if (pb.getPageNo() == 0) {
            pb.setPageNo(1);
        }
        paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 查询所有订单
        pb.setList(customerMapper.queryAllMyOrders(paramMap));
        return pb;
    }

    /**
     * 查询当前会员的退单信息
     *
     * @param paramMap 查询订单条件
     * @param pb
     * @return
     */
    @Override
    public PageBean queryAllBackMyOrders(Map<String, Object> paramMap, PageBean pb) {
        // 退单的数据条数
        Long count = customerMapper.searchTotalCountBack(paramMap);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        if (pb.getPageNo() > pb.getLastPageNo()) {
            pb.setPageNo(pb.getLastPageNo());
        }
        if (pb.getPageNo() == 0) {
            pb.setPageNo(1);
        }
        paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 查询该会员下面的
        List<Object> backOrders = customerMapper.queryAllMyBackOrders(paramMap);
        if (null != backOrders && !backOrders.isEmpty()) {
            for (int i = 0; i < backOrders.size(); i++) {
                BackOrder bo = (BackOrder) backOrders.get(i);
                if (!"".equals(bo.getBackGoodsIdAndSum())) {
                    // 获取退单对象 下面的退单的 商品Id
                    String[] strs = bo.getBackGoodsIdAndSum().split("-");
                    // 处理数据
                    if (strs.length > 0) {
                        // 遍历退单对象下面的商品Id
                        for (int j = 0; j < strs.length; j++) {
                            String strss = strs[j];
                            // 获取第J个商品的Id
                            Long goodsId = Long.valueOf(strss.substring(0, strss.indexOf(",")));
                            // 根据ID获取单个商品的详细信息
                            GoodsProductVo orderProductVo = backOrderMapper.selectGoodsById(goodsId);
                            //默认普通商品
                            orderProductVo.setIsPresent("0");
                            //查询ordergoods表id
                            if(strss.indexOf(",")!=strss.lastIndexOf(",")){
                                Long ordergoodsid = Long.valueOf(strss.substring(strss.lastIndexOf(",")+1,strss.length()));
                                if(null!=ordergoodsid){
                                    //查询ordergood 判断是否是赠品
                                    OrderGoods og = this.orderGoodsMapper.selectOrderGoodsByOGId(ordergoodsid);
                                    if(null!=og && null!=og.getIsPresent() && og.getIsPresent().equals("1")){
                                        //如果是赠品 则添加标识1
                                        orderProductVo.setIsPresent("1");
                                    }
                                }
                            }
                            // 循环把查询获取到的商品信息放入到退单对象的商品集合中
                            bo.getOrderGoodslistVo().add(orderProductVo);
                        }
                    }
                }
            }
            pb.setList(backOrders);
        }
        return pb;
    }

    /**
     * 根据订单编号查找订单信息
     *
     * @param orderId    订单编号
     * @param customerId
     * @return
     */
    @Override
    public Object queryOrderByCustIdAndOrderId(Long orderId, Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put(CUSTOMERID, customerId);
            paramMap.put(ConstantUtil.ORDERID, orderId);
            // 根据订单 会员编号查找订单信息
            return customerMapper.queryOrderByParamMap(paramMap);
        } finally {
            paramMap = null;
        }
    }

    /**
     * 根据订单编号查找订单收货地址
     *
     * @param orderId 订单编号
     * @return
     */
    @Override
    public Object queryCustomerAddressById(Long orderId) {
        // 根据订单编号查找订单收货地址
        return customerMapper.queryCustomerAddressById(orderId);
    }

    /**
     * 根据会员编号查找会员详细信息
     *
     * @param parseLong 会员编号
     * @return
     */
    @Override
    public Object queryCustomerInfoById(long parseLong) {
        // 根据会员编号查找会员详细信息
        return customerMapper.queryCustomerInfoById(parseLong);
    }

    /**
     * 修改会员信息
     *
     * @param allInfo 新信息对象
     * @param flag
     * @return
     */
    @Override
    public int modifyCustomerInfo(CustomerAllInfo allInfo, String flag) {
        if ("1".equals(flag)) {
            // 修改昵称
            customerMapper.modifyCustNickName(allInfo);
        }
        // 修改会员信息
        return customerMapper.modifyCustomerInfo(allInfo);
    }

    /**
     * 确认收货
     *
     * @param orderId 订单编号 {@link java.lang.Long}
     * @return
     */
    @Override
    public Integer comfirmOfGooods(Long orderId) {
        // 修改收货状态
        Integer result = customerMapper.comfirmOfGooods(orderId);
        // 成功后修改订单状态为已完成
        if (result == 1) {
            result += customerMapper.modifyOrderStatusToSuccess(orderId);
        }
        return result;
    }

    /**
     * 查询所有省份 用于添加会员页面填充省份
     *
     * @return
     */
    @Override
    public List<ProvinceBean> selectAllProvince() {
        // 查询所有省份 用于添加会员页面填充省份
        return customerMapper.selectAllProvince();
    }

    /**
     * 根据省份编号 查询所有城市
     *
     * @param provinceId 省份编号 java.lang.Long {@link java.lang.Long}
     * @return
     */
    @Override
    public List<CityBean> selectAllCityByPid(Long provinceId) {
        // 根据省份编号 查询所有城市
        return customerMapper.selectAllCityByPid(provinceId);
    }

    /**
     * 根据城市编号 查询所有区县
     *
     * @param cityId 城市编号 java.lang.Long {@link java.lang.Long}
     * @return
     */
    @Override
    public List<DistrictBean> selectAllDistrictByCid(Long cityId) {
        // 根据城市编号 查询所有区县
        return customerMapper.selectAllDistrictByCid(cityId);
    }

    /**
     * 按区县编号获取对应街道集合
     *
     * @param dId 区县编号 java.lang.Long {@link java.lang.Long}
     * @return
     */
    @Override
    public List<StreetBean> getAllStreetByDid(Long dId) {
        // 按区县编号获取对应街道集合
        return customerMapper.getAllStreetByDid(dId);
    }

    /**
     * 根据会员编号查找会员详细信息
     *
     * @param parseLong 会员编号
     * @return
     */
    @Override
    public Object queryCustomerByCustomerId(long parseLong) {

        Object object = customerMapper.queryCustomerByCustomerId(parseLong);
        if (null != object) {
            CustomerAllInfo customerAllInfo = (CustomerAllInfo) customerMapper.queryCustomerByCustomerId(parseLong);

            if (null != customerAllInfo) {
                customerAllInfo.setInfoPointSum(customerPointServiceMapper.getCustomerAllPoint(parseLong + ""));
            }

            return customerAllInfo;

        } else {
            return object;
        }
    }

    /**
     * 验证用户密码
     *
     * @param customerId 会员编号
     * @param password   当前输入密码
     * @return
     */
    @Override
    public int checkCustomerPassword(Long customerId, String password) {
        /*Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put(CustomerConstantStr.CUSTOMERID, customerId);
            map.put("password", password);
            // 检查用户输入的密码和原密码是否相同 相投返回1 不同返回0
            if (null != customerMapper.checkCustomerPwd(map)) {
                return 1;
            }
            return 0;
        } finally {
            map = null;
        }*/
        //根据ID查询
        Customer customer = customerMapper.queryLoginInfoByCustomerId(customerId);
        if (customer != null) {//会员信息不为空时
            //验证加密后的密码
            String encodePwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), password, customer.getSaltVal());
            if (encodePwd.equals(customer.getCustomerPassword())) {
                return 1;
            }
            return 0;
        }
        return 0;
    }

    /**
     * 修改用户密码
     *
     * @param allInfo 用户信息
     * @return
     */
    @Override
    public int updateByPrimaryKey(CustomerAllInfo allInfo) {
        // 修改用户密码
        return customerMapper.updateByPrimaryKeySelective(allInfo);
    }

    /**
     * 修改会员详细信息
     *
     * @param user
     * @return
     */
    @Override
    public int updateCustInfoByPrimaryKey(CustomerAllInfo user) {
        // 修改会员详细信息
        return customerMapper.updateCustInfoByPrimaryKeySelective(user);
    }

    /**
     * 根据用户编号查找用户的收货地址
     *
     * @param customerId 用户编号
     * @return
     */
    @Override
    public CustomerAllInfo queryAddressByCustomerId(Long customerId) {
        // 根据用户编号查找用户的收货地址
        return customerMapper.queryAddressByCustomerId(customerId);
    }

    /**
     * 根据用户编号查找用户的收货地址
     *
     * @param pb         分页条件
     * @param customerId 用户编号
     * @return PageBean
     */
    @Override
    public PageBean queryAddressByCustomerId(Long customerId, PageBean pb) {
        // 封装查询条件
        Map<String, Object> map = new HashMap<>();
        // 根据用户编号查找用户的收货地址的数量
        Long count = customerMapper.queryAddressCountByCustomerId(customerId);
        pb.setRows(Integer.parseInt(count == null ? "0" : count.toString()));
        if (pb.getPageNo() > pb.getLastPageNo()) {
            pb.setPageNo(pb.getLastPageNo());
        }
        if (pb.getPageNo() == 0) {
            pb.setPageNo(1);
        }
        map.put(CUSTOMERID, customerId);
        map.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        map.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 根据用户编号查找用户的收货地址
        pb.setList(customerMapper.queryAddressByCustomerId(map));
        return pb;
    }

    /**
     * 添加收货地址
     *
     * @param address 地址信息
     * @return
     */
    @Override
    public int addCustomerAddress(CustomerAddress address) {
        // 添加收货地址
        return customerMapper.addCustomerAddress(address);
    }

    /**
     * 删除收货地址
     *
     * @param addressId 地址编号
     * @return
     */
    @Override
    public int deleteCustAddress(Long addressId) {
        // 删除收货地址
        return customerMapper.deleteCustAddress(addressId);
    }

    /**
     * 根据地址编号查找收货地址
     *
     * @param addressId 地址编号
     * @return
     */
    @Override
    public CustomerAddress queryCustAddress(Long addressId) {
        // 根据地址编号查找收货地址
        return customerMapper.queryCustAddress(addressId);
    }

    /**
     * 修改会员收货地址
     *
     * @param address 新地址信息
     * @return
     */
    @Override
    public int modifyCustAddress(CustomerAddress address) {
        // 修改会员收货地址
        return customerMapper.modifyCustAddress(address);
    }

    /**
     * 修改默认地址 将之前的默认地址改为非默认 并且将当前地址改为默认地址
     *
     * @param request
     * @param customerId 会员编号
     * @param addressId  地址编号 要设置为默认的编号
     * @return
     */
    @Override
    public int modifyIsDefaultAddress(HttpServletRequest request, String customerId, String addressId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            // 取消当前用户的所有默认地址
            if (customerMapper.cancelDefaultAddress(customerId) >= 1) {
                // 根据地址编号查找收货地址
                CustomerAddress address = customerMapper.queryCustAddress(Long.parseLong(addressId));
                if (address != null) {
                    // 设置收货地址
                    // 区id
                    request.getSession().setAttribute("distinctId", address.getDistrict().getDistrictId());
                    // 省
                    request.getSession().setAttribute("chProvince", address.getProvince().getProvinceName());
                    // 市
                    request.getSession().setAttribute("chCity", address.getCity().getCityName());
                    // 地址
                    request.getSession().setAttribute("chDistinct", address.getDistrict().getDistrictName());

                    request.getSession().setAttribute("chAddress", address.getProvince().getProvinceName()+address.getCity().getCityName()+address.getDistrict().getDistrictName());
                }
                // 封装参数
                paramMap.put("addressId", addressId);
                paramMap.put(CustomerConstantStr.CUSTOMERID, customerId);
                // 设置新的默认地址
                customerMapper.setDefaultAddress(paramMap);
                return 1;
            }
        } finally {
            paramMap = null;
        }
        return 0;
    }

    /**
     * 根据会员编号查询相应的会员积分明细
     *
     * @param paramMap 查询条件
     * @param pb
     * @return
     */
    @Override
    public PageBean selectAllCustomerPoint(Map<String, Object> paramMap, PageBean pb) {
        pb.setRows(Integer.parseInt(customerMapper.queryPointRcCount(paramMap) + ""));
        if (pb.getPageNo() > pb.getLastPageNo()) {
            pb.setPageNo(pb.getLastPageNo());
        }
        if (pb.getPageNo() == 0) {
            pb.setPageNo(1);
        }
        paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 查询所有积分记录
        pb.setList(customerMapper.queryAllPointRc(paramMap));
        return pb;
    }

    /**
     * 根据会员编号查询相应的总会员积分
     *
     * @param customerId 会员编号
     * @return
     */
    @Override
    public Long selectTotalPointByCid(Long customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put(CustomerConstantStr.CUSTOMERID, customerId);
            // 查询积分总和
            return customerMapper.selectTotalPointByCid(paramMap);
        } finally {
            paramMap = null;
        }
    }

    /**
     * 根据会员编号查询相应的会员收藏
     *
     * @param paramMap 查询条件
     * @param pb
     * @return
     */
    @Override
    public PageBean selectAllCustomerFollow(Map<String, Object> paramMap, PageBean pb) {
        pb.setRows(Integer.parseInt(customerMapper.queryFollowRcCount(paramMap) + ""));
        paramMap.put(CustomerConstantStr.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pb.getEndRowNum());
        // 查询所有收藏记录
        pb.setList(customerMapper.queryAllFollowRc(paramMap));
        return pb;
    }

    /**
     * 查询通知内容数量
     *
     * @param customerId 会员编号
     * @return
     */
    @Override
    public Map<String, Object> selectNotice(Long customerId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put(CustomerConstantStr.CUSTOMERID, customerId);
            // 待付款订单数量
            paramMap.put("flag", 3);
            resultMap.put("onpayNum", customerMapper.selectpendingOrderNotice(paramMap));
            // 前台待处理订单数量（待付款，待发货，待收货3种）
            paramMap.put("flag", 6);
            resultMap.put("onDealNum", customerMapper.selectpendingOrderNotice(paramMap));
            // 待收货 订单数量
            paramMap.put("flag", 4);
            resultMap.put("onReceiptNum", customerMapper.selectpendingOrderNotice(paramMap));
            // 待自提订单数量
            paramMap.put("flag", 5);
            resultMap.put("onMyNum", customerMapper.selectpendingOrderNotice(paramMap));
            // 待处理订单数量
            paramMap.put("flag", 0);
            resultMap.put("pendingNum", customerMapper.selectpendingOrderNotice(paramMap));
            // 待评论订单数量
            paramMap.put("flag", 1);
            // 查询待评论的订单
            resultMap.put("commentNum", customerMapper.selectpendingOrderNotice(paramMap));
            // 查询降价通知数量
            resultMap.put("reduceNum", customerMapper.selectReducePriceNum(paramMap));
            // 查询到货通知的数量
            resultMap.put("goodsArriveNum", customerMapper.selectGoodsArriveNum(paramMap));
            // 查询收藏总数
            Long count = customerFollowMapper.selectCustomerFollowCount(customerId);
            resultMap.put("activityGoodsNum", count);
            // 查询未读的咨询数量
            resultMap.put("noReadNum", customerMapper.selectNoReadNum(paramMap));
            // 查询为查看的信息数量
            resultMap.put("noReadInsideNum", insideletter.findInsideCount(customerId));
            return resultMap;
        } finally {
            resultMap = null;
            paramMap = null;
        }
    }

    /**
     * 取消订单
     *
     * @param orderId 订单Id
     * @param reason
     * @return
     */
    @Override
    public int cancelOrder(Long orderId, String reason) {
        // 执行返回的结果
        int result = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put(ConstantUtil.ORDERID, orderId);
            paramMap.put("reason", reason);
            // 取消订单
            result = customerMapper.cancelOrder(paramMap);
            if (result == 1) {
                Order order = orderMapper.orderDetail(orderId);
                Long customerId = order.getCustomerId();
                Double orderPrice = order.getOrderPrice().doubleValue();
                //第三方商家退货没有积分返还
                if(order.getBusinessId()==0){
                    if(order.getOrderIntegral() != null && order.getOrderIntegral().intValue() != 0 ){
                        CustomerPoint cusPoint = new CustomerPoint();
                        cusPoint.setPointDetail("订单取消返还订单使用积分");
                        cusPoint.setPoint(order.getOrderIntegral().intValue());
                        cusPoint.setPointType("1");
                        cusPoint.setDelFlag("0");
                        cusPoint.setCreateTime(new Date());
                        cusPoint.setCustomerId(order.getCustomerId());
                        // 扣除后的积分保存到数据库
                        customerPointMapper.insertSelective(cusPoint);
                        CustomerInfo info = customerInfoMapper.selectCustInfoById(customerId);
                        // 当前会员总积分
                        int allpoint = info.getInfoPointSum();
                        if(null != order.getOrderIntegral()){
                            allpoint+=order.getOrderIntegral().intValue();
                        }
                        info.setInfoPointSum(allpoint);
                        info.setCustomerId(order.getCustomerId());
                        result=customerInfoMapper.updateInfoByCustId(info);
                    }

                }

                // 添加取消订单后的消费记录
//                CustomerConsume cc;
//
//                cc = new CustomerConsume();
//                // 从订单中取出付款方式1.在线支付2.货到付款
//                if (order.getPayId() == 1) {
//                    cc.setPayType("1");
//                }
//                if (order.getPayId() == 2) {
//                    cc.setPayType("2");
//                }
//                cc.setCustomerId(customerId);
//                cc.setBalanceNum(BigDecimal.valueOf(orderPrice));
//                cc.setBalanceRemark("订单取消消费减少");
//                cc.setBalanceType("3");
//                cc.setCreateTime(new Date());
//                cc.setDelFlag("0");
//                cc.setOrderNo(order.getOrderCode());
//                customerConsumeMapper.insertSelective(cc);

            }
            return result;
        } finally {
            paramMap = null;
        }

    }

    /**
     * 删除订单
     *
     * @param orderId 订单Id
     * @return
     */
    @Override
    public int delOrder(Long orderId) {
        // 删除订单
        return customerMapper.delOrder(orderId);
    }

    /**
     * 修改密码 邮箱 手机
     *
     * @param request
     * @param newStr  新字段
     * @param type    pwd email mobile
     * @return
     */
    @Override
    public int modifyPem(HttpServletRequest request, String newStr, String type) {
        Object cId = request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        CustomerAllInfo cust = new CustomerAllInfo();
        cust.setCustomerId((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));
        if ("pwd".equals(type)) {
            Customer customer = customerMapper.queryLoginInfoByCustomerId((Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID));
            //生成新的盐值
            String saltVal = SecurityUtil.getNewPsw();
            //uniqueCode
            String uniqueCode = customer.getUniqueCode();
            //加密password
            String encodePwd = SecurityUtil.getStoreLogpwd(uniqueCode, newStr, saltVal);
            //设置customer修改信息
            Customer loginInfo = new Customer();
            loginInfo.setCustomerId(customer.getCustomerId());
            loginInfo.setUniqueCode(uniqueCode);
            loginInfo.setCustomerPassword(encodePwd);
            loginInfo.setSaltVal(saltVal);
            // 按条件修改会员信息
            return customerMapper.updatePwdInfo(loginInfo);
        } else if ("mobile".equals(type)) {
            cust.setIsMobile("1");
            cust.setInfoMobile(newStr);
            // 按照主键编号查找 基本信息
            Customer custs = customerMapper.selectByPrimaryKey((Long) cId);
        } else if ("email".equals(type)) {
            // cust.setIsEmail("1");
            cust.setInfoMobile(newStr);
        }
        // 按条件修改会员信息
        customerMapper.updateByPrimaryKeySelective(cust);
        // 修改会员信息
        return customerMapper.modifyCustomerInfo(cust);
    }

    /**
     * 发送邮件
     *
     * @param request
     * @param email   邮件地址
     * @return
     */
    @Override
    public int sendEamil(HttpServletRequest request, String email) {
        return 0;
    }

    /**
     * 发送手机验证码
     *
     * @param request
     * @param moblie  目标手机
     * @return
     */
    @Override
    public int sendPost(HttpServletRequest request, String moblie) {
        Customer customer = null;
        // 查询短信信息
        Sms sms = mapper.selectSms();
        if (sms == null) {
            return 0;
        }
        Object cId = request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (cId == null) {
            cId = request.getSession().getAttribute("uId");
        }
        Object custEmp = null;
        // 查询验证码和失效时间
        customer = customerMapper.selectCaptcha((Long) cId);
        if ((System.currentTimeMillis() - customer.getAeadTime().getTime()) > (60 * 1000)) {

            sms.setSendSim(moblie);
            int num = (int) ((Math.random() * 9 + 1) * 100000);
            // 手机验证码
            request.getSession().setAttribute("codeMobileNum", num);
            sms.setMsgContext(((Integer) num).toString());
            custEmp = request.getSession().getAttribute("cust");
            if (custEmp == null) {
                custEmp = request.getSession().getAttribute("user");
            }
            customer = (Customer) custEmp;
            customer.setCaptcha(((Integer) num).toString());
            customer.setAeadTime(new Date());
            try {
                // 短信发送
                if (SmsPost.sendPost(sms)) {
                    // 修改验证码
                    customerMapper.updateSmsCaptcha(customer);
                    return 1;
                }
                return 0;
            } catch (IOException e) {
                LOGGER.error("", e);
                return 0;
            }
        }
        return -1;
    }
    /**
     * 发送手机验证码
     *
     * @param request
     * @param moblie  目标手机
     * @return
     */
    @Override
    public int newsendPost(HttpServletRequest request, String moblie) {
        Sms sms = mapper.selectSms();
        if (sms == null) {
            return 0;
        }
        sms.setSendSim(moblie);
        int num = (int) ((Math.random() * 9 + 1) * 100000);
        request.getSession().setAttribute("mcCode", num);
        request.getSession().setAttribute("userMobile", moblie);
        sms.setMsgContext(((Integer) num).toString());
        try {
            if (SmsPost.sendPost(sms)) {
                return 1;
            }
            return 0;
        } catch (IOException e) {
            LOGGER.error(""+e);
            return 0;
        }
    }

    /**
     * 修改找回密码Code
     *
     * @param user
     * @return
     */
    @Override
    public int updateFindPwdCode(CustomerAllInfo user) {
        // 修改验证码
        return customerMapper.updateSmsCaptcha(user);
    }

    /**
     * 验证手机验证码
     *
     * @param request
     * @param code
     * @return
     */
    @Override
    public int getMCode(HttpServletRequest request, String code) {

        if (code == null) {
            return -2;
        }

        Object cId = request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (cId == null) {
            cId = request.getSession().getAttribute("uId");
        }
        // 查询验证码和失效时间
        Customer customer = customerMapper.selectCaptcha((Long) cId);
        if ((System.currentTimeMillis() - customer.getAeadTime().getTime()) > 30 * 60 * 1000) {
            return -1;
        }
        if(!code.equals(customer.getCaptcha())){
            //验证码错误超过6次则清空，前台需重新获取验证码
            if(request.getSession().getAttribute("submitCount") == null){
                request.getSession().setAttribute("submitCount", 1);
            }else{
                request.getSession().setAttribute("submitCount", (int)request.getSession().getAttribute("submitCount")+1);
                int submitCount = (int)request.getSession().getAttribute("submitCount");
                if(submitCount > 5){
                    request.getSession().setAttribute("submitCount", null);
                    customer.setAeadTime(new Date(customer.getAeadTime().getTime() - (30 * 60 * 1000)));
                    customer.setCaptcha(null);
                    customer.setCustomerId((Long) cId);
                    // 修改验证码
                    customerMapper.updateSmsCaptcha(customer);
                    //验证码失效
                    return -1;
                }else{
                    //验证码错误
                    return 0;
                }
            }
        }else{
            customer.setAeadTime(new Date(customer.getAeadTime().getTime() - (11 * 60 * 1000)));
            customer.setCaptcha(null);
            customer.setCustomerId((Long) cId);
            // 修改验证码
            customerMapper.updateSmsCaptcha(customer);
            // 按照主键编号查找 基本信息
            Customer cust = customerMapper.selectByPrimaryKey((Long) cId);
            request.getSession().setAttribute("cFlag", 1);
            return 1;
        }
        return 0;
    }

    /**
     * 验证邮件
     *
     * @param request
     * @param checkCode
     * @param d
     * @return
     */
    @Override
    public int validPwdEmail(HttpServletRequest request, String checkCode, Long d) {
        // 查询验证码和失效时间
        Customer customer = customerMapper.selectCaptcha(d);
        if (customer != null && customer.getPwdCaptcha() != null) {
            if ((System.currentTimeMillis() - customer.getPwdAeadTime().getTime()) > 120 * 60 * 1000) {
                return -1;
            }
            if (checkCode.equals(customer.getPwdCaptcha())) {
                CustomerAllInfo cust = this.selectCustomerByUname(customer.getCustomerUsername());
                request.getSession().setAttribute("user", cust);
                request.getSession().setAttribute("uId", cust.getCustomerId());
                customer.setPwdAeadTime(new Date(customer.getPwdAeadTime().getTime() - (120 * 60 * 1000)));
                customer.setPwdCaptcha("");
                customer.setCustomerId(d);
                // 修改验证码
                customerMapper.updateSmsCaptcha(customer);
                request.getSession().setAttribute("cFlag", 1);
                return 1;
            }
            return -2;
        } else {
            return -2;
        }
    }

    /**
     * 确认收货
     *
     * @param orderId 订单编号
     * @return
     */
    @Override
    public int comfirmofGoods(Long orderId) {
        // 确认收货
        return customerMapper.comfirmOfGooods(orderId);
    }

    /**
     * 检查用户名存在性
     *
     * @param username 用户名
     * @return
     */
    @Override
    public Long checkUsernameFlag(String username) {
        // 检查用户名存在性
        return customerMapper.checkexistsByCustName(username);
    }

    /**
     * 根据用户名查询用户简单信息
     *
     * @param username 用户名
     * @return
     */
    @Override
    public CustomerAllInfo selectCustomerByUname(String username) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            paramMap.put("username", username);
            // 根据用户名查询用户简单信息
            return customerMapperSite.selectCustomerByUname(paramMap);
        } finally {
            paramMap = null;
        }
    }

    /**
     * 验证邮箱存在性
     *
     * @param email 目标邮箱 {@link String}
     * @return
     */
    @Override
    public Long checkEmailExist(String email) {
        // 验证邮箱存在性
        return customerMapper.checkEmailExist(email);
    }

    /**
     * 验证手机存在性
     *
     * @param mobile 目标手机 {@link String}
     * @return
     */
    @Override
    public Long checkMobileExist(String mobile) {
        // 验证手机存在性
        return customerMapper.checkMobileExist(mobile);
    }

    public CustomerMapperSite getCustomerMapperSite() {
        return customerMapperSite;
    }

    @Resource(name = "customerMapperFindPwd")
    public void setCustomerMapperSite(CustomerMapperSite customerMapperSite) {
        this.customerMapperSite = customerMapperSite;
    }

    public SmsMapper getMapper() {
        return mapper;
    }

    @Resource(name = "smsMapperSite")
    public void setMapper(SmsMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 获取customerMaper
     *
     * @return CustomerMapper
     */
    public CustomerMapper getCustomerMapper() {
        return customerMapper;
    }

    /**
     * 设置属性 spring注入
     *
     * @param customerMapper
     */
    @Resource(name = "customerMapperSite")
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public CustomerInfoMapper getCustomerInfoMapper() {
        return customerInfoMapper;
    }

    @Resource(name = "customerInfoMapper")
    public void setCustomerInfoMapper(CustomerInfoMapper customerInfoMapper) {
        this.customerInfoMapper = customerInfoMapper;
    }

    public CustomerPointServiceMapper getCustomerPointServiceMapper() {
        return customerPointServiceMapper;
    }

    @Resource(name = "customerPointServiceMapper")
    public void setCustomerPointServiceMapper(CustomerPointServiceMapper customerPointServiceMapper) {
        this.customerPointServiceMapper = customerPointServiceMapper;
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    @Resource(name = "OrderMapper")
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public CouponService getCouponService() {
        return couponService;
    }

    @Resource(name = "CouponService")
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public DistrictBean selectDistrictBeanById(Long did) {
        return customerMapper.selectDistrictBeanById(did);
    }

    @Override
    public ProvinceBean selectprovinceByPid(Long provinceId) {
        return customerMapper.selectprovinceByPid(provinceId);
    }

    @Override
    public SystemsSet getIsBackOrder() {
        return customerMapper.getIsBackOrder();
    }

    public CustomerPointMapper getCustomerPointMapper() {
        return customerPointMapper;
    }

    @Resource(name = "customerPointMapper")
    public void setCustomerPointMapper(CustomerPointMapper customerPointMapper) {
        this.customerPointMapper = customerPointMapper;
    }

    /**
     * 验证会员是否存在某订单
     *
     * @param customerId
     * @param orderCode
     * @return
     */
    @Override
    public Long checkexistsByIdAndCode(Long customerId, String orderCode) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(CUSTOMERID, customerId);
        paramMap.put("orderCode", orderCode);
        // 验证会员是否存在某订单
        return customerMapper.checkexistsByIdAndCode(paramMap);
    }

    /**
     * 修改密码信息
     *
     * @param customerId 会员ID
     * @param password   密码
     * @return
     */
    @Override
    public int updatePwdInfo(Long customerId, String password) {
        Customer customer = customerMapper.queryLoginInfoByCustomerId(customerId);
        //生成盐值
        String saltval = SecurityUtil.getNewPsw();
        //生成新的加密后的登陆密码
        String logpwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), password, saltval);
        //设置新的盐值,密码
        customer.setSaltVal(saltval);
        customer.setCustomerPassword(logpwd);
        return customerMapper.updatePwdInfo(customer);
    }

    /**
     * 查询会员等级表信息
     */
    @Override
    public List<CustomerPointLevel> queryPointLevel() {
        return this.customerMapper.queryPointLevel();
    }

    /**
     * 判断用户当前是否可以新增地址
     *
     * @param customerId 用户id
     * @return false:不可以 true:可以
     */
    @Override
    public boolean isCustomerAddressNumLegal(Long customerId) {
        //用户是否添加标识
        boolean flag = false;
        //查询用户已添加的地址数目
        Long addsNum = this.customerMapper.queryAddressCountByCustomerId(customerId);
        if (addsNum<10)
            flag = true;
        return flag;
    }

    /**
     * 根据用户id和地址id查询用户地址信息
     *
     * @param addressId 地址id
     * @return 用户地址
     */
    @Override
    public CustomerAddress selectByAddrIdAndCusId(Long customerId, Long addressId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerId", customerId);
        map.put("addressId", addressId);
        return this.customerMapper.selectByAddrIdAndCusId(map);
    }
}
