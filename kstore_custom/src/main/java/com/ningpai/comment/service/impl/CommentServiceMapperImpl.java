/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.comment.service.impl;

import com.ningpai.comment.bean.Comment;
import com.ningpai.comment.dao.CommentMapper;
import com.ningpai.comment.service.CommentServiceMapper;
import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.service.CustomerServiceMapper;
import com.ningpai.logger.util.OperaLogUtil;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.util.PageBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 评论服务类实现类
 * 
 * @author NINGPAI-zhangqiang
 * @since 2013年12月24日 下午6:55:49
 * @version 1.0
 */
@Service("commentServiceMapper")
public class CommentServiceMapperImpl implements CommentServiceMapper {

    /** 记录日志对象 */
    private static final Logger LOGGER = Logger.getLogger(CommentServiceMapperImpl.class);

    private static final String RESULT = "result";
    private static final String GOODSID="goodsId";
    private static final String COMMENTTYPE="commentType";
    private static final String PARAMSTRING="paramString";

    // spring注解
    private CommentMapper commentMapper;

    @Resource(name = "customerServiceMapper")
    private CustomerServiceMapper customerServiceMapper;

    /**
     * 查询所有评论 分页
     *
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @see Comment {@link com.ningpai.comment.bean.Comment}
     * @return @see com.ningpai.util.PageBean {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean selectAllComment(PageBean pageBean, Comment comment,Long thirdId) {

        if (null != comment) {
            comment.setThirdId(thirdId);
        }
        Map<String, Object> paramMap;
        int no;
        try {
            // 设置pageBean的总行数
            pageBean.setRows(Integer.parseInt((comment == null ? commentMapper.selectAllCommentCount(thirdId) : commentMapper.selectCommentCount(comment)) + ""));
            no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean.getRows() / pageBean.getPageSize() : (pageBean.getRows() / pageBean.getPageSize() + 1);
            no = no == 0 ? 1 : no;
            // 页码大于最后一页 则设为最好一页的页码
            if (pageBean.getPageNo() >= no) {
                pageBean.setPageNo(no);
                pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
            }
            // 设置查询对象的起始编号和结束编号
            if (comment != null) {
                comment.setStartRowNum(pageBean.getStartRowNum());
                comment.setEndRowNum(pageBean.getEndRowNum());
            }
            paramMap = new HashMap<String, Object>();
            paramMap.put("startRowNum", pageBean.getStartRowNum());
            paramMap.put("endRowNum", pageBean.getEndRowNum());
            paramMap.put("thirdId", thirdId);
            pageBean.setList(comment == null ? commentMapper.selectCommentByLimit(paramMap) : commentMapper.selectCommentByComment(comment));
        } finally {
            // 置空对象
            paramMap = null;
        }
        return pageBean;
    }

    /**
     * 删除评论
     *
     * @seede
     * @see java.lang.Integer {@link java.lang.Integer}
     * @return 0 失败 1成功
     */
    @Override
    public int deleteComment(String[] commentIds) {
        int count = 0;
        try {
            for (String custId : commentIds) {
                count += commentMapper.deleteCommentById(Long.parseLong(custId));
            }
            return count;
        } finally {
            count = 0;
        }

    }

    /**
     * 根据评论编号 查询评论
     *
     * @see java.lang.Integer {@link java.lang.Integer}
     * @param commentId
     * @return Comment {@link com.ningpai.comment.bean.Comment}
     */
    @Override
    public Comment selectByCommentId(Long commentId) {
        return commentMapper.selectByCommentId(commentId);
    }

