/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.third.logger.service;

import com.ningpai.third.logger.bean.OperationLog;
import com.ningpai.util.PageBean;

import javax.servlet.http.HttpServletResponse;



import java.util.List;

/**
 * 操作日志Service
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年6月25日 上午10:25:13
 * @version 0.0.1
 */
public interface OperateLogService {

    /***
     * 根据ID获取对应日志的操作内容
     * @param opid
     * @return
     */
    OperationLog selectLogById(Long opid);

    /**
     * 添加操作日志
     * 
     * @param log
     *            日志对象 {@link com.ningpai.logger.bean.OperationLog}
     * @return
     */
    int addOperaLog(OperationLog log);

    /**
     * 去重获取opname
     * @return
     */
    List<Object> selectDistinctOpName();

    /**
     * 查询操作日志
     *
     * @param pageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @param log
     *            日志对象 {@link com.ningpai.logger.bean.OperationLog}
     * @return {@link com.ningpai.util.PageBean}
     */
    PageBean selectAllOpera(PageBean pageBean, OperationLog log, String startTime, String endTime, Long thirdId);

    /**
     * 根据时间段导出日志
     * 
     * @param days
     *            天 {@link Long}
     */
    void exportExcel(HttpServletResponse response, Long days,Long thirdId);

    /**
     * 删除日志
     * 
     * @param opIds
     *            日志ID
     * @return 0 删除失败 1成功
     */
    int deleteLog(String[] opIds);

    /**
     * 查看最新的订单日志
     *
     * @param pageBean 分页参数
     * @author lih
     * @return 日志列表
     */
     PageBean  queryNewLog(PageBean pageBean);
}
