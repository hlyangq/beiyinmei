/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.order.controller;

import com.ningpai.customer.bean.CustomerAddress;
import com.ningpai.m.common.service.SeoService;
import com.ningpai.m.customer.service.CustomerAddressService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.m.order.util.OrderURL;
import com.ningpai.m.util.LoginUtil;
import com.ningpai.system.service.DeliveryPointService;
import com.ningpai.system.service.impl.ExpressConfBizImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 订单收货地址
 * 
 * @author NINGPAI-LIH
 * @since 2014年9月4日15:15:03
 * 
 */
@Controller
public class OrderAddressController {

    private static final String ADDRESSID = "addressId";
    private static final String FLAG = "flag";
    private static final String ADDRESSLIST_HTML = "redirect:/order/addresslist.html";

    @Resource(name = "expressConfBizImpl")
    private ExpressConfBizImpl expressConfBizImpl;

    @Resource(name = "SeoService")
    private SeoService seoService;

    @Resource(name = "customerAddressServiceM")
    private CustomerAddressService addressService;

    // 自提点
    @Resource(name = "DeliveryPointService")
    private DeliveryPointService deliveryPointService;

    /**
     * 查收货地址
     * 
     * @param request
     * @return
     */
    @RequestMapping("/addresslist")
    public ModelAndView queryOrderAddressList(HttpServletRequest request,String flag) {
        // 判断用户是否登录
        if (LoginUtil.checkLoginStatus(request)) {
            // 地址列表
            List<Object> addresses = addressService.queryCustomerAddress((Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
            //跳转订单提交页的地址列表
            if (flag != null && "1".equals(flag)) {
                return seoService.getCurrSeo(new ModelAndView(CustomerConstants.ORDERLIST).addObject("addresses", addresses));
            }
            return seoService.getCurrSeo(new ModelAndView(CustomerConstants.ADDRESSLIST).addObject("addresses", addresses));
        } else {
            return new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
    }

    /**
     * 更改订单收货地址
     * 
     * @param request
     * @param addressId
     * @return
     */
    @RequestMapping("/changeAddress")
    public ModelAndView changeAddress(HttpServletRequest request, Long addressId) {
        // 用户编号
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        if (customerId != null) {
            // 创建容器存储要修改的地址信息
            CustomerAddress address = new CustomerAddress();
            address.setAddressId(addressId);
            address.setCustomerId(customerId);
            // 1为使用
            address.setIsDefault("1");
            // 将所有的地址都更改为默认状态0
            addressService.updateAddressDef(customerId);
            // 修改指定地址为使用
            addressService.updateAddress(address, customerId);
            // 判断返回地址
            if (request.getSession().getAttribute("box") == null) {
                // 返回index
                return new ModelAndView(new RedirectView("customercenter.html"));
            }
            // 订单
            return new ModelAndView(new RedirectView("suborder.htm?addressId=" + addressId));
        } else {
            return new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX);
        }
    }

    /**
     * 根据地区id查询自提点列表
     * 
     * @param districtId
     *            地区id
     * @param cityId
     *            城市id
     * @param provinceId
     *            省市id
     * @return
     */
    @RequestMapping("/querydelivery")
    public ModelAndView queryDelivery(Long districtId, Long cityId, Long provinceId, Long[] box) {
        ModelAndView mav = new ModelAndView("/order/instead_address");
        // 查询自提点列表
        mav.addObject("delivery", deliveryPointService.selectDeliveryPointByDistrictIdForSite(districtId));
        // 地区id
        mav.addObject("districtId", districtId);
        // 城市id
        mav.addObject("cityId", cityId);
        // 省Id
        mav.addObject("Province", provinceId);
        mav.addObject("box", box);
        return seoService.getCurrSeo(mav);
    }

    /**
     * 返回到添加地址页面
     * 
     * @return
     */
    @RequestMapping("toAddAddress")
    public ModelAndView toAddAddress(String flag) {
        return seoService.getCurrSeo(new ModelAndView("order/my_account_address_add").addObject(FLAG,flag));
    }

    /**
     * 查询所有的配送方式
     * 
     * @return 查询到的配送方式列表
     */
    @RequestMapping("/queryAllExpress")
    @ResponseBody
    public Map<String, Object> queryAllExpress() {
        return this.expressConfBizImpl.queryAllExpress();
    }

    /**
     * 根据地址编号查找收货地址
     * 
     * @param addressId
     * @return {@link ModelAndView}
     */
    @RequestMapping("/orderfilladdress")
    public ModelAndView orderFillAddress(HttpServletRequest request, Long addressId, Long[] box) {
        if (LoginUtil.checkLoginStatus(request)) {
            if (addressId == null) {
                return seoService.getCurrSeo(new ModelAndView(OrderURL.FILLADDRESS).addObject("box", box)).addObject(ADDRESSID, 0);
            } else {
                return seoService.getCurrSeo(new ModelAndView(OrderURL.FILLADDRESS).addObject("addr",
                        addressService.queryCustomerAddressById(addressId, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID))).addObject("box", box));
            }

        } else {
            return seoService.getCurrSeo(new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX));
        }
    }

