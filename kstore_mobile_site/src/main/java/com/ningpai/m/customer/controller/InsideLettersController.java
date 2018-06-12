/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ningpai.customer.service.InsideLetterServiceMapper;
import com.ningpai.customer.vo.InsideLetterVo;
import com.ningpai.m.customer.service.OrderNoticeService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.util.PageBean;

/**
 * 站内信控制器
 * 
 * @Description 站内信控制器
 * @author Songhl
 * @since 2015年8月27日 20:42:21
 */
@Controller
public class InsideLettersController {

    /**
     * spring注解
     */
    private InsideLetterServiceMapper insideLetterService;
    
    @Resource(name="orderNoticeServiceM")
    private OrderNoticeService orderNoticeService;

    /**
     * 跳转到站内信，管理员
     *
     * @param request
     * @return
     */
    @RequestMapping("/adminnotice")
    public ModelAndView showInsideLetter(HttpServletRequest request, PageBean pb) {
        ModelAndView mav = null;
        pb.setUrl("/adminnotice");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            // 验证登录
            if (checkLoginStatus(request)) {
                resultMap.put(CustomerConstants.RECEIVE_CUSTOMERID, (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID));
                // 查询全部站内信
                mav = new ModelAndView("member/notice1").addObject("pb", insideLetterService.queryInsideLetters(resultMap));

            } else {
                mav = new ModelAndView("redirect:/loginm.html").addObject("url", "/adminnotice.html");
            }
            return mav;
        } finally {
            mav = null;
        }
    }

    /**
     * 标记为已读
     *
     * @param request
     * @return
     */
    @RequestMapping("/readletterm")
    @ResponseBody
    public int readedLetter(HttpServletRequest request, @Valid InsideLetterVo inside) {
        // 当前登录会员id
        Long customerId = (Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID);
        if (customerId != null) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("customerId", customerId);
            resultMap.put("letterId", inside.getLetterId());
            // 是否已读
            int isRead = Integer.parseInt(insideLetterService.letterIsRead(resultMap) + "");
            //如果未读的才标记为已读
            if (isRead == 0) {
                // 用户编号
                inside.setCustomerId(customerId);
                // 标记为已读
                return insideLetterService.readedLetter(inside);
            }
        }
        return 0;
    }

    /**
     * 跳转到消息，系统通知
     *
     * @param request
     * @return
     */
    @RequestMapping("/ordernotice")
    public ModelAndView ordernotice(HttpServletRequest request, PageBean pb) {
        ModelAndView mav = null;
        pb.setUrl("/ordernotice");
        try {
            // 验证登录
            if (checkLoginStatus(request)) {
                // 查询全部订单通知
                mav = new ModelAndView("member/notice2").addObject("pb", orderNoticeService.selectOrderNotices((Long) request.getSession().getAttribute(CustomerConstants.CUSTOMERID)));

            } else {
                mav = new ModelAndView("redirect:/loginm.html").addObject("url", "/ordernotice.htm");
            }
            return mav;
        } finally {
            mav = null;
        }
    }

    /**
     * 系统通知消息标记为已读
     *
     * @param request
     * @return
     */
    @RequestMapping("/readnotice")
    @ResponseBody
    public Object readnotice(HttpServletRequest request, Long noticeId) {
        // 验证登录
        if (checkLoginStatus(request)) {
            // 查询全部订单通知
            return this.orderNoticeService.readNotice(noticeId);

        } else {
            return "/loginm.html?url=/ordernotice.htm";
        }
    }
    
    
    /**
     * 检查是否登录
     * 
     * @param request
     *            请求
     * @return
     */
    private boolean checkLoginStatus(HttpServletRequest request) {
        if (request.getSession().getAttribute("cust") == null) {
            return false;
        }
        return true;
    }

    public InsideLetterServiceMapper getInsideLetterService() {
        return insideLetterService;
    }

    @Resource(name = "insideLetterServiceMapper")
    public void setInsideLetterService(InsideLetterServiceMapper insideLetterService) {
        this.insideLetterService = insideLetterService;
    }
}
