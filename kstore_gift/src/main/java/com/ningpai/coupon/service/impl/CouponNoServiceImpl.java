/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.coupon.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.coupon.bean.Coupon;
import com.ningpai.coupon.service.CouponService;

import com.ningpai.customer.service.CustomerServiceMapper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.stereotype.Service;

import com.ningpai.coupon.bean.CouponNo;
import com.ningpai.coupon.dao.CouponNoMapper;
import com.ningpai.coupon.service.CouponNoService;
import com.ningpai.util.MapUtil;
import com.ningpai.util.PageBean;

/**
 * @author ggn 优惠券券码接口实现类 2014-03-19
 */
@Service("CouponNoService")
public class CouponNoServiceImpl implements CouponNoService {

    private static final String COUPONID = "couponId";

    private CouponNoMapper couponNoMapper;

    private CouponService couponService;

    private CustomerServiceMapper customerServiceMapper;

    /*
     * 
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#selectNoByCouponId(java.lang
     * .Long)
     */
    @Override
    public List<CouponNo> selectNoByCouponId(Long couponId) {
        return couponNoMapper.selectNoByCouponId(couponId);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#updateCodeIsUse(java.lang.
     * String, java.lang.Long)
     */
    @Override
    public int updateCodeIsUse(String codeNo, String orderCode) {
        // 创建bean
        CouponNo cn = new CouponNo();
        // 设置修改参数
        cn.setCodeGetTime(new Date());
        cn.setCodeUseOrderId(orderCode);
        cn.setCodeNo(codeNo);
        cn.setCodeGetTime(new Date());
        return couponNoMapper.updateCodeIsUse(cn);
    }

    public CouponNoMapper getCouponNoMapper() {
        return couponNoMapper;
    }

    @Resource(name = "CouponNoMapper")
    public void setCouponNoMapper(CouponNoMapper couponNoMapper) {
        this.couponNoMapper = couponNoMapper;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.coupon.service.CouponService#selectNoByCouponIdByStatus(java
     * .lang.Long)
     */
    @Override
    public CouponNo selectNoByCouponIdByStatus(Long couponNo) {
        return couponNoMapper.selectNoByCouponIdByStatus(couponNo);
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#selectList(com.ningpai.util
     * .PageBean, java.lang.Long)
     */
    @Override
    public PageBean selectList(PageBean pb, Long couponId, CouponNo couponNo) {
        // 分装实体类属性
        Map<String, Object> paramMap = MapUtil.getParamsMap(couponNo);
        // 设置劵码id
        paramMap.put(COUPONID, couponId);
        paramMap.put("start", pb.getStartRowNum());
        paramMap.put("end", pb.getEndRowNum());
        // 查询总数
        pb.setRows(couponNoMapper.selectNoCountByCouponId(paramMap));

        // 查询条件封装
        paramMap.put("start", pb.getStartRowNum());
        paramMap.put("number", pb.getEndRowNum());
        try {
            // 查询列表页
            pb.setList(couponNoMapper.selectNoListByCouponId(paramMap));
        } finally {
            paramMap = null;
        }
        return pb;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#changeCouponGetAndStatus(java
     * .lang.Long)
     */
    @Override
    public int changeCouponGetAndStatus(Long codeId) {
        return couponNoMapper.changeCouponGetAndStatus(codeId);
    }

