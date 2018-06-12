/**
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.logger.util;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ningpai.third.logger.bean.OperationLog;
import com.ningpai.third.logger.service.OperateLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志Util
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年6月25日 上午10:05:05
 * @version 0.0.1
 */
@Service
public final class OperateLogUtil {
    private static OperateLogService operaLogService;

    /**
     * 添加异常日志
     * 
     * @param username
     *            用户名
     * @param e
     *            异常 {@link Exception}
     * @param request
     */
    public static void addOperaException(String flag, Exception e, HttpServletRequest request) {
        Date date = new Date();
        StackTraceElement[] st = e.getStackTrace();
        OperationLog log = new OperationLog();
        log.setOpCode(e.getClass().getName());
        String name=(String) request.getSession().getAttribute("name");
        log.setOpName(name);
        log.setOpTime(date);
        log.setOpIp(com.ningpai.third.logger.util.IPAddress.getIpAddr(request));
        log.setCreateId((Long) request.getSession().getAttribute("loginUserId"));
        log.setOpContent("异常代码位置" + ":" + flag+" [类:" + st[0].getClassName() + "]调用" + st[0].getMethodName() + "时在第" + st[0].getLineNumber() + "行代码处发生异常!异常类型:" + e.getClass().getName()+"（请及时与相关人员联系！）");
        operaLogService.addOperaLog(log);
    }

    /**
     * 添加操作日志记录
     * 
     * @param request
     * @param username
     *            操作人 {@link String}
     * @param operaCode
     *            操作主题 如:添加会员 {@link String}
     * @param operaContent
     *            操作内容
     */
    public static void addOperaLog(HttpServletRequest request, String username, String operaCode, String operaContent) {
        Date date = new Date();
        OperationLog log = new OperationLog();
        log.setOpCode(operaCode);
        log.setOpName(username);
        log.setOpTime(date);
        log.setOpIp(com.ningpai.third.logger.util.IPAddress.getIpAddr(request));
        log.setOpContent(operaContent);
        if(request.getSession().getAttribute("thirdId") != null){
            log.setOpIsSeller("1");
            log.setThirdId((Long) request.getSession().getAttribute("thirdId"));
        }
        operaLogService.addOperaLog(log);
    }

    /**
     * 操作日志Service
     * @return
     */
    public OperateLogService getOperaLogService() {
        return operaLogService;
    }

    /**
     * setOperaLogService
     * @param operaLogService
     */
    @Resource(name = "operateLogService")
    public void setOperaLogService(OperateLogService operaLogService) {
        OperateLogUtil.operaLogService = operaLogService;
    }

}
