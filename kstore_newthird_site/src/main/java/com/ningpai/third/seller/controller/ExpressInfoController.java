/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.seller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.third.logger.util.OperateLogUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.ningpai.third.seller.bean.Express;
import com.ningpai.third.seller.service.ExpressInfoService;
import com.ningpai.third.seller.util.SellerValueUtil;
import com.ningpai.third.util.MenuOperationUtil;

/**
 * <p>
 * 物流信息控制类
 * </p>
 * 
 * @author Zhouh
 * @since 2014.5.21
 * @version 2.0
 * 
 */
@Controller
public class ExpressInfoController {

    private static final Logger LOGGER = Logger.getLogger(ExpressInfoController.class);

    private static final String THIRDID = "thirdId";

    // 物流支持Service
    private ExpressInfoService expressInfoService;

    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    /**
     * 
     * 查询物流信息
     * 
     * @param request
     * @param n
     *            导航栏索引头部
     * @param l
     *            导航栏索引左侧
     * @return
     */
    @RequestMapping("third/selectexpress")
    public ModelAndView selectExpress(HttpServletRequest request, String n, String l) {
        // 填充导航/左侧菜单索引 用于页面控制css选中
        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 创建一个物流公司对象
        Express express = new Express();
        // 设置店铺ID
        express.setStoreId((Long) request.getSession().getAttribute(THIRDID));
        // 根据店铺查询物流信息
        resultMap.put("express", expressInfoService.selectByStoreIds(express));
        // 根据店铺查询物流信息
        resultMap.put("boolcot", expressInfoService.selectDefaultRows((Long) request.getSession().getAttribute(THIRDID)));
        // 设置要跳转的页面路径 装载需要返回到页面的数据
        return new ModelAndView("seller/expressinfo").addAllObjects(resultMap);
    }

