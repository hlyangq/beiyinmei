package com.ningpai.customer.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.other.bean.StreetBean;
import com.ningpai.util.MyLogger;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.other.bean.CustomerAllInfo;

/**
 * 导出会员信息 2015-08-12
 * 
 * @author ggn
 *
 */
@Controller
public class ExportCustomerController {

    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(ExportCustomerController.class);

    // spring注入
    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    /**
     * 导出选中会员
     * @param response
     * @param customerIds
     */
    @RequestMapping("/exportCustomerCheck")
    public void exportCustomerCheck(HttpServletResponse response, Long[] customerIds) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("会员信息");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        // 设置宽度
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        // 设置列头信息
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("手机");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("邮箱");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("身份证");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("所在地");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("积分");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<CustomerAllInfo> list = customerServiceMapper.queryListForExportByCustomerIds(customerIds);

        for (int i = 0; i < list.size(); i++) {

            // 详细地址
            String detailAddress = "";
            row = sheet.createRow((int) i + 1);
            CustomerAllInfo cus = list.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(cus.getCustomerUsername());

            if (cus.getInfoGender() != null) {
                if ("1".equals(cus.getInfoGender())) {
                    row.createCell(1).setCellValue("男");
                } else if ("2".equals(cus.getInfoGender())) {
                    row.createCell(1).setCellValue("女");
                } else {
                    row.createCell(1).setCellValue("保密");
                }

            }
            if (cus.getInfoMobile() != null) {
                row.createCell(2).setCellValue(cus.getInfoMobile());
            }

            if (cus.getInfoEmail() != null) {
                row.createCell(3).setCellValue(cus.getInfoEmail());
            }

            if (cus.getInfoCardid() != null) {
                row.createCell(4).setCellValue(cus.getInfoCardid());
            }

            if (cus.getProvince() != null) {
                detailAddress += cus.getProvince().getProvinceName();
            }

            if (cus.getCity() != null) {
                detailAddress += cus.getCity().getCityName();
            }

            if (cus.getDistrict() != null) {
                detailAddress += cus.getDistrict().getDistrictName();
            }

            // 如果街道信息不为空 则查询街道信息
            if (StringUtils.isNotEmpty(cus.getInfoStreet())) {
                StreetBean streetBean = customerServiceMapper.queryStreetBeanById(cus.getInfoStreet());

                if (null != streetBean) {
                    detailAddress += streetBean.getStreetName();
                }
            }

            if (cus.getInfoAddress() != null) {
                detailAddress += cus.getInfoAddress();
            }

            if (StringUtils.isNotEmpty(detailAddress)) {
                row.createCell(5).setCellValue(detailAddress);
            }

            if (cus.getInfoPointSum() != null) {
                row.createCell(6).setCellValue(cus.getInfoPointSum());
            }

        }
        // 第六步，将文件存到指定位置
        String filename = String.valueOf(System.currentTimeMillis()).concat(".xls");
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            LOGGER.error("导出会员信息失败，请查看原因", e);
        }

    }

    /**
     * 导出会员信息
     */
    @RequestMapping("exportallcustomer")
    public void exportCustomer(HttpServletResponse response) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("会员信息");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        // 设置宽度
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        // 设置列头信息
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("性别");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("手机");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("邮箱");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("身份证");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("所在地");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("积分");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        List<CustomerAllInfo> list = customerServiceMapper.selectCustomerAllInfomation();


        for (int i = 0; i < list.size(); i++) {

            // 详细地址
            String detailAddress = "";
            row = sheet.createRow((int) i + 1);
            CustomerAllInfo cus = list.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue(cus.getCustomerUsername());

            if (cus.getInfoGender() != null) {
                if ("1".equals(cus.getInfoGender())) {
                    row.createCell(1).setCellValue("男");
                } else if ("2".equals(cus.getInfoGender())) {
                    row.createCell(1).setCellValue("女");
                } else {
                    row.createCell(1).setCellValue("保密");
                }

            }
            if (cus.getInfoMobile() != null) {
                row.createCell(2).setCellValue(cus.getInfoMobile());
            }

            if (cus.getInfoEmail() != null) {
                row.createCell(3).setCellValue(cus.getInfoEmail());
            }

            if (cus.getInfoCardid() != null) {
                row.createCell(4).setCellValue(cus.getInfoCardid());
            }


            if (cus.getProvince() != null) {
                detailAddress += cus.getProvince().getProvinceName();
            }

            if (cus.getCity() != null) {
                detailAddress += cus.getCity().getCityName();
            }

            if (cus.getDistrict() != null) {
                detailAddress += cus.getDistrict().getDistrictName();
            }

            // 如果街道信息不为空 则查询街道信息
            if (StringUtils.isNotEmpty(cus.getInfoStreet())) {
                StreetBean streetBean = customerServiceMapper.queryStreetBeanById(cus.getInfoStreet());

                if (null != streetBean) {
                    detailAddress += streetBean.getStreetName();
                }
            }

            if (cus.getInfoAddress() != null) {
                detailAddress += cus.getInfoAddress();
            }

            if (StringUtils.isNotEmpty(detailAddress)) {
                row.createCell(5).setCellValue(detailAddress);
            }
            if (cus.getInfoPointSum() != null) {
                row.createCell(6).setCellValue(cus.getInfoPointSum());
            }

        }
        // 第六步，将文件存到指定位置
        String filename = String.valueOf(System.currentTimeMillis()).concat(".xls");
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            LOGGER.error("导出会员信息失败，请查看原因", e);
        }

    }

}
