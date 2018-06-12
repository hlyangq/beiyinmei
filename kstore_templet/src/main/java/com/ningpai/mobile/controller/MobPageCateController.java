/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 *
 */
package com.ningpai.mobile.controller;

import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.mobile.bean.MobPageCate;
import com.ningpai.mobile.service.MobPageCateServcie;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器-页面分类
 *
 * @author NINGPAI-Daiyitian
 * @since 2014年7月24日上午10:58:19
 */
@Controller
public class MobPageCateController {

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(MobPageCateController.class);

    public static final String NAME = "name";

    public static final String OPERAPATH = "operaPath";

    private static final String LOGINUSERID = "loginUserId";
    // 查询pc端专题列表
    private static final String QUERYMOBPAGECATEBYPAGEBEAN_HTM = "queryMobPageCateByPageBean.htm";
    private static final String LOGGERINFO1 = ",用户名:";

    @Resource(name = "MobPageCateServcie")
    private MobPageCateServcie mobPageCateServcie;

    /**
     * 移动端-页面分类列表
     * @param pb
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("/queryMobPageCateByPageBean")
    public String queryMobPageCateByPageBean(PageBean pb, String name, Model model) {
        Map<String,Object> params = new HashMap<>();
        params.put("delflag","0");
        params.put("name",name);
        model.addAttribute("pb", mobPageCateServcie.selectByPageBean(pb, params));
        model.addAttribute("sname", name);
        return "jsp/mobile/mob_page_cate";
    }

    /**
     * 查询移动端-页面分类列表，用于手机端首页配置
     * @param pb
     * @param name
     * @return
     */
    @RequestMapping("/ajaxQueryMobPageCateList")
    @ResponseBody
    public Map<String, Object> ajaxQueryMobPageCateList(String name) {
        Map<String,Object> params = new HashMap<>();
        params.put("delflag","0");
        params.put("name",name);
        Map<String, Object> result = new HashMap<>();
        result.put("list", mobPageCateServcie.selectAll(params));
        result.put("sname", name);
        return result;
    }

    /**
     * 跳转到移动端-页面分类展示页面
     * @param pageCateId
     * @return
     */
    @RequestMapping("/ajaxShowMobPageCate")
    @ResponseBody
    public MobPageCate ajaxShowMobPageCate(Long pageCateId) {
        return mobPageCateServcie.getPageCate(pageCateId);
    }

    /**
     * 添加页面分类
     *
     * @return ModelAndView
     */
    @RequestMapping("/addMobPageCate")
    public ModelAndView addMobPageCate(HttpServletRequest request, @Valid MobPageCate  mobPageCate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYMOBPAGECATEBYPAGEBEAN_HTM));
        }
        LOGGER.debug("==============================添加页面分类专题==============================");
        // 新增页面分类操作
        // 获取存储在session中的登录id
        Long loginUserId = (Long) request.getSession().getAttribute(LOGINUSERID);
        try {
            if (null == loginUserId) {
                loginUserId = 1L;
            }
            mobPageCate.setCreateUserId(loginUserId);
            mobPageCate.setUpdateUserId(loginUserId);
            this.mobPageCateServcie.savePageCate(mobPageCate);
            // 获取session中的用户名称
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录操作日志
            OperaLogUtil.addOperaLog(request, customerName, "添加移动端页面分类", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (Exception e) {
            LOGGER.error("==============================添加移动端页面分类失败：", e);
        }
        // 返回结果
        return new ModelAndView(new RedirectView(QUERYMOBPAGECATEBYPAGEBEAN_HTM));
    }

    /**
     * 修改页面分类
     *
     * @return ModelAndView
     */
    @RequestMapping("/updateMobPageCate")
    public ModelAndView updateMobPageCate(HttpServletRequest request, @Valid MobPageCate  mobPageCate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYMOBPAGECATEBYPAGEBEAN_HTM));
        }
        LOGGER.debug("==============================添加页面分类专题==============================");
        // 新增页面分类操作
        // 获取存储在session中的登录id
        Long loginUserId = (Long) request.getSession().getAttribute(LOGINUSERID);
        try {
            if (null == loginUserId) {
                loginUserId = 1L;
            }
            mobPageCate.setUpdateUserId(loginUserId);
            this.mobPageCateServcie.updatePageCate(mobPageCate);
            // 获取session中的用户名称
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录操作日志
            OperaLogUtil.addOperaLog(request, customerName, "修改移动端页面分类", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (Exception e) {
            LOGGER.error("==============================修改移动端页面分类失败：", e);
        }
        // 返回结果
        return new ModelAndView(new RedirectView(QUERYMOBPAGECATEBYPAGEBEAN_HTM));
    }


    /**
     * 删除页面分类
     */
    @RequestMapping("/delMobPageCate")
    @ResponseBody
    public Map<String, String> delMobPageCate(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("==============================删除页面分类==============================");
        int res = 0;
        try {
            // 声明一个数组来接受页面的数据
            String[] ids = request.getParameterValues("ids");
            Long[] sids = new Long[ids.length];
            for (int i = 0; i < ids.length; i++) {
                sids[i] = Long.valueOf(ids[i]);
            }
            res = this.mobPageCateServcie.delete(sids);
            // 记录操作日志
            String customerName = (String) request.getSession().getAttribute(NAME);
            OperaLogUtil.addOperaLog(request, customerName, "删除页面分类", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (Exception e) {
            LOGGER.error("==============================删除页面分类失败：", e);
        }

        Map<String,String> resMap = new HashMap<>();
        resMap.put("res",String.valueOf(res));
        return resMap;
    }

}