    /**
     * 验证无路编号是否存在
     * 
     * @param expCompany
     * @return
     */
    @RequestMapping("third/checkExpressNo")
    @ResponseBody
    public int checkExpressNo(HttpServletRequest request, String expCompany, Long shoreExpId) {
        // 保存需要返回到页面到值
        int result = 0;
        // 创建一个物流对象
        if (null != expCompany) {
            Express ex = expressInfoService.selectByshoreExpId(shoreExpId);
            if (null != ex && expCompany.equals(ex.getExpCompany())) {
                return result;
            } else {
                // 设置物流的商铺ID
                Express exs = new Express();
                // 设置商家ID
                exs.setStoreId((Long) request.getSession().getAttribute(THIRDID));
                // 获取当前店铺所有的物流信息
                List<Express> expres = expressInfoService.selectByStoreIds(exs);
                if (!expres.isEmpty() && expres.size() > 0) {
                    // 遍历所有的物流公司
                    for (int i = 0; i < expres.size(); i++) {
                        Express exp = expres.get(i);
                        // 判断新增的物流单号是否已经存在
                        if (expCompany.equals(exp.getExpCompany())) {
                            result++;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 判断物流公司名称是否已经存在
     * @param request HttpServletRequest对象
     * @param expressName 物流公司名称
     * @return 0不存在，>0存在
     */
    @RequestMapping("third/checkExpressName")
    @ResponseBody
    public int checkExpressName(HttpServletRequest request, String expressName) {
        // 保存需要返回到页面到值
        int result = 0;
        // 创建一个物流对象
        if (null != expressName) {
            Express exs = new Express();
            // 设置商家ID
            exs.setStoreId((Long) request.getSession().getAttribute(THIRDID));
            // 获取当前店铺所有的物流信息
            List<Express> expres = expressInfoService.selectByStoreIds(exs);
            if (!expres.isEmpty() && expres.size() > 0) {
                // 遍历所有的物流公司
                for (int i = 0; i < expres.size(); i++) {
                    Express exp = expres.get(i);
                    // 判断新增的物流名称是否已经存在
                    if (expressName.equals(exp.getExpName())) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 跳转添加物流
     * 
     * @return
     */
    @RequestMapping("third/addexpressinfo")
    public ModelAndView toAddEmp() {
        return new ModelAndView("seller/addexpress");
    }

    /**
     * 新增物流
     * 
     * @param request
     * @param express
     * @return
     */
    @RequestMapping("third/insertexpress")
    public ModelAndView addExpress(HttpServletRequest request, Express express) {
        expressInfoService.insertExpress(request, express);
        //添加操作日志
        if(request.getSession().getAttribute("customerId") != null){
            //操作员工信息（当前登录的员工）
            CustomerAllInfo customerAllInfo = customerServiceMapper.selectByPrimaryKey((Long) request.getSession().getAttribute("customerId"));
            if(customerAllInfo != null && customerAllInfo.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "商家新增物流", "商家新增物流，物流名称：" + express.getExpName() + "-->用户名：" + customerAllInfo.getCustomerUsername());
                LOGGER.info("商家新增物流");
            }
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + SellerValueUtil.EXPRESSLIST));
    }

    /**
     * 删除物流 修改flag标记
     * 
     * @param request
     * @param delflag
     *            =1
     * @return
     */
    @RequestMapping("third/deleteexpress")
    public ModelAndView deleteExpress(HttpServletRequest request, Express delflag) {
        Express before = expressInfoService.selectByshoreExpId(delflag.getShoreExpId());
        delflag.setStoreId((Long) request.getSession().getAttribute(THIRDID));
        expressInfoService.deleteByShoreExpIdUpdate(delflag);
        //添加操作日志
        if(request.getSession().getAttribute("customerId") != null){
            //操作员工信息（当前登录的员工）
            CustomerAllInfo customerAllInfo = customerServiceMapper.selectByPrimaryKey((Long) request.getSession().getAttribute("customerId"));
            if(customerAllInfo != null && customerAllInfo.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "删除商家物流", "删除商家物流，物流名称：" + before.getExpName() + "-->用户名：" + customerAllInfo.getCustomerUsername());
                LOGGER.info("删除商家物流");
            }
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + SellerValueUtil.EXPRESSLIST));
    }

    /**
     * 修改物流 修改state标记
     * 
     * @param request
     * @param express
     *            =0 to 1
     * @return
     */
    @RequestMapping("third/updatedefaultstate")
    public ModelAndView updateExpressToState(HttpServletRequest request, Express express) {
        //物流详情
        Express before = expressInfoService.selectByshoreExpId(express.getShoreExpId());
        express.setStoreId((Long) request.getSession().getAttribute(THIRDID));
        expressInfoService.updateStateByShoreExpId(express);
        //添加操作日志
        if(request.getSession().getAttribute("customerId") != null){
            //操作员工信息（当前登录的员工）
            CustomerAllInfo customerAllInfo = customerServiceMapper.selectByPrimaryKey((Long) request.getSession().getAttribute("customerId"));
            if(customerAllInfo != null && customerAllInfo.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "开启商家物流", "开启商家物流，物流名称：" + before.getExpName() + "-->用户名：" + customerAllInfo.getCustomerUsername());
                LOGGER.info("开启商家物流");
            }
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + SellerValueUtil.EXPRESSLIST));
    }

    /**
     * 修改物流 修改state标记
     * 
     * @param request
     * @param express
     *            =1 to 0
     * @return
     */
    @RequestMapping("third/updatebackstate")
    public ModelAndView updateExpressToStateBack(HttpServletRequest request, Express express) {
        //物流详情
        Express before = expressInfoService.selectByshoreExpId(express.getShoreExpId());
        express.setStoreId((Long) request.getSession().getAttribute(THIRDID));
        expressInfoService.updateStateBackByShoreExpId(express);
        //添加操作日志
        if(request.getSession().getAttribute("customerId") != null){
            //操作员工信息（当前登录的员工）
            CustomerAllInfo customerAllInfo = customerServiceMapper.selectByPrimaryKey((Long) request.getSession().getAttribute("customerId"));
            if(customerAllInfo != null && customerAllInfo.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "关闭商家物流", "关闭商家物流，物流名称：" + before.getExpName() + "-->用户名：" + customerAllInfo.getCustomerUsername());
                LOGGER.info("关闭商家物流");
            }
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + SellerValueUtil.EXPRESSLIST));
    }

    /**
     * 跳转修改物流
     * 
     * @return
     */
    @RequestMapping("third/updateexpress")
    @ResponseBody
    public Express toUpdateEmp(Long shoreExpId) {
        return expressInfoService.selectByshoreExpId(shoreExpId);
    }

    /**
     * 修改具体物流信息
     * 
     * @param request
     * @param express
     * @return
     */
    @RequestMapping("third/updatedoneexpress")
    public ModelAndView updateExpress(HttpServletRequest request, Express express) {
        //修改之前的物流信息
        Express before = expressInfoService.selectByshoreExpId(express.getShoreExpId());
        expressInfoService.updateExpress(request, express);
        //添加操作日志
        if(request.getSession().getAttribute("customerId") != null){
            //操作员工信息（当前登录的员工）
            CustomerAllInfo customerAllInfo = customerServiceMapper.selectByPrimaryKey((Long) request.getSession().getAttribute("customerId"));
            if(customerAllInfo != null && customerAllInfo.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "修改商家物流信息", "修改商家物流信息，物流名称：" + before.getExpName() + "-->用户名：" + customerAllInfo.getCustomerUsername());
                LOGGER.info("修改商家物流信息");
            }
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + SellerValueUtil.EXPRESSLIST));
    }

    public ExpressInfoService getExpressInfoService() {
        return expressInfoService;
    }

    @Resource(name = "expressInfoService")
    public void setExpressInfoService(ExpressInfoService expressInfoService) {
        this.expressInfoService = expressInfoService;
    }

}