    /**
     * 导出优惠券券码
     * 
     * @param response
     * @param couponId
     */
    @Override
    public void exportCouponCodeNo(HttpServletResponse response, Long couponId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("couponId",couponId);
        Object couponNo;
        // 得到优惠券的所有券码信息
        List<Object> couponNos = couponNoMapper.newSelectCouponList(param);
        //得到优惠券信息
        Coupon coupon = couponService.searchCouponById(couponId);
        HSSFWorkbook wb = new HSSFWorkbook();
        // Sheet样式
        CellStyle style = wb.createCellStyle();
        HSSFRow tempRow;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        HSSFSheet sheet1 = wb.createSheet("优惠券券码列表");
        // 冻结
        sheet1.createFreezePane(255, 1);
        // 调整导出excel的券码的列宽
        sheet1.setColumnWidth(1, 11000);
        // 调整导出excel的领取时间的列宽
        sheet1.setColumnWidth(3, 5000);
        // 调整导出excel的使用订单号的列宽
        sheet1.setColumnWidth(4, 5000);
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("序号");
        headRow.createCell(1).setCellValue("券码");
        headRow.createCell(2).setCellValue("券码状态");
        headRow.createCell(3).setCellValue("领取时间");
        headRow.createCell(4).setCellValue("领取人");
        // 遍历数据集创建Excel的行
        if (couponNos != null && !couponNos.isEmpty()) {
            for (int i = 0; i < couponNos.size(); i++) {
                couponNo = couponNos.get(i);
                tempRow = sheet1.createRow(1 + i);
                tempRow.createCell(0).setCellValue(i + 1);
                tempRow.createCell(1).setCellValue(((CouponNo)couponNo).getCodeNo());
                tempRow.createCell(2).setCellValue(((CouponNo)couponNo).getCodeStatus());
                if (((CouponNo)couponNo).getCodeGetTime() != null) {
                    tempRow.createCell(3).setCellValue(
                            sdf.format(((CouponNo)couponNo).getCodeGetTime()));
                }
                if (((CouponNo)couponNo).getCustomerName() != null) {
                    tempRow.createCell(4).setCellValue(
                            ((CouponNo)couponNo).getCustomerName());
                }
            }
        }
        // 设置下载时客户端Excel的名称
        String filename = coupon.getCouponName() + "券码.xls";
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="
                + URLEncoder.encode(filename));
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            ouputStream = null;
        } finally {
            filename = null;
            couponNo = null;
            wb = null;
            ouputStream = null;
            style = null;
            tempRow = null;
        }
    }

    /*
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#getCouponGetNoByCouponId(java
     * .lang.Long)
     */
    @Override
    public int getCouponGetNoByCouponId(Long couponId) {
        return couponNoMapper.getCouponGetNoByCouponId(couponId);
    }

    /*
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#updateCouponCustomer(java.
     * lang.Long, java.lang.Long)
     */
    @Override
    public int updateCouponCustomer(Long codeId, Long cId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("codeId", codeId);
        param.put("customerId", cId);
        return couponNoMapper.updateCouponCustomer(param);
    }

    /*
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#selectReadyGet(java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public int selectReadyGet(Long couponId, Long cId) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put(COUPONID, couponId);
        param.put("customerId", cId);
        return couponNoMapper.selectReadyGet(param);
    }

    /*
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#queryUsedCountByCouponId(java
     * .lang.Long)
     */
    @Override
    public Long queryUsedCountByCouponId(Long couponId) {

        return couponNoMapper.queryUsedCountByCouponId(couponId);
    }

    @Override
    public int selectCountAllByCouponId(Long couponId) {
        return couponNoMapper.selectCountAllByCouponId(couponId);
    }

    /*
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#selectCouponNoByStatus(java
     * .lang.Long)
     */
    @Override
    public int selectCouponNoByStatus(Long couponId) {
        return couponNoMapper.selectCouponNoByStatus(couponId);
    }

    /*
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#selectCouponList(java.lang
     * .Long, com.ningpai.coupon.bean.CouponNo)
     */
    @Override
    public List<Object> selectCouponList(Long couponId, CouponNo couponNo) {
        // 分装实体类属性
        Map<String, Object> paramMap = MapUtil.getParamsMap(couponNo);
        // 设置劵码id
        paramMap.put(COUPONID, couponId);
        return couponNoMapper.newSelectCouponList(paramMap);
    }

    public CouponService getCouponService() {
        return couponService;
    }

    @Resource(name = "CouponService")
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    /*
     * 根据券码查询优惠券 (non-Javadoc)
     * 
     * @see
     * com.ningpai.coupon.service.CouponNoService#selectCouponByCode(java.lang
     * .String)
     */
    @Override
    public int selectCouponByCode(String couponCode, Long cId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("codeNo", couponCode);
        CouponNo couponNo = this.couponNoMapper.selectCouponNo(map);
        map.clear();
        if (couponNo == null) {
            // 优惠券码不存在
            return 1;
        } else {
            // 插叙优惠券详细
            Coupon coupon = couponService.searchCouponById(couponNo
                    .getCouponId());
            // 查询优惠券总数
            int countAll = couponNoMapper.selectCountAllByCouponId(couponNo
                    .getCouponId());
            // 查询已领张数
            map.put("couponId",couponNo.getCouponId());
            map.put("customerId",cId);
            Long  yilingPersonCount= couponNoMapper.queryUsedCountByCouponIdNew(map);
            Long  yilingCount = couponNoMapper.queryUsedCountByCouponId(couponNo.getCouponId());
            Date d = new Date();
            // 如果该券码未被领取，且优惠券还有可领张数,且未过有效期,才能领
            if (("0").equals(couponNo.getCodeStatus())
                    && couponNo.getCustomerId() == null
                    && (countAll - yilingCount.intValue()) > 0
                    && coupon.getCouponStartTime().before(d)
                    && coupon.getCouponEndTime().after(d)
                    && yilingPersonCount < coupon.getCouponGetNo().intValue()
                    ) {
                // 可领用
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("codeId", couponNo.getCodeId());
                param.put("customerId", cId);
                couponNoMapper.updateCouponCustomer(param);
                return 2;
            } else {
                // 无效
                return 3;
            }
        }
    }

    /**
     * 根据券码查询优惠券CouponNo
     * @param code
     * @return
     */
    @Override
    public CouponNo selectCouponNoByCode(String code){
        return couponNoMapper.selectCouponNoByCode(code);
    }
    public CustomerServiceMapper getCustomerServiceMapper() {
        return customerServiceMapper;
    }

    public void setCustomerServiceMapper(CustomerServiceMapper customerServiceMapper) {
        this.customerServiceMapper = customerServiceMapper;
    }
}