    /**
     * 修改收货地址
     * 
     * @return {@link ModelAndView}
     */
    @RequestMapping("/orderupdateaddress")
    public ModelAndView orderUpdateAddress(HttpServletRequest request, CustomerAddress address, Long[] box,String flag) {
        if (LoginUtil.checkLoginStatus(request)) {
          //如果新添加地址设置为默认了，就把用户之前添加的地址设置为不是默认
            if (("1").equals(address.getIsDefault())) {
                // 地址列表
                List<Object> addresses = addressService.queryCustomerAddress((Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
                CustomerAddress addr = new CustomerAddress();
                for (Object object : addresses) {
                    addr = (CustomerAddress) object;
                    addr.setIsDefault("0");
                    addressService.updateAddress(addr, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
                } 
            }
            //修改地址
            addressService.updateAddress(address, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
            return new ModelAndView(ADDRESSLIST_HTML).addObject("box", box).addObject(ADDRESSID, address.getAddressId()).addObject(FLAG,flag);
        } else {
            return seoService.getCurrSeo(new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX));
        }
    }

    /**
     * 去修改地址
     * 
     * @return
     */
    @RequestMapping("toupdateAddress")
    public ModelAndView toupdateAddress(HttpServletRequest request, Long addressId,String flag) {
        // 获得用户Id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        // 判断是否登录
        if (customerId != null) {
            CustomerAddress address = addressService.queryCustomerAddressById(addressId, customerId);
            ModelAndView mav = new ModelAndView("order/my_account_address_edit").addObject("orderaddress", address).addObject(FLAG,flag);
            return seoService.getCurrSeo(mav);
        } else {
            // 去登录
            return seoService.getCurrSeo(new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX).addObject("url","/customercenter.html"));
        }
    }

    /**
     * 删除
     * 
     * @param addressId
     * @return
     */
    @RequestMapping("/delAddress")
    public ModelAndView delAddress(HttpServletRequest request, Long addressId,String flag) {
        // 获得用户Id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        // 判断是否登录
        if (customerId != null) {
            // 创建容器
            CustomerAddress address = new CustomerAddress();
            // 地址Id
            address.setAddressId(addressId);
            // 用户Id
            address.setCustomerId(customerId);
            // 1为删除状态
            address.setDelFlag("1");
            // 修改地址状态 为删除
            addressService.updateAddress(address, customerId);
            return new ModelAndView(ADDRESSLIST_HTML).addObject(FLAG,flag);
        } else {
            // 去登录
            return seoService.getCurrSeo(new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX));
        }
    }

    /**
     * 添加收货地址
     * 
     * @return {@link ModelAndView}
     */
    @RequestMapping("/orderaddaddress")
    public ModelAndView orderAddAddress(HttpServletRequest request, CustomerAddress address, Long[] box, Long addressId,String flag) {
        if (LoginUtil.checkLoginStatus(request)) {
            //如果新添加地址设置为默认了，就把用户之前添加的地址设置为不是默认
            if (("1").equals(address.getIsDefault())) {
             // 地址列表
                List<Object> addresses = addressService.queryCustomerAddress((Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
                CustomerAddress addr = new CustomerAddress();
                for (Object object : addresses) {
                    addr = (CustomerAddress) object;
                    addr.setIsDefault("0");
                    addressService.updateAddress(addr, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
                }
            }
            addressService.addAddress(address, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
            return new ModelAndView(ADDRESSLIST_HTML).addObject("box", box).addObject(ADDRESSID, addressId).addObject(FLAG,flag);
        } else {
            return seoService.getCurrSeo(new ModelAndView(CustomerConstants.REDIRECTLOGINTOINDEX));
        }
    }

    /**
     * 根据地址查询百度地图
     * 
     * @param address
     * @return
     */
    @RequestMapping("/bdmap")
    public ModelAndView bdMap(String address) {
        ModelAndView mav = new ModelAndView("/order/map").addObject("ad", address);
        return seoService.getCurrSeo(mav);
    }

}