    /**
     * 查询所用评论 分页
     *
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @see thirdId 第三方id
     * @see Comment {@link com.ningpai.comment.bean.Comment}
     * @return @see com.ningpai.util.PageBean {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean selectAllConsult(PageBean pageBean, Comment comment,Long thirdId) {

        if (null != comment) {
            comment.setThirdId(thirdId);
        }

        Map<String, Object> paramMap = null;
        int no = 0;
        try {
            // 设置pageBean的总行数
            pageBean.setRows(Integer.parseInt((comment == null ? commentMapper.selectAllConsultCount(thirdId) : commentMapper.selectConsultCount(comment)) + ""));
            no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean.getRows() / pageBean.getPageSize() : (pageBean.getRows() / pageBean.getPageSize() + 1);
            no = no == 0 ? 1 : no;
            // 页码大于最后一页 则设为最好一页的页码
            if (pageBean.getPageNo() >= no) {
                pageBean.setPageNo(no);
                pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
            }
            // 设置查询对象的起始编号和结束编号
            if (comment != null) {
                comment.setStartRowNum(pageBean.getStartRowNum());
                comment.setEndRowNum(pageBean.getEndRowNum());
            }
            paramMap = new HashMap<String, Object>();
            paramMap.put("startRowNum", pageBean.getStartRowNum());
            paramMap.put("endRowNum", pageBean.getEndRowNum());
            paramMap.put("thirdId", thirdId);
            pageBean.setList(comment == null ? commentMapper.selectAllConsult(paramMap) : commentMapper.selectConsultByConsult(comment));
        } finally {
            // 置空对象
            paramMap = null;
        }
        return pageBean;
    }

    /**
     * 根据商品编号查询所有评论
     *
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @param goodsId
     *            商品编号
     * @param type
     *            评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param paramString
     *            咨询内容
     * @param item
     *            咨询项目
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean selectCommByGoodsId(PageBean pageBean, Long goodsId, Character type, String paramString, String item) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(GOODSID, goodsId);
        paramMap.put(COMMENTTYPE, type);
        paramMap.put(PARAMSTRING, paramString);
        paramMap.put("item", item);
        pageBean.setRows(Integer.parseInt(commentMapper.selectGoodAllCommCount(paramMap).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }
        paramMap.put(CustomerConstantStr.STARTNUM, pageBean.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pageBean.getEndRowNum());
        pageBean.setList(commentMapper.selectAllCommentByGoodsId(paramMap));
        return pageBean;
    }

    /**
     * 根据商品编号查询所有评论
     *
     * @see PageBean {@link com.ningpai.util.PageBean}
     * @param goodsId
     *            商品编号
     * @param type
     *            评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString
     *            咨询内容
     * @return PageBean {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean selectCommByGoodsId(PageBean pageBean, Long goodsId, Character type, String parmaString) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(GOODSID, goodsId);
        paramMap.put(COMMENTTYPE, type);
        paramMap.put(PARAMSTRING, parmaString);
        pageBean.setRows(Integer.parseInt(commentMapper.selectGoodAllCommCount(paramMap).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }
        paramMap.put(CustomerConstantStr.STARTNUM, pageBean.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pageBean.getEndRowNum());
        pageBean.setList(commentMapper.selectAllCommentByGoodsId(paramMap));
        return pageBean;
    }

    /**
     * 根据商品编号查询所有评论
     * 新版移动端商品详情页使用
     *
     * @param pageBean
     * @param goodsId     商品编号
     * @param type        评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString 咨询内容    @return PageBean {@link com.ningpai.util.PageBean}
     * @author houyichang 2015/10/21
     * @see com.ningpai.util.PageBean {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean selectCommByGoodsIdHyc(PageBean pageBean, Long goodsId, Character type, String parmaString) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(GOODSID, goodsId);
        paramMap.put(COMMENTTYPE, type);
        paramMap.put(PARAMSTRING, parmaString);
        pageBean.setRows(Integer.parseInt(commentMapper.selectGoodAllCommCount(paramMap).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }
        pageBean.setList(commentMapper.selectAllCommentByGoodsIdHyc(paramMap));
        return pageBean;
    }

    /**
     * 根据商品编号查询所有评论
     * 新版移动端商品详情页使用
     *
     * @param pageBean
     * @param goodsId     商品编号
     * @param type        评论类型 传null将查询咨询 0查询好评 1是中评 2是差评 3全部
     * @param parmaString 咨询内容    @return PageBean {@link com.ningpai.util.PageBean}
     * @author houyichang 2015/10/21
     * @see com.ningpai.util.PageBean {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean selectCommDetailByGoodsIdHyc(PageBean pageBean, Long goodsId, Character type, String parmaString) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(GOODSID, goodsId);
        paramMap.put(COMMENTTYPE, type);
        paramMap.put(PARAMSTRING, parmaString);
        pageBean.setRows(Integer.parseInt(commentMapper.selectGoodAllCommCount(paramMap).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }
        pageBean.setList(commentMapper.selectAllCommentDetailByGoodsId(paramMap));
        return pageBean;
    }

    /**
     * 查询商品评论条数
     *
     * @param goodsId     商品id
     * @param commentType 评论类型
     * @param paramString
     * @return 查询到的行数
     */
    @Override
    public int queryCommentCountByType(Long goodsId, Character commentType, String paramString) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(GOODSID, goodsId);
        paramMap.put(COMMENTTYPE, commentType);
        paramMap.put(PARAMSTRING, paramString);

