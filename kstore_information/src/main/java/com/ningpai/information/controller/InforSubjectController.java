/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 *
 */
package com.ningpai.information.controller;

import com.ningpai.information.bean.InforSubject;
import com.ningpai.information.service.InforSubjectServcie;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.system.mobile.service.MobSiteBasicService;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器-资讯专题
 *
 * @author NINGPAI-WangHaiYang
 * @since 2014年7月24日上午10:58:19
 */
@Controller
public class InforSubjectController {

    /**
     * 记录日志对象
     */
    private static final MyLogger LOGGER = new MyLogger(InforSubjectController.class);

    public static final String NAME = "name";

    public static final String OPERAPATH = "operaPath";

    private static final String LOGINUSERID = "loginUserId";
    // 查询pc端专题列表
    private static final String QUERYINFORSUBJECTBYPAGEBEAN_HTM = "queryInforSubjectByPageBean.htm";
    // 查询手机端专题列表
    private static final String QUERYMOBILEINFORSUBJECTBYPAGEBEAN_HTM = "queryMobileInfoSubjectByPageBean.htm";
    private static final String LOGGERINFO1 = ",用户名:";

    @Resource(name = "basicSetService")
    private BasicSetService basicSetService;

    @Resource(name = "MobSiteBasicService")
    private MobSiteBasicService mobSiteBasicService;

    @Resource(name = "InforSubjectServcie")
    private InforSubjectServcie subjectService;

    /**
     * 查询PC端资讯专题分页数据
     */
    @RequestMapping(value = "/queryInforSubjectByPageBean")
    public ModelAndView queryInforSubjectByPageBean(PageBean pb, String titles) {
        return new ModelAndView("jsp/information/subject_list", "pb", subjectService.selectPCSubjectByPageBean(pb, titles)).addObject("siteAddress",
                this.basicSetService.findBasicSet().getBsetAddress()).addObject("titles", titles);
    }

    /**
     * 手机端 咨询专题页列表
     *
     * @param pb
     * @param titles
     * @param model
     * @return
     */
    @RequestMapping("/queryMobileInfoSubjectByPageBean")
    public String queryMobileInfoSubjectByPageBean(HttpServletRequest request, PageBean pb, String titles, Model model) {
        model.addAttribute("pb", subjectService.selectMobileSubjectByPageBean(pb, titles));
        model.addAttribute("titles", titles);
        // 拼接展示地址
        String siteAddress = basicSetService.findBasicSet().getBsetAddress();
        String mAddress = mobSiteBasicService.selectCurrMobSiteBasic(request).getSiteAddress();
        if (siteAddress != null && mAddress != null && siteAddress.endsWith("/") && mAddress.startsWith("/")) {
            siteAddress = siteAddress.substring(0, siteAddress.lastIndexOf("/"));
        }
        if (!mAddress.endsWith("/")) {
            mAddress += "/";
        }
        model.addAttribute("siteAddress", siteAddress + mAddress);
        return "jsp/mobile/m_subject_list";
    }

    /**
     * 查询手机端专题页列表  用于手机端首页配置
     * @param pb
     * @param title
     * @param request
     * @return
     */
    @RequestMapping("/ajaxQuerySubjectList")
    @ResponseBody
    public Map<String, Object> querySubjectList(PageBean pb, String title, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("pb", subjectService.selectSubjectByPageBean(pb, title, "0", "1"));
        result.put("title", title);
        // 拼接展示地址
        String siteAddress = basicSetService.findBasicSet().getBsetAddress();
        String mAddress = mobSiteBasicService.selectCurrMobSiteBasic(request).getSiteAddress();
        if (siteAddress != null && mAddress != null && siteAddress.endsWith("/") && mAddress.startsWith("/")) {
            siteAddress = siteAddress.substring(0, siteAddress.lastIndexOf("/"));
        }
        if (!mAddress.endsWith("/")) {
            mAddress += "/";
        }
        result.put("address", siteAddress + mAddress + "subject/");
        return result;
    }

