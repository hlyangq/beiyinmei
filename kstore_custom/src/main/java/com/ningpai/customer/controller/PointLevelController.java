/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.customer.controller;

import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerInfoMapper;
import com.ningpai.customer.service.CustomerPointServiceMapper;
import com.ningpai.customer.service.PointLevelServiceMapper;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.util.PageBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 会员等级控制层
 * 
 * @author NINGPAI-zhangqiang
 * @since 2013年12月19日 下午4:05:44
 * @version 1.0
 */
@Controller("pointLevelController")
public class PointLevelController {

    private static final String REDIRECT = "initPointLevel.htm";

    // spring注入
    private PointLevelServiceMapper pointLevelServiceMapper;

    @Resource(name="customerInfoMapper")
    private CustomerInfoMapper customerInfoMapper;

    @Resource(name="customerPointServiceMapper")
    private CustomerPointServiceMapper customerPointServiceMapper;

    // 记录日志对象
    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(PointLevelController.class);
    }

    /**
     * 初始化会员等级列表
     * 
     * @param pageBean
     *            {@link com.ningpai.util.PageBean}
     * @return
     */
    @RequestMapping("/initPointLevel")
    public ModelAndView initPointLevel(PageBean pageBean) {
        // 设置页面跳转路径
        pageBean.setUrl(REDIRECT);
        return new ModelAndView("jsp/customer/customerlevel", "pageBean", pointLevelServiceMapper.selectAllPointLevel(pageBean));
    }

    /**
     * 添加会员等级
     * 
     * @param customerPointLevel
     *            {@link com.ningpai.customer.bean.CustomerPointLevel}
     * @return
     */
    @RequestMapping("/addPointLevel")
    public ModelAndView addPointLevel(@Valid CustomerPointLevel customerPointLevel, String pointNeed1) {
        // 非空验证 会员等级名称
        if (null != customerPointLevel.getPointLevelName()) {
            // 日志记录
            LOGGER.info("添加会员等级名称为：" + customerPointLevel.getPointLevelName());
        }
        customerPointLevel.setPointNeed(customerPointLevel.getPointNeed() + "~" + pointNeed1);
        // 添加会员等级
        pointLevelServiceMapper.addPointLevel(customerPointLevel);
        LOGGER.debug(CustomerConstantStr.ADDPOINTLEVEL);
        return new ModelAndView(new RedirectView(CustomerConstantStr.INITPOINTLEVEL));
    }


    /**
     * 验证积分等级
     * @param pointLelvelId
     * @param pointLevelName
     * @param pointDiscount
     * @param pointNeed
     * @param pointNeed1
     * @author chendaiming
     * @return  int 0,成功   1，开始积分必须小于结束积分   2。会员名称不能相同   3，开始积分范围错误，不可设置  33，结束积分范围错误，不可设置 4，折扣添加有误
     */
    @RequestMapping("/validatePointLevel")
    @ResponseBody
    public int validatePointLevel(String pointLelvelId,String pointLevelName,String pointDiscount,Long pointNeed,Long pointNeed1){
        int result = 0;
        boolean isBreak = false;
        Long pointStart = pointNeed;
        Long pointEnd = pointNeed1;
        if(pointStart >= pointEnd){
            result = 1; //开始积分必须小于结束积分
        }else{
            List<CustomerPointLevel> pointLevelList = pointLevelServiceMapper.selectAllPointLevel();
            if(pointLevelList != null && !pointLevelList.isEmpty()) {
                for (CustomerPointLevel customerPointLevel : pointLevelList) {
                    if(!pointLelvelId.equals(customerPointLevel.getPointLelvelId().toString())){
                        if(pointLevelName != null && pointLevelName.equals(customerPointLevel.getPointLevelName())){
                            result = 2;  //会员名称不能相同
                            isBreak = true;
                            break;
                        }

                        String[] pointArrays = customerPointLevel.getPointNeed().split("~");
                        for (int i = 0; i < pointArrays.length; i++) {
                            if(pointStart >= Long.valueOf(pointArrays[0]) && pointStart < Long.valueOf(pointArrays[1])){
                                result = 3; //开始积分发生交集，不可设置
                                isBreak = true;
                                break;
                            }
                            if(pointEnd > Long.valueOf(pointArrays[0]) && pointEnd <= Long.valueOf(pointArrays[1])){
                                result = 33; //结束积分发生交集，不可设置
                                isBreak = true;
                                break;
                            }
                            BigDecimal b1 = new BigDecimal(pointDiscount);
                            BigDecimal b2 = customerPointLevel.getPointDiscount();
                            //添加的开始积分高于已经存在的积分
                            if(pointStart >= Long.valueOf(pointArrays[1])){
                                //添加的折扣大于已经存在的折扣
                                if(b1.compareTo(b2) == 1 || b1.compareTo(b2) == 0){
                                    result = 4; //折扣添加有误
                                    isBreak = true;
                                    break;
                                }
                            }else{
                                //添加的折扣大于已经存在的折扣
                                if(b1.compareTo(b2) == -1 || b1.compareTo(b2) == 0){
                                    result = 4; //折扣添加有误
                                    isBreak = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(isBreak){
                        break;
                    }
                }
            }
        }
        return result;
    }


    /**
     * 查询会员等级 jason 填充修改弹框显示
     * 
     * @param pointLevelId
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/queryPointLevelById")
    @ResponseBody
    public CustomerPointLevel queryPointLevelById(Long pointLevelId) {
        // 获取单个会员等级对象
        CustomerPointLevel customerPointLevel = pointLevelServiceMapper.selectPointLevelById(pointLevelId);
        // 非空验证 会员等级名称
        if (null != customerPointLevel.getPointLevelName()) {
            LOGGER.info("获取会员等级名称为：" + customerPointLevel.getPointLevelName() + "的信息");
        }
        return pointLevelServiceMapper.selectPointLevelById(pointLevelId);
    }

    /**
     * 修改会员等级
     * 
     * @param customerPointLevel
     *            {@link com.ningpai.customer.bean.CustomerPointLevel}
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/updatePointLevel")
    public ModelAndView updatePointLevel(@Valid CustomerPointLevel customerPointLevel, String pointNeed1) throws UnsupportedEncodingException {
        // 非空验证 会员等级名称
        if (null != customerPointLevel.getPointLevelName()) {
            LOGGER.info("修改会员等级名称为：" + customerPointLevel.getPointLevelName() + "的信息");
        }
        // 修改会员等级
        customerPointLevel.setPointNeed(customerPointLevel.getPointNeed() + "~" + pointNeed1);
        pointLevelServiceMapper.updatePointLevel(customerPointLevel);
        LOGGER.debug(CustomerConstantStr.UPDATEPOINTLEVEL + customerPointLevel.getPointLevelName());
        return new ModelAndView(new RedirectView(CustomerConstantStr.INITPOINTLEVEL));
    }

    /**
     * 删除会员等级
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/deletePointLevel")
    public ModelAndView deletePointLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
            // 删除会员等级 1 成功 0 失败
            pr.print(pointLevelServiceMapper.deletePointLevel(request.getParameterValues("parameterIds[]")));
        } finally {
            LOGGER.debug(CustomerConstantStr.DELPOINTLEVEL);
            pr = null;
        }
        return null;
    }

    /**
     * 删除会员等级
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/deleteNewPointLevel")
    public ModelAndView deleteNewPointLevel(HttpServletRequest request, HttpServletResponse response, Long pointLevelId) throws IOException {
        // 删除会员等级 1 成功 0 失败
        String[] pointLevelIds = new String[] { "" + pointLevelId + "" };
        pointLevelServiceMapper.deletePointLevel(pointLevelIds);
        return new ModelAndView(new RedirectView(REDIRECT));
    }

    /**
     * 新批量删除会员等级
     * 
     * @param request
     * @param pointLevelId
     * @return ModelAndView
     */
    @RequestMapping("/deleteAllNewPointLevel")
    public ModelAndView deleteAllNewCustomer(HttpServletRequest request, Long[] pointLevelId) {
        // 获取会员ID封装String数组
        String[] pointLevelIds = request.getParameterValues("pointLevelId");
        // 执行删除
        pointLevelServiceMapper.deletePointLevel(pointLevelIds);
        // 返回当前列表页面
        return new ModelAndView(new RedirectView(REDIRECT));
    }



    @RequestMapping("/deletePointLevelValidate")
    @ResponseBody
    public int deletePointLevelValidate(HttpServletRequest request){
        int result = 0;
        // 获取会员ID封装String数组
        String[] pointLevelIds = request.getParameterValues("pointLevelId");
        if(pointLevelIds !=null && pointLevelIds.length > 0) {
            for (String pointLevelId : pointLevelIds) {
                result = customerInfoMapper.queryInfoCountByLevelId(Long.valueOf(pointLevelId));
                if (result > 0) {
                    break;
                }
            }
        }else{
            result = -1;
        }
        return result;
    }

    /**
     * 检查等级名称是否存在
     * 
     * @param pointLevelName
     *            等级名称
     * @param response
     * @return 1 存在 0 不存在
     * @throws IOException
     */
    @RequestMapping(value = "/checkExistPointLevelName", method = RequestMethod.POST)
    public ModelAndView checkExistPointLevelName(String pointLevelName, HttpServletResponse response) throws IOException {
        // 非空验证 会员等级名称
        if (null != pointLevelName) {
            // 日志记录
            LOGGER.info("验证会员等级名称：" + pointLevelName + "是否存在！");
        }
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
            // 检查会员等级是否存在 1 存在 0 不存在
            pr.print(pointLevelServiceMapper.selectPointLevelByName(pointLevelName));
        } finally {
            pr = null;
        }
        return null;
    }

    /**
     * 初始化会员等级列表 -- 添加会员时加载等级列表
     * 
     * @param pageBean
     *            {@link com.ningpai.util.PageBean}
     * @return
     */
    @RequestMapping("/getPointLevel")
    @ResponseBody
    public List<CustomerPointLevel> getPointLevel(PageBean pageBean) {
        return pointLevelServiceMapper.selectAllPointLevel();
    }

    /**
     * 检查等级默认是否存在
     * 
     * @param response
     * @return 1 存在 0 不存在
     * @throws IOException
     */
    @RequestMapping("/checkExistDefaultPointLevel")
    public ModelAndView checkExistDefaultPointLevel(HttpServletResponse response) throws IOException {
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
            // 检查等级默认是否存在 1 存在 0 不存在
            pr.print(pointLevelServiceMapper.selectDefaultPointLevel());
        } finally {
            pr = null;
        }
        return null;
    }

    @RequestMapping("/getmaxpoint")
    @ResponseBody
    public String getMaxPoint() {
        return customerPointServiceMapper.getMaxCustomerPointLevelPoint() + "";
    }


    @RequestMapping("/deleteallcustomerpointlevel")
    @ResponseBody
    public int deleteAllCustomerPointLevel() {
        return  customerPointServiceMapper.deleteAllCustomerPointLevel();
    }

    public PointLevelServiceMapper getPointLevelServiceMapper() {
        return pointLevelServiceMapper;
    }

    // spring注解
    @Resource(name = "pointLevelServiceMapper")
    public void setPointLevelServiceMapper(PointLevelServiceMapper pointLevelServiceMapper) {
        this.pointLevelServiceMapper = pointLevelServiceMapper;
    }


}