        return Integer.parseInt(commentMapper.selectGoodAllCommCount(paramMap).toString());
    }

    /**
     * 修改评论
     *
     * @param comment
     *            评论对象{@link com.ningpai.comment.bean.Comment}
     * @return 0失败 1成功
     */
    @Override
    public int updateComment(Comment comment) {
        return commentMapper.updateComment(comment);
    }

    /**
     * 添加商品评论
     *
     * @param comment
     *            评论信息 {@link Comment}
     * @return 评论编号
     */
    @Override
    public int addGoodsComment(Comment comment) {
        return commentMapper.addGoodsComment(comment);
    }

    /**
     * 查询会员咨询
     *
     * @param customerId
     *            会员编号 {@link Long}
     * @param flag
     *            回复标记
     * @param
     *
     * @return PageBean 分页辅助类 {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean queryCustConsult(Long customerId, String flag, PageBean pageBean) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Comment comment = new Comment();
        comment.setCustomerId(customerId);
        comment.seteFlag(flag == null ? null : flag);

        pageBean.setRows(Integer.parseInt(commentMapper.queryCustConsultCount(comment).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }
        paramMap.put("comment", comment);
        paramMap.put(CustomerConstantStr.STARTNUM, pageBean.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pageBean.getEndRowNum());
        pageBean.setList(commentMapper.queryCustConsult(paramMap));
        return pageBean;
    }

    /**
     * 查询会员评论
     *
     * @param customerId
     *            会员编号 {@link Long}
     * @param pageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @return PageBean 分页辅助类 {@link com.ningpai.util.PageBean}
     */
    @Override
    public PageBean queryCustComment(Long customerId, PageBean pageBean) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Comment comment = new Comment();
        comment.setCustomerId(customerId);
        pageBean.setRows(Integer.parseInt(commentMapper.queryCustCommentCount(comment).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }
        paramMap.put("comment", comment);
        paramMap.put(CustomerConstantStr.STARTNUM, pageBean.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pageBean.getEndRowNum());
        pageBean.setList(commentMapper.queryCustComment(paramMap));
        return pageBean;
    }

    /**
     * 查询会员评论 前台我的评论使用 确认收货过的订单商品都可以评价
     * 
     * @param customerId
     *            会员编号 {@link Long}
     * @param pageBean
     *            分页辅助类 {@link com.ningpai.util.PageBean}
     * @return
     */
    @Override
    public PageBean queryCommentByCust(Long orderId, Long customerId, PageBean pageBean) {
        // 条件封装容器
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId);
        paramMap.put("customerId", customerId);
        // 评论的数量 也是完成过的订单商品
        pageBean.setRows(Integer.parseInt(commentMapper.queryCommentCountByCust(paramMap).toString()));
        if (pageBean.getPageNo() > pageBean.getLastPageNo()) {
            pageBean.setPageNo(pageBean.getLastPageNo());
        }
        if (pageBean.getPageNo() == 0) {
            pageBean.setPageNo(1);
        }

        paramMap.put(CustomerConstantStr.STARTNUM, pageBean.getStartRowNum());
        paramMap.put(CustomerConstantStr.ENDNUM, pageBean.getEndRowNum());
        pageBean.setList(commentMapper.queryCommentByCust(paramMap));
        return pageBean;
    }

    /**
     * 导出所有评论
     * 
     * @param response
     */
    @Override
    public void exportComment(HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Comment comment = null;
        List<Comment> comments = commentMapper.queryAllComment();
        HSSFWorkbook wb = new HSSFWorkbook();
        CellStyle style = wb.createCellStyle();
        HSSFRow tempRow;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        HSSFSheet sheet1 = wb.createSheet("商品评论列表");
        sheet1.createFreezePane(255, 1);
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("序号");
        headRow.createCell(1).setCellValue("商品名称");
        headRow.createCell(2).setCellValue("发表人（昵称）");
        headRow.createCell(3).setCellValue("内容");
        headRow.createCell(4).setCellValue("发表时间");
        if (null != comments && !comments.isEmpty()) {
            // 循环传递过来的货品列表,并添加到文件中
            for (int i = 0; i < comments.size(); i++) {
                comment = (Comment) comments.get(i);
                tempRow = sheet1.createRow(1 + i);
                tempRow.createCell(0).setCellValue(i + 1);
                tempRow.createCell(1).setCellValue(comment.getGoodsName());
                tempRow.createCell(2).setCellValue(comment.getCustomerNickname());
                tempRow.createCell(3).setCellValue(comment.getCommentContent());
                tempRow.createCell(4).setCellValue(sdf.format(comment.getPublishTime()));
            }

        }
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("商品评论备份.xls"));
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            LOGGER.error("",e);
            ouputStream = null;
        } finally {
            comment = null;
            wb = null;
            ouputStream = null;
            style = null;
            tempRow = null;
        }
    }

    /**
     * 导出评论模板
     * 
     * @param response
     */
    @Override
    public void exportCommentTemp(HttpServletResponse response) {
        HSSFWorkbook wb = new HSSFWorkbook();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        HSSFSheet sheet1 = wb.createSheet("分类导入模板");
        sheet1.createFreezePane(255, 1);
        HSSFRow headRow = sheet1.createRow(0);
        headRow.createCell(0).setCellValue("商品编号");
        headRow.createCell(1).setCellValue("评论内容");
        headRow.createCell(2).setCellValue("是否显示（0否1是）");
        headRow.createCell(3).setCellValue("评分");
        headRow.createCell(4).setCellValue("是否匿名（0否1是）");
        headRow.createCell(5).setCellValue("用户账号");
        // 设置下载时客户端Excel的名称
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("商品评论模板.xls"));
        OutputStream ouputStream;
        try {
            ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            LOGGER.error("",e);
            ouputStream = null;
        } finally {
            wb = null;
            ouputStream = null;
            style = null;
        }
    }

    /**
     * 导入评论
     * 
     * @param request
     * @param response
     * @param multiRequest
     * @return
     */
    @Override
    public String importCommentByExcel(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiRequest) {
        try {
            MultipartFile file = multiRequest.getFile("importExcel");
            if (file.isEmpty()) {
                return "401";// 文件为空
            } else if (!".xls".equals(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."), file.getOriginalFilename().length()))) {
                return "402";// 扩展名不正确
            }
            return execImportComment(importComment(request, file.getInputStream()), request);
        } catch (IOException e) {
            LOGGER.error("导入商品评论失败:",e);
            return "400";// 失败
        }
    }

    /**
     * 导出comment
     *
     * */
    public String execImportComment(Map<String, Object> map, HttpServletRequest request) {
        List<Comment> list = (List<Comment>) map.get("commentList");
        if (list == null) {
            return map.get(RESULT).toString();
        }

        try {
            if (null != list && !list.isEmpty()) {
                for (Comment comment : list) {
                    comment.setDelFlag("0");
                    commentMapper.addGoodsComment(comment);
                }
                return "200";
            } else {
                return "400";
            }
        } catch (Exception e) {
            LOGGER.error("",e);
            Customer cust = (Customer) request.getSession().getAttribute("cust");
            OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
        }
        return "200";
    }

    /**
     * 导入商品品牌
     * 
     * @param is
     * @return
     */
    public Map<String, Object> importComment(HttpServletRequest request, InputStream is) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Comment> commentList = new ArrayList<Comment>();
        Comment comment = null;
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        HSSFRow row = null;
        int rows = 0;
        try {
                fs = new POIFSFileSystem(is);
                wb = new HSSFWorkbook(fs);
            sheet = wb.getSheetAt(0);
            rows = sheet.getPhysicalNumberOfRows();
            Pattern numberPattern = Pattern.compile("[0-9]*");
            Pattern decimalPattern = Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?");
            Matcher isDecimal = null;
            Matcher isNum = null;
            for (int i = 1; i < rows; i++) {
                comment = new Comment();
                row = sheet.getRow(i);

                // 1、设置货品id
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                String goodsNo = row.getCell(0).getStringCellValue();
                if (goodsNo == null || "".equals(goodsNo)) {
                    map.put(RESULT, "501");
                    return map;
                }
                Long goodsInfoId = commentMapper.selectGoodsInfoIdByNo(goodsNo);
                if (goodsInfoId == null) {
                    continue;
                }
                comment.setGoodsId(goodsInfoId);

                // 2、设置评论内容
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String commentContent = row.getCell(1).getStringCellValue();
                comment.setCommentContent(commentContent);

                // 3、设置是否显示
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String isDisplay = row.getCell(2).getStringCellValue();
                isNum = numberPattern.matcher(isDisplay);
                // 是否显示不是数字
                if (isDisplay != null && !"".equals(isDisplay) && !isNum.matches()) {
                    map.put(RESULT, "502");
                    return map;
                }
                if (isDisplay != null && !"".equals(isDisplay)) {
                    comment.setIsDisplay(isDisplay);
                }

                // 4、设置评分
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String scoreStr = row.getCell(3).getStringCellValue();
                    isDecimal = decimalPattern.matcher(scoreStr);
                    if (!isDecimal.matches()) {
                        map.put(RESULT, "503");
                        return map;
                    }
                    comment.setCommentScore(new BigDecimal(scoreStr));
                }

                // 4、设置是否匿名
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String isAno = row.getCell(4).getStringCellValue();
                    // 是否匿名不是数字
                    isNum = numberPattern.matcher(isAno);
                    if (isAno != null && !"".equals(isAno) && !isNum.matches()) {
                        map.put(RESULT, "504");
                        return map;
                    }
                    comment.setIsAnonymous(isAno);
                }

                // 5、设置用户id
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String userName = row.getCell(5).getStringCellValue();
                    Customer customer = customerServiceMapper.selectCustomerByUserName(userName);
                    comment.setCustomerId(customer.getCustomerId());
                    comment.setCustomerImg(customer.getCustomerImg());
                    comment.setCustomerNickname(customer.getCustomerNickname());
                }
                commentList.add(comment);
            }
            map.put("commentList", commentList);
            return map;
        } catch (Exception e) {
            LOGGER.error("",e);
            Customer cust = (Customer) request.getSession().getAttribute("cust");
            OperaLogUtil.addOperaException(cust.getCustomerUsername(), e, request);
            map.put(RESULT, "400");
            return map;
        } finally {
            commentList = null;
            comment = null;
            fs = null;
            wb = null;
            sheet = null;
            row = null;
        }
    }

    /**
     * 查询第三方店铺的评级
     * 
     * @param thirdId
     *            店铺Id
     * @return 评论对象
     */
    @Override
    public Comment selectSellerComment(Long thirdId) {

        return commentMapper.selectSellerAvg(thirdId);
    }

    /**
     * 根据订单商品编号和会员编号查询商品评论晒单信息
     * 
     * @param orderGoodsId
     * @param customerId
     * @return
     */
    @Override
    public Comment queryCommentByOrderGoodsId(Long orderGoodsId, Long customerId) {
        // 创建封装条件容器
        Map<String, Object> map = new HashMap<String, Object>();
        // 填充订单商品编号
        map.put("orderGoodsId", orderGoodsId);
        // 会员编号
        map.put("customerId", customerId);
        return commentMapper.queryCommentByOrderGoodsId(map);
    }

    public CommentMapper getCommentMapper() {
        return commentMapper;
    }

    @Resource(name = "commentMapper")
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

}
