/**
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.logger.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.third.logger.bean.OperationLog;
import com.ningpai.third.logger.service.OperateLogService;
import com.ningpai.third.logger.util.ConstantLogUtil;
import com.ningpai.third.util.MenuOperationUtil;
import com.ningpai.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * 日志操作Controller
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年6月27日 上午10:15:26
 * @version 0.0.1
 */
@Controller
public class OperateLogController {
    // 属性注入
    @Resource(name = "operateLogService")
    private OperateLogService operaLogService;
    public static final String INITOPERALOG = "operateloglist.htm";

    /**
     * 初始化日志列表
     * 
     * @param pageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @param log
     *            日志对象 {@link com.ningpai.logger.bean.OperationLog}
     * @return {@link org.springframework.web.servlet.ModelAndView}
     */
    @RequestMapping("/operateloglist")
    public ModelAndView initOperaLog(HttpServletRequest request,
            PageBean pageBean, String startTime, String endTime,
            OperationLog log, String n, String l) {
        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        // 设置跳转URL
        pageBean.setUrl("operateloglist.htm");
        request.setAttribute("startTime", startTime);
        request.setAttribute("endTime", endTime);

        // 查询操作日志
        return new ModelAndView(ConstantLogUtil.OPREALIST, "pageBean",
                operaLogService.selectAllOpera(pageBean, log, startTime,
                        endTime,(Long) request.getSession().getAttribute("thirdId"))).addObject("operationLog", log).addObject(
                "operationLogNameList", operaLogService.selectDistinctOpName());
    }

//    /**
//     * ajax查询日志
//     *
//     * @param pb
//     * @return PageBean
//     */
//    @RequestMapping("/operalogajax")
//    @ResponseBody
//    public PageBean operaLogAjax(PageBean pb) {
//        return operaLogService.queryNewLog(pb);
//    }
//
    /**
     * 根据时间段 导出日志
     *
     * @param days
     *            天 {@link Long}
     */
    @RequestMapping("/exportlogexcel")
    public void exportLogExcel(HttpServletRequest request,HttpServletResponse response, Long days) {
        Long thirdId = (Long) request.getSession().getAttribute("thirdId");
        operaLogService.exportExcel(response, days, thirdId);
    }
//
//    /**
//     * deleteLog
//     * @param request
//     * @param response
//     * @param opName
//     * @return
//     */
//    @RequestMapping("/deletelog")
//    public ModelAndView deleteLog(HttpServletRequest request,
//            HttpServletResponse response, String opName) {
//        operaLogService.deleteLog(request.getParameterValues("parameterIds"));
//
//        return new ModelAndView(new RedirectView(INITOPERALOG)).addObject(
//                "opName", opName);
//
//    }


}