    /**
     * 跳转到显示资讯专题
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/showInforSubject")
    public ModelAndView showInforSubject(Long subjectId) {
        ModelAndView mav = new ModelAndView();

        if (null != subjectId) {
            LOGGER.debug("==============================查看专题并跳转到查看或修改页面==============================");
            mav.addObject("subject", subjectService.getSubject(subjectId));
        }
        mav.addObject("siteAddress", this.basicSetService.findBasicSet().getBsetAddress());
        mav.setViewName("jsp/information/show_subject");
        return mav;
    }

    /**
     * 跳转到显示资讯专题
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/showinforsubjectajax")
    @ResponseBody
    public InforSubject showInforSubjectAjax(Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    /**
     * 添加PC资讯专题
     *
     * @return ModelAndView
     */
    @RequestMapping("/addInforSubject")
    public ModelAndView addPcInforSubject(MultipartHttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        LOGGER.debug("==============================添加PC专题==============================");
        // 标记为PC端的专题
        subject.setTemp2("1");
        // 新增专题操作
        addInforSubject(request, subject);
        // 返回结果
        return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 新增手机端专题页
     *
     * @param request
     * @param subject
     * @param bindingResult
     * @return
     */
    @RequestMapping("/addMobileInforSubject")
    public ModelAndView addMobileInforSubject(MultipartHttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        LOGGER.debug("==============================添加Mobile专题==============================");
        subject.setTemp2("0");
        // 新增专题操作
        addInforSubject(request, subject);
        // 返回结果
        return new ModelAndView(new RedirectView(QUERYMOBILEINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 添加专题页
     *
     * @param request
     * @param subject
     * @return
     */
    private void addInforSubject(MultipartHttpServletRequest request, InforSubject subject) {
        // 获取存储在session中的登录id
        Long loginUserId = (Long) request.getSession().getAttribute(LOGINUSERID);
        // 获取页面中name 为iamgeSrc的文件流
        MultipartFile muFile = request.getFile("imageSrc");
        /*
         * 判断文件流的size是否大于0
         */
        if (muFile.getSize() > 0) {
            subject.setBackgroundImg(UploadUtil.uploadFileOne(muFile, request));
        }
        try {
            /*
             * 判断登录id是否为null 为null就设置为1
             */
            if (null == loginUserId) {
                loginUserId = 1L;
            }
            // 设置创建者id
            subject.setCreateUserId(loginUserId);
            // 设置更新者id
            subject.setUpdateUserId(loginUserId);
            // 执行保存方法
            subjectService.saveSubject(subject);
            // 获取session中的用户名称
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录操作日志
            OperaLogUtil.addOperaLog(request, customerName, "添加专题", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (Exception e) {
            LOGGER.error("==============================添加专题失败：", e);
        }
    }

    /**
     * 添加PC资讯专题
     *
     * @return ModelAndView
     */
    @RequestMapping("/addinforsubjectnew")
    public ModelAndView addPCInforSubjectNew(HttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        LOGGER.debug("==============================添加PC专题==============================");
        // 标记为PC端的专题
        subject.setTemp2("1");
        addInforSubjectNew(request, subject);
        // 返回结果
        return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 添加手机端咨询专题
     * @param request
     * @param subject
     * @param bindingResult
     * @return
     */
    @RequestMapping("/addMobileinforsubjectnew")
    public ModelAndView addMobileInforSubjectNew(HttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        LOGGER.debug("==============================添加Mobile专题==============================");
        // 标记为Mobile端的专题
        subject.setTemp2("0");
        addInforSubjectNew(request, subject);
        // 返回结果
        return new ModelAndView(new RedirectView(QUERYMOBILEINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 添加专题页操作
     * @param request
     * @param subject
     */
    private void addInforSubjectNew(HttpServletRequest request, InforSubject subject) {
        // 获取登录id
        Long loginUserId = (Long) request.getSession().getAttribute(LOGINUSERID);
        // 设置创建人id
        subject.setCreateUserId(loginUserId);
        // 设置修改人id
        subject.setUpdateUserId(loginUserId);
        // 修改
        subjectService.saveSubject(subject);
        // 获取用户名
        String customerName = (String) request.getSession().getAttribute(NAME);
        // 记录日志
        OperaLogUtil.addOperaLog(request, customerName, "添加专题", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
    }

    /**
     * 修改资讯专题
     *
     * @return ModelAndView
     */
    @RequestMapping("/updateInforSubject")
    public ModelAndView updateInforSubject(MultipartHttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        // 获取存储在session中的登录id
        Long loginUserId = (Long) request.getSession().getAttribute(LOGINUSERID);
        LOGGER.debug("==============================修改专题==============================");
        // 获取页面中name 为imageSrc的文件流
        MultipartFile muFile = request.getFile("imageSrc");
        if (muFile.getSize() > 0) {
            subject.setBackgroundImg(UploadUtil.uploadFileOne(muFile, request));
        }
        try {
            // 判断登录id是否为null
            // 为null时设置为1
            if (null == loginUserId) {
                loginUserId = 1L;
            }
            // 设置登录id
            subject.setUpdateUserId(loginUserId);
            // 修改操作
            subjectService.updateSubject(subject);
            // 获取用户名
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录日志
            OperaLogUtil.addOperaLog(request, customerName, "修改专题", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (Exception e) {
            LOGGER.error("==============================修改专题失败：", e);
        }
        return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 修改资讯专题
     *
     * @return ModelAndView
     */
    @RequestMapping("/updateInforSubjectNew")
    public ModelAndView updatePCInforSubjectNew(HttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        // 判断是否符合规范
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        // 修改专题
        updateInfoSubjectNew(request, subject);
        return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 修改手机端专题页
     * @param request
     * @param subject
     * @param bindingResult
     * @return
     */
    @RequestMapping("/updateMobileInforSubjectNew")
    public ModelAndView updateMobileInfoSubjectNew(HttpServletRequest request, @Valid InforSubject subject, BindingResult bindingResult) {
        // 判断是否符合规范
        if (bindingResult.hasErrors()) {
            return new ModelAndView(new RedirectView(QUERYINFORSUBJECTBYPAGEBEAN_HTM));
        }
        // 修改专题
        updateInfoSubjectNew(request, subject);
        return new ModelAndView(new RedirectView(QUERYMOBILEINFORSUBJECTBYPAGEBEAN_HTM));
    }

    /**
     * 修改专题页
     * @param request
     * @param subject
     */
    private void updateInfoSubjectNew(HttpServletRequest request, InforSubject subject) {
        // 获取登录id
        Long loginUserId = (Long) request.getSession().getAttribute(LOGINUSERID);
        LOGGER.debug("==============================修改专题==============================");
        try {
            // 设置登录id
            subject.setUpdateUserId(loginUserId);
            // 修改操作
            subjectService.updateSubject(subject);
            // 获取用户名
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录日志
            OperaLogUtil.addOperaLog(request, customerName, "修改专题", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (Exception e) {
            LOGGER.error("==============================修改专题失败：", e);
        }
    }


    /**
     * 删除资讯专题
     *
     * @return ModelAndView
     */
    @RequestMapping("/delInforSubject")
    public void delInforSubject(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("==============================删除专题==============================");
        try {
            // 声明一个数组来接受页面的数据
            String[] ids = request.getParameterValues("inforSubjectIds[]");
            Long[] sids = new Long[ids.length];
            // 循环数据并赋值给另一个数组
            for (int i = 0; i < ids.length; i++) {
                sids[i] = Long.valueOf(ids[i]);
            }
            // 执行批量删除方法
            subjectService.batchDeleteSubject(sids);
            // 获取存储在session中的用户名称
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录操作日志
            OperaLogUtil.addOperaLog(request, customerName, "删除专题", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (NumberFormatException e) {
            LOGGER.error("==============================删除专题失败：", e);
        }
    }

    /**
     * 删除资讯专题
     *
     * @return ModelAndView
     */
    @RequestMapping("/delinforsubjectnew")
    public void delPCInforSubjectNew(HttpServletRequest request, HttpServletResponse response) {
        // 记录日志
        LOGGER.debug("==============================删除PC专题==============================");
        delInfoSubjectNew(request, response);
    }

    /**
     * 删除手机端专题
     * @param request
     * @param response
     */
    @RequestMapping("/delMobileInfoSubjectNew")
    public void delMobileInfoSubjectNew(HttpServletRequest request, HttpServletResponse response) {
        // 记录日志
        LOGGER.debug("==============================删除Mobile专题==============================");
        delInfoSubjectNew(request, response);
    }

    /**
     * 删除专题页
     * @param request
     * @param response
     */
    private void delInfoSubjectNew(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取修改id
            String[] ids = request.getParameterValues("inforSubjectIds");
            // 获取长度
            Long[] sids = new Long[ids.length];
            // 赋值
            for (int i = 0; i < ids.length; i++) {
                sids[i] = Long.valueOf(ids[i]);
            }
            // 进行删除操作
            subjectService.batchDeleteSubject(sids);
            // 获取用户名
            String customerName = (String) request.getSession().getAttribute(NAME);
            // 记录日志
            OperaLogUtil.addOperaLog(request, customerName, "删除专题", request.getSession().getAttribute(OPERAPATH) + LOGGERINFO1 + customerName);
        } catch (NumberFormatException e) {
            // 记录日志
            LOGGER.error("==============================删除专题失败：", e);
        }
    }

    /**
     * Ajax验证url的唯一性
     *
     * @param url
     */
    @ResponseBody
    @RequestMapping("/checkSubjectByUrl")
    public boolean checkSubjectByUrl(String url, Long subjectId) {
        if (null != subjectId) {
            String subUrl = this.subjectService.getSubject(subjectId).getUrl();
            if (subUrl != null && "".equals(subUrl) && subUrl.equals(url)) {
                return true;
            }
        }
        return this.subjectService.checkByUrl(url);
    }
}
