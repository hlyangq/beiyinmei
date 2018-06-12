/**
 * Copyright 2014 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */

package com.ningpai.report.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.order.bean.BackGoods;
import com.ningpai.order.bean.BackOrder;
import com.ningpai.order.bean.OrderGoods;
import com.ningpai.order.dao.BackOrderMapper;
import com.ningpai.order.dao.OrderGoodsMapper;
import com.ningpai.order.dao.OrderMapper;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ningpai.common.lucene.main.LuceneIKUtil;
import com.ningpai.order.bean.Order;
import com.ningpai.order.service.OrderService;
import com.ningpai.report.bean.Report;
import com.ningpai.report.dao.ReportMapper;
import com.ningpai.report.service.ReportService;
import com.ningpai.report.util.DateUtil;
import com.ningpai.thirdaudit.bean.StoreInfo;
import com.ningpai.thirdaudit.mapper.StoreInfoMapper;
import com.ningpai.util.PageBean;

/**
 * @author Ningpai-HEHU
 * @version V1.0
 * @Description: np_report的service的实现类:
 * @date 2014-12-17 14:06:51
 */
@Service("ReportService")
public class ReportServiceImpl implements ReportService {

    /**
     * 记录日志对象
     */
    private static final Logger LOGGER = Logger.getLogger(LuceneIKUtil.class);

    /**
     * 时间格式化*
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String REPORT = "report";
    private static final String ENDDATE = "endDate";

    /**
     * 订单业务接口
     */
    @Resource(name = "OrderService")
    private OrderService orderService;

    /**
     * 商家业务接口
     */
    @Resource(name = "storeMapper")
    private StoreInfoMapper storeInfoMapper;

    // Spring注入
    @Resource(name = "ReportMapper")
    private ReportMapper reportMapper;

    @Resource(name = "BackOrderMapper")
    private BackOrderMapper backOrderMapper;

    @Resource(name = "OrderMapper")
    private OrderMapper orderMapper;

    @Resource(name = "OrderGoodsMapper")
    private OrderGoodsMapper orderGoodsMapper;
    /**
     * 根据主键删除
     *
     * @param reportId
     * @return
     */
    @Override
    public int delete(Long reportId) {
        return this.reportMapper.deleteByPrimaryKey(reportId);
    }

    /**
     * 插入，空属性不会插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(Report record) {
        return this.reportMapper.insertSelective(record);
    }

    /**
     * 根据主键查询
     *
     * @param reportId
     * @return
     */
    @Override
    public Report select(Long reportId) {
        return this.reportMapper.selectByPrimaryKey(reportId);
    }

    /**
     * 根据主键修改，空值条件不会修改成null
     *
     * @param record
     * @return
     */
    @Override
    public int update(Report record) {

        return this.reportMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除多条记录
     *
     * @param reportIds
     * @return
     */
    @Override
    public int deleteMuilti(Long[] reportIds) {
        return this.reportMapper.deleteMuilti(reportIds);
    }

    /**
     * 分页查询列表
     *
     * @param record
     * @param pageBean
     * @return
     */
    @Override
    public PageBean selectList(Report record, PageBean pageBean) {
        pageBean.setObjectBean(record);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageBean", pageBean);
        pageBean.setRows(this.reportMapper.selectListCount(map));
        pageBean.setList(this.reportMapper.selectList(map));
        return pageBean;
    }

    /**
     * 根据参数查询报表
     *
     * @param record
     * @return
     */
    @Override
    public List<Object> selectSumListByParam(Report record) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(REPORT, record);
        return this.reportMapper.selectSumListByParam(map);
    }

    /**
     * 分页查询列表
     *
     * @param record
     * @param pageBean
     * @return
     */
    @Override
    public PageBean selectReportCateList(Report record, PageBean pageBean) {
        pageBean.setObjectBean(record);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageBean", pageBean);
        pageBean.setRows(this.reportMapper.selectReportCateListCount(map));
        pageBean.setList(this.reportMapper.selectReportCateList(map));
        return pageBean;
    }

