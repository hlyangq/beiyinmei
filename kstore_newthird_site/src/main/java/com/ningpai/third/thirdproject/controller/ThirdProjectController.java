package com.ningpai.third.thirdproject.controller;

/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ningpai.customer.bean.Customer;
import com.ningpai.third.goods.util.ThirdValueBean;
import com.ningpai.third.logger.util.OperateLogUtil;
import com.ningpai.util.MyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.ningpai.third.util.MenuOperationUtil;
import com.ningpai.thirdproject.bean.ThirdProject;
import com.ningpai.thirdproject.service.ThirdProjectService;
import com.ningpai.util.PageBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方专题管理controller
 * 
 * @author zhangsl
 * @since 2015年1月15日 上午10:53:21
 * @version
 */
@Controller
public class ThirdProjectController {
    private static final MyLogger LOGGER = new MyLogger(ThirdProjectController.class);

    private static final String THIRDID = "thirdId";
    private static final String QUERYPROJECTLIST_HTM = "queryProjectList.htm";

    @Resource(name = "ThirdProjectService")
    private ThirdProjectService thirdProjectService;

    /**
     * 分页查询专题信息
     * 
     * @param request
     * @param pageBean
     * @return
     */
    @RequestMapping("queryProjectList")
    public ModelAndView queryProjectList(HttpServletRequest request, PageBean pageBean, ThirdProject thirdProject, String n, String l) {
        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        Long thirdId = (Long) request.getSession().getAttribute(THIRDID);
        thirdProject.setThirdId(thirdId);
        return new ModelAndView("project/thirdproject").addObject("pageBean", thirdProjectService.queryThirdProjectByPage(pageBean, thirdProject));
    }

    /**
     * 去添加页面
     * 
     * @return
     */
    @RequestMapping("toAddThirdProject")
    public ModelAndView toAddThirdProject() {
        return new ModelAndView("project/addthirdproject");
    }

    /**
     * 添加专题信息
     * 
     * @param request
     * @param thirdProject
     * @return
     */
    @RequestMapping("addThirdProject")
    public ModelAndView addThirdProject(HttpServletRequest request, ThirdProject thirdProject) {
        Long thirdId = (Long) request.getSession().getAttribute(THIRDID);
        thirdProject.setThirdId(thirdId);
        thirdProjectService.addThirdProject(thirdProject);
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "添加商家专题", "添加商家专题，专题名称【" + thirdProject.getThirdProjectName() + "】-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("添加商家专题");
            }
        }
        return new ModelAndView(new RedirectView(QUERYPROJECTLIST_HTM));
    }

    /**
     * 删除专题信息
     * 
     * @param thirdProjectId
     * @return
     */
    @RequestMapping("deleteThirdProject")
    public ModelAndView deleteThirdProject(HttpServletRequest request, Long thirdProjectId) {
        ThirdProject thirdProject = thirdProjectService.selectProjectById(thirdProjectId);
        Map<String, Object> map = new HashMap<>();
        map.put("thirdProjectId", thirdProjectId);
        map.put(THIRDID, request.getSession().getAttribute(THIRDID));
        thirdProjectService.updateDelflagstatu(map);
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "删除商家专题", "删除商家专题，专题名称【" + thirdProject.getThirdProjectName() + "】-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("删除商家专题");
            }
        }
        return new ModelAndView(new RedirectView(QUERYPROJECTLIST_HTM));
    }

    /**
     * 去修改页面
     * 
     * @param thirdProjectId
     * @return
     */
    @RequestMapping("toUpdateThirdProject")
    public ModelAndView toUpdateThirdProject(Long thirdProjectId) {
        return new ModelAndView("project/updatethirdproject").addObject("thirdProject", thirdProjectService.selectProjectById(thirdProjectId));

    }

    /**
     * 根据ID查询专题信息
     * 
     * @param thirdProjectId
     *            专题ID
     * @return 专题信息
     */
    @RequestMapping("/getProjectInfoById")
    @ResponseBody
    public ThirdProject getProjectInfoById(Long thirdProjectId) {
        return thirdProjectService.selectProjectById(thirdProjectId);
    }

    /**
     * 修改专题信息
     * 
     * @param thirdProject
     * @return
     */
    @RequestMapping("updateThirdProject")
    public ModelAndView updateThirdProject(HttpServletRequest request, ThirdProject thirdProject) {
        thirdProject.setThirdId((Long) request.getSession().getAttribute(THIRDID));
        thirdProjectService.updateThirdProject(thirdProject);
        //添加操作日志
        if(request.getSession().getAttribute(ThirdValueBean.CUST) != null){
            Customer cust = (Customer) request.getSession().getAttribute(ThirdValueBean.CUST);
            if(cust.getCustomerUsername() != null){
                // 操作日志
                OperateLogUtil.addOperaLog(request, cust.getCustomerUsername(), "修改商家专题", "修改商家专题，新专题名称【" + thirdProject.getThirdProjectName() + "】-->用户名：" + cust.getCustomerUsername());
                LOGGER.info("修改商家专题");
            }
        }
        return new ModelAndView(new RedirectView(QUERYPROJECTLIST_HTM));
    }
}
