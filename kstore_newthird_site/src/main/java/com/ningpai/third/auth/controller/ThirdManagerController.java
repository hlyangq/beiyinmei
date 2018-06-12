/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.other.bean.CustomerAllInfo;
import com.ningpai.third.logger.util.OperateLogUtil;
import com.ningpai.util.MyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ningpai.third.auth.bean.ThirdAuthority;
import com.ningpai.third.auth.bean.ThirdPage;
import com.ningpai.third.auth.service.ThirdAuthorityPageService;
import com.ningpai.third.auth.service.ThirdAuthorityService;

/**
 * <p>
 * 第三方角色Controller
 * </p>
 * 
 * @author zhanghl
 * @since 20150730
 * @version 2.0
 */
@Controller
public class ThirdManagerController {
    private static final MyLogger LOGGER = new MyLogger(ThirdManagerController.class);

    // 第三方商家权限页面Service 接口
    private ThirdAuthorityPageService thirdManagerService;
    // 商家权限sevice接口
    @Resource(name = "thirdAuthorityService")
    private ThirdAuthorityService thirdAuthorityService;
    /**
     * spring 注解 会员service
     */
    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    /**
     * 查询用户权限页面
     * 
     * @param request
     * @return List<ThirdPage> 权限页面
     */
    @RequestMapping("/loadthirdpage")
    @ResponseBody
    public List<ThirdPage> loadThirdPage(HttpServletRequest request, Long cid) {
        return thirdManagerService.queryMenuByManager(cid);
    }

    /**
     * 查询所有权限页面
     * 
     * @param request
     * @return List<ThirdPage> 权限页面
     */
    @RequestMapping("third/loadallauthority")
    @ResponseBody
    public Map<String, Object> loadAllAuthority(HttpServletRequest request, Long pid) {
        // 状态查询条件
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 查找商家权限页面列表
        resultMap.put("pages", thirdManagerService.loadAllAuthority());
        // 根据权限编号查找权限页面
        resultMap.put("rolePages", thirdManagerService.queryThirdPageByAuthId(pid));
        return resultMap;
    }

    /**
     * 修改角色权限
     * 
     * @param request
     * @param pagesId
     *            包含页面Id的字符串
     * @param authId
     *            角色编号
     * @return
     */
    @RequestMapping("/updateauthority")
    @ResponseBody
    public String updateAuthority(HttpServletRequest request, String[] pagesId, Long authId) {
        Long thirdId = (Long) request.getSession().getAttribute("thirdId");
        // 查询该角色是否属于这个店铺
        // 根据主键获取单个的职位对象
        ThirdAuthority authority = thirdAuthorityService.selectAuthorById(authId);
        if (authority != null) {
            // 判断职位是否为空
            if (thirdId.equals(authority.getStoreId())) {
                // 根据条件修改 角色权限
                thirdManagerService.updateAuthority(request, pagesId, authId);
                if(request.getSession().getAttribute("customerId") != null){
                    CustomerAllInfo customerAllInfo = customerServiceMapper.selectByPrimaryKey((Long) request.getSession().getAttribute("customerId"));
                    if(customerAllInfo != null && customerAllInfo.getCustomerUsername() != null){
                        // 操作日志
                        OperateLogUtil.addOperaLog(request, customerAllInfo.getCustomerUsername(), "修改职位权限", "修改职位："+authority.getDesignation()+"的权限-->用户名：" + customerAllInfo.getCustomerUsername());
                        LOGGER.info("修改职位权限");
                    }
                }
                return "";
            } else {
                return "-1";
            }
        } else {
            return "-1";
        }
        /*
         * thirdManagerService.updateAuthority(request, pagesId, authId); return
         * "";
         */
    }

    public ThirdAuthorityPageService getThirdManagerService() {
        return thirdManagerService;
    }

    @Resource(name = "thirdAuthorityPageService")
    public void setThirdManagerService(ThirdAuthorityPageService thirdManagerService) {
        this.thirdManagerService = thirdManagerService;
    }

}