    /**
     * 对账查询订单
     *
     * @param storeId
     * @param pb
     * @return
     */
    @Override
    public PageBean selectReportOrderCateList(Long storeId,Date startTime,Date endTime, PageBean pb) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageBean", pb);
        map.put("startTime", DateUtil.dateToString(startTime));
        map.put("endTime", DateUtil.dateToString(endTime));
        map.put("storeId", storeId);
        pb.setRows(this.orderMapper.selectThirdOrderListByTimeCount(map));
        List<Object> orders = this.orderMapper.selectThirdOrderListByTime(map);
        if(orders.size() >0){
            for(Object o : orders){
                //订单总佣金
                BigDecimal ogp = BigDecimal.ZERO;
                Order order = (Order) o;
                if(order.getOrderGoodsList().size() > 0){
                    for(OrderGoods og : order.getOrderGoodsList()){
                        ogp = ogp.add(og.getCaRate().multiply(og.getGoodsBackPrice()));
                    }
                }
                ((Order) o).setOrderCatePrice(ogp);
            }
        }
        pb.setList(orders);

        return pb;
    }

    /**
     * 订单明细
     * @param storeId
     * @param orderId
     * @param pb
     * @return
     */
    @Override
    public PageBean selectReportOrderCateDdetailList(Long storeId, Long orderId, PageBean pb) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageBean", pb);
        map.put("orderId", orderId);
        map.put("storeId", storeId);
        pb.setRows(this.orderGoodsMapper.selectThirdselectOrderGoodsListListByTimeCount(map));
        List<Object> orderGoods = this.orderGoodsMapper.selectThirdOrderGoodsListByTime(map);
        for(Object orderg:orderGoods){
            OrderGoods og = (OrderGoods)orderg;
            if(!og.getGoodsBackPrice().equals(null)){
                og.setCaRateProduc(og.getGoodsBackPrice().multiply(og.getCaRate()));
            }
        }
        pb.setList(orderGoods);
        return pb;
    }

    @Override
    public PageBean selectReportBackOrderCateDdetailList(Long storeId, Long orderId, PageBean pb) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageBean", pb);
        map.put("orderId", orderId);
        map.put("storeId", storeId);
        pb.setRows(this.orderGoodsMapper.selectThirdselectOrderGoodsListListByTimeCount(map));
        List<Object> orderGoods = this.orderGoodsMapper.selectThirdOrderGoodsListByTime(map);
        pb.setList(orderGoods);
        return pb;
    }

    @Override
    public PageBean selectReportBackOrderCateList(Long storeId, Date startTime, Date endTime, PageBean pb) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageBean", pb);
        map.put("startTime", DateUtil.dateToString(startTime));
        map.put("endTime", DateUtil.dateToString(endTime));
        map.put("storeId", storeId);
        pb.setRows(this.backOrderMapper.queryReportBackOrderByBusinessIdCount(map));
        List<Object> border = this.backOrderMapper.queryReportBackOrderByBusinessId(map);
        for (Object borde : border){
            BackOrder bo = (BackOrder)borde;
            //订单总佣金
            BigDecimal ogp = BigDecimal.ZERO;
            //退货
            if(bo.getBackCheck().equals("4")){
                if(bo.getBackGoodsList().size() > 0){
                    for(BackGoods bg : bo.getBackGoodsList()){
                        ogp = ogp.add(bg.getCateRate().multiply(bg.getBackGoodsPrice()));
                    }
                }
                ((BackOrder) borde).setBackOrderCatePrice(ogp);
            }
            //退款
            else if (bo.getBackCheck().equals("10")){
                ((BackOrder) borde).setBackOrderCatePrice(ogp);
            }
        }
        pb.setList(border);
        return pb;
    }

    /**
     * 查询报表包含的订单
     *
     * @param reportId
     *            报表id
     * @param pb
     * @return
     */
    @Override
    public PageBean selectReportOrders(Long reportId, PageBean pb) {
        Report report = select(reportId);
        Order order = new Order();
        order.setBusinessId(report.getStoreId());
        // 这里值随便写，只要不为空即可
        order.setAlreadyPay("yes");
        order.setPayTimeStart(DateUtil.dateToString(report.getStartTime()));
        order.setPayTimeEnd(DateUtil.dateToString(report.getEndTime()));
        return orderService.searchOrderList(order, pb);
    }

    /**
     * 根据时间段，查询结算周期在这个时间段内的商家的订单金额、凤凰卡金额、银联付款金额
     *
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return
     */
    @Override
    public Object generateReport(String startDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startRowNum", 0);
        paramMap.put("endRowNum", 100000);
        List<Object> stores = storeInfoMapper.selectAuditList(paramMap);
        int days = DateUtil.getDays(startDate, endDate);
        for (int i = 0; i <= days; i++) {
            // 当前日期
            String currDate = DateUtil.addSomeDays(startDate, i);
            // 当月中的第几天
            int dayOfMonth = DateUtil.getDayOfMonth(currDate);

            for (Object o : stores) {
                StoreInfo storeInfo = (StoreInfo) o;
                String settleDates = storeInfo.getBillingCycle();
                if (settleDates == null) {
                    continue;
                }
                String[] countdates = settleDates.split("\\|");
                for (String str : countdates) {
                    // 说明有结算日期含当天的店铺
                    if (str.equals(dayOfMonth + "")) {
                        // 获取上一个结算日期
                        String lastCountDate = DateUtil.getLastCountDate(currDate, countdates);
                        if (lastCountDate == null) {
                            continue;
                        }
                        // 这里这么写，是因为客户后来要把结算日期算法往后推一天
//                        paramMap.put("startDate", DateUtil.addOneDay(lastCountDate));
//                        paramMap.put(ENDDATE, DateUtil.addOneDay(currDate));
                        paramMap.put("startDate", lastCountDate);
                        paramMap.put(ENDDATE, currDate);
                        paramMap.put("storeId", storeInfo.getStoreId());
                        insertIntoReport(paramMap);
                        continue;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 插入
     *
     * @param paramMap
     */
    private void insertIntoReport(Map<String, Object> paramMap) {
        List<Report> datas = reportMapper.queryReportData(paramMap);
        //判断是否有已完成的订单(如果没有也存在只有退单的情况)
        if(datas.size() >0){
            //计算总运费
            List<Object> orderlist = this.orderMapper.selectThirdOrderListTime(paramMap);
            //总运费
            BigDecimal expressPrice = BigDecimal.ZERO;
            for(Object ol : orderlist){
                Order order =  (Order)ol;
                expressPrice = expressPrice.add(order.getExpressPrice());
            }

            //退货流程
            paramMap.put("backCheck","4");
            List<BackOrder> border = backOrderMapper.queryBackOrderByBusinessId(paramMap);
            if(datas.size() >0 && border.size() >0){
                for (Report report : datas) {
                    report.setRefundPrice(BigDecimal.ZERO);
                    report.setBackPrice(BigDecimal.ZERO);
                    report.setExpressPrice(BigDecimal.ZERO);
                    report.setOrderPrice(BigDecimal.ZERO);
                    for(BackOrder bk : border){
                        if (bk.getBackGoodsList().size() >0){
                            for(BackGoods bg : bk.getBackGoodsList()){
                                if(bg.getCatId().equals(report.getCateId()) ){
                                    //设置退货金额
                                    report.setBackPrice(report.getBackPrice().add(bg.getBackGoodsPrice()));
                                }
                            }
                        }
                    }
                }
            }else{
                for (Report report : datas) {
                    report.setRefundPrice(BigDecimal.ZERO);
                    report.setBackPrice(BigDecimal.ZERO);
                    report.setExpressPrice(BigDecimal.ZERO);
                    report.setOrderPrice(BigDecimal.ZERO);
                }
            }

            //退款流程
            BigDecimal refund = BigDecimal.ZERO;
            paramMap.put("backCheck","10");
            List<BackOrder> rorder = backOrderMapper.queryBackOrderByBusinessId(paramMap);
            if(datas.size() >0 && rorder.size() >0){
                for(BackOrder ro : rorder){
                    if(ro.getBackPrice() !=null){
                        refund = refund.add(ro.getBackPrice());
                    }
                }
            }
            //将退款插入第一个
            if(datas.size()>0){
                datas.get(0).setExpressPrice(expressPrice);
                datas.get(0).setRefundPrice(refund);
            }

            // 这个日期已经加了1天了。从N天00点00分00秒，变成了N+1天00点00分00秒了，所以要给他变回来，减去一天
            paramMap.put(ENDDATE, DateUtil.subOneDay(paramMap.get(ENDDATE).toString()));
            Report r = reportMapper.selectOneByParam(paramMap);
            if (r != null) {
                return;
            }
            if (datas == null) {
                return;
            }

            for (Report report : datas) {
                if (report == null) {
                    continue;
                }
                //订单收入
                if(report.getTotalOrderMoney() != null){
                    report.setOrderPrice(report.getOrderPrice().add(report.getTotalOrderMoney()).add(report.getExpressPrice()));
                }
                //平台总佣金
                report.setCateRatePrice(report.getTotalOrderMoney().multiply(report.getCateRate()));
                //退货佣金
                report.setCateRatebackPrice(report.getBackPrice().multiply(report.getCateRate()));
                //类目总金额=当期完成的订单-平台的佣金+退货的佣金
                report.setTotalOrderMoney(report.getTotalOrderMoney().subtract(report.getCateRatePrice()).add(report.getCateRatebackPrice()));
                //如果存在运费就吧运费加上去
                report.setTotalOrderMoney(report.getTotalOrderMoney().add(report.getExpressPrice()));
                //如果存在退款就把退款加上去
                report.setTotalOrderMoney(report.getTotalOrderMoney().add(report.getRefundPrice()));
                report.setStartTime(DateUtil.stringToDate(paramMap.get("startDate").toString()));
                report.setEndTime(DateUtil.stringToDate(paramMap.get(ENDDATE).toString()));
                report.setCreateTime(new Date());
                reportMapper.insertSelective(report);
            }
        }else{
            //计算总运费
            List<Object> orderlist = this.orderMapper.selectThirdOrderListTime(paramMap);
            //总运费
            BigDecimal expressPrice = BigDecimal.ZERO;
            for(Object ol : orderlist){
                Order order =  (Order)ol;
                expressPrice = expressPrice.add(order.getExpressPrice());
            }

            Report report = new Report();

            //退货流程
            paramMap.put("backCheck","4");
            List<BackOrder> border = backOrderMapper.queryBackOrderByBusinessId(paramMap);
            if(border.size() >0){
                report.setRefundPrice(BigDecimal.ZERO);
                report.setBackPrice(BigDecimal.ZERO);
                report.setExpressPrice(BigDecimal.ZERO);
                report.setOrderPrice(BigDecimal.ZERO);
                for(BackOrder bk : border){
                    if (bk.getBackGoodsList().size() >0){
                        for(BackGoods bg : bk.getBackGoodsList()){
                            if(bg.getCatId().equals(report.getCateId()) ){
                                //设置退货金额
                                report.setBackPrice(report.getBackPrice().add(bg.getBackGoodsPrice()));
                            }
                        }
                    }
                }
            }else{
                report.setRefundPrice(BigDecimal.ZERO);
                report.setBackPrice(BigDecimal.ZERO);
                report.setExpressPrice(BigDecimal.ZERO);
                report.setOrderPrice(BigDecimal.ZERO);
            }

            //退款流程
            BigDecimal refund = BigDecimal.ZERO;
            paramMap.put("backCheck","10");
            List<BackOrder> rorder = backOrderMapper.queryBackOrderByBusinessId(paramMap);
            if(datas.size() >0 && rorder.size() >0){
                for(BackOrder ro : rorder){
                    if(ro.getBackPrice() !=null){
                        refund = refund.add(ro.getBackPrice());
                    }
                }
            }
            //将退款插入第一个
            report.setExpressPrice(expressPrice);
            report.setRefundPrice(refund);

            // 这个日期已经加了1天了。从N天00点00分00秒，变成了N+1天00点00分00秒了，所以要给他变回来，减去一天
            paramMap.put(ENDDATE, DateUtil.subOneDay(paramMap.get(ENDDATE).toString()));
            Report r = reportMapper.selectOneByParam(paramMap);

            if (r != null)

            //插入订单，存在退款或者退货
            if(border.size() >0 || rorder.size() >0){
                //订单收入
                if(report.getTotalOrderMoney() != null){
                    report.setOrderPrice(report.getOrderPrice().add(report.getTotalOrderMoney()).add(report.getExpressPrice()));
                }
                //平台总佣金
                report.setCateRatePrice(report.getTotalOrderMoney().multiply(report.getCateRate()));
                //退货佣金
                report.setCateRatebackPrice(report.getBackPrice().multiply(report.getCateRate()));
                //类目总金额=当期完成的订单-平台的佣金+退货的佣金
                report.setTotalOrderMoney(report.getTotalOrderMoney().subtract(report.getCateRatePrice()).add(report.getCateRatebackPrice()));
                //如果存在运费就吧运费加上去
                report.setTotalOrderMoney(report.getTotalOrderMoney().add(report.getExpressPrice()));
                //如果存在退款就把退款加上去
                report.setTotalOrderMoney(report.getTotalOrderMoney().add(report.getRefundPrice()));
                report.setStartTime(DateUtil.stringToDate(paramMap.get("startDate").toString()));
                report.setEndTime(DateUtil.stringToDate(paramMap.get(ENDDATE).toString()));
                report.setCreateTime(new Date());
                reportMapper.insertSelective(report);
            }else{
                return;
            }
        }
    }

    /**
     * 根据时间段，查询结算周期在这个时间段内的商家的订单金额、凤凰卡金额、银联付款金额
     */
    @Autowired
    public void generateTodayReport() {
        generateReport(DateUtil.dateToString(new Date()), DateUtil.dateToString(new Date()));
    }

    /**
     * 根据商家id，删除报表记录
     *
     * @param storeId
     *            商家id
     */
    @Override
    public void deleteByReportByStoreId(Long storeId) {
        reportMapper.deleteByStoreId(storeId);
    }

    /**
     * 根据条件导出报表
     *
     * @param report
     *            报表查询参数
     * @param response
     */
    @Override
    public void exportSumReport(Report report, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(REPORT, report);
        List<Object> reports = selectSumListByParam(report);
        HSSFWorkbook wb = new HSSFWorkbook();
        CellStyle style = wb.createCellStyle();
        HSSFRow tempRow;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        HSSFSheet sheet1 = wb.createSheet("对账报表");
        sheet1.setColumnWidth(0, 600);
        sheet1.setColumnWidth(1, 10000);
        sheet1.setColumnWidth(2, 3000);
        sheet1.setColumnWidth(3, 3000);
        sheet1.setColumnWidth(4, 3000);
        sheet1.createFreezePane(255, 1);
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("编号");
        headRow.createCell(1).setCellValue("店铺名称");
        headRow.createCell(2).setCellValue("总流水");
        headRow.createCell(4).setCellValue("商品优惠金额");
        if (null != reports && !reports.isEmpty()) {
            // 循环传递过来的货品列表,并添加到文件中
            for (int i = 0; i < reports.size(); i++) {
                Report r = (Report) reports.get(i);
                tempRow = sheet1.createRow(1 + i);
                tempRow.createCell(0).setCellValue(i + 1);
                tempRow.createCell(1).setCellValue(r.getStoreName());
                tempRow.createCell(2).setCellValue(r.getTotalOrderMoney().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(3).setCellValue(r.getTotalOrderPrePrice().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(4).setCellValue(r.getTotalGoodsPrePrice().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());

            }

        }
        String filename = "商家报表汇总_" + String.valueOf(System.currentTimeMillis()).concat(".xls");
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename));
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            LOGGER.error("根据条件导出报表错误：" + e);
        } finally {
            wb = null;
            filename = null;
            ouputStream = null;
            style = null;
            tempRow = null;
        }
    }

    /**
     * 导出类目报表
     *
     * @param report
     * @param response
     */
    @Override
    public void exportCateReport(Report report, HttpServletResponse response) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(REPORT, report);
        List<Object> reports = selectStoreCateReportList(report);
        HSSFWorkbook wb = new HSSFWorkbook();
        CellStyle style = wb.createCellStyle();
        HSSFRow tempRow;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        HSSFSheet sheet1 = wb.createSheet("对账报表");
        sheet1.setColumnWidth(0, 3000);
        sheet1.setColumnWidth(1, 10000);
        sheet1.setColumnWidth(2, 3000);
        sheet1.setColumnWidth(3, 3000);
        sheet1.setColumnWidth(4, 3000);
        sheet1.setColumnWidth(5, 3000);
        sheet1.setColumnWidth(6, 3000);
        sheet1.setColumnWidth(7, 3000);
        sheet1.setColumnWidth(8, 5000);
        sheet1.setColumnWidth(9,5000);
        sheet1.createFreezePane(255, 1);
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("编号");
        headRow.createCell(1).setCellValue("店铺名称");
        headRow.createCell(2).setCellValue("分类名称");
        headRow.createCell(3).setCellValue("分类扣率");
        headRow.createCell(4).setCellValue("总流水");
        headRow.createCell(5).setCellValue("应扣金额");
        headRow.createCell(6).setCellValue("订单优惠金额");
        headRow.createCell(7).setCellValue("商品优惠金额");
        headRow.createCell(8).setCellValue("报表时间");
        headRow.createCell(9).setCellValue("结算状态");
        if (null != reports && !reports.isEmpty()) {
            // 循环传递过来的货品列表,并添加到文件中
            for (int i = 0; i < reports.size(); i++) {
                Report r = (Report) reports.get(i);
                tempRow = sheet1.createRow(1 + i);
                tempRow.createCell(0).setCellValue(i + 1);
                tempRow.createCell(1).setCellValue(r.getStoreName());
                tempRow.createCell(2).setCellValue(r.getCateName());
                tempRow.createCell(3).setCellValue(r.getCateRate().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(4).setCellValue(r.getTotalOrderMoney().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(5).setCellValue(r.getShouldBuckle().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(6).setCellValue(r.getTotalOrderPrePrice().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(7).setCellValue(r.getTotalGoodsPrePrice().setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                tempRow.createCell(8).setCellValue(sdf.format(r.getStartTime()) + "~" + sdf.format(r.getEndTime()));
                if ("0".equals(r.getSettleStatus())) {
                    tempRow.createCell(9).setCellValue("未结算");
                } else if ("1".equals(r.getSettleStatus())) {
                    tempRow.createCell(9).setCellValue("已结算");
                }

            }

        }
        String filename = "商家类目报表_" + String.valueOf(System.currentTimeMillis()).concat(".xls");
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename));
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            LOGGER.error("导出类目报表：" + e);
        } finally {
            wb = null;
            filename = null;
            ouputStream = null;
            style = null;
            tempRow = null;
        }
    }

    /**
     * 查询
     *
     * @param report
     * @return List
     */
    private List<Object> selectStoreCateReportList(Report report) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(REPORT, report);
        return this.reportMapper.selectStoreCateReportList(map);
    }

    /**
     * 根据商店id，删除报表记录
     *
     * @param storeId
     *            商店id
     */
    @Override
    public void settleReport(Long storeId,Date startTime,Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        this.reportMapper.settleReport(map);
    }

    /**
     * 根据分类id，删除报表记录
     *
     * @param reportId
     */
    @Override
    public void settleReportById(Long reportId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportId", reportId);
        this.reportMapper.settleReport(map);
    }

}
