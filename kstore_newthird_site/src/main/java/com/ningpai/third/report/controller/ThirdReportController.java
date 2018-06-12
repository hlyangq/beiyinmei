package com.ningpai.third.report.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ningpai.thirdaudit.service.AuditService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ningpai.report.bean.Report;
import com.ningpai.report.service.ReportService;
import com.ningpai.report.util.DateUtil;
import com.ningpai.third.report.util.ReportPath;
import com.ningpai.third.util.MenuOperationUtil;
import com.ningpai.util.PageBean;
/**
 * <P>商家对账控制器</P>
 * @author NINGPAI-LIH
 * @since 2014年5月19日17:41:01
 * @version 2.0
 */
@Controller
public class ThirdReportController {
    //报表业务接口
    @Resource(name="ReportService")
    private ReportService reportService;

    @Resource(name="auditService")
    private AuditService auditService;

    /**查询报表列表
     * @param report 报表查询参数
     * @param month 月份 yyyy-MM
     * @param pb    分页查询参数
     * @param request
     * @param n 当前位置
     * @param l 当前位置
     * @return
     */
    @RequestMapping("queryCheckReport")
    public ModelAndView queryCheckReport(Report report,String month ,PageBean pb,HttpServletRequest request,String n,String l) {
        //填充导航/左侧菜单索引 用于页面控制css选中
        MenuOperationUtil.fillSessionMenuIndex(request, n, l);
        //报表结束时间
        String endmonth = request.getParameter("endmonth");
        if(month!=null && !"".equals(month)) {
            //设置根据日期筛选的起始日期
            report.setStartDate(month+" 00:00:00");
        }
        if(endmonth!=null && !"".equals(endmonth)){
            //设置根据日期筛选的截止日期
            report.setEndDate(endmonth+" 23:59:59");
        }
        //装载返回到页面的数据
        Map<String, Object> map = new HashMap<String, Object>();
        //商家ID
        report.setStoreId((Long) request.getSession().getAttribute("thirdId"));
        //根据条件筛选获取的报表数据
        map.put("pb", reportService.selectReportCateList(report, pb));
        //表的实体
        map.put("report", report);
        //月份
        map.put("month", month);
        map.put("endmonth", endmonth);
        //重定向到要跳转的页面 转载需要返回到页面的数据
        return new ModelAndView(ReportPath.THIRDREPORTLIST).addObject("map", map);
    }

    /**
     * 查询报表退单
     * @param pb
     * @param pb
     * @param attrName
     * @param attrValue
     * @return
     */
    @RequestMapping("queryThirdReportBackOrderCate")
    public ModelAndView queryThirdReportBackOrderCate(PageBean pb,Long storeId,String startTime,String endTime,String attrName,String attrValue) throws ParseException {
        pb.setUrl("queryReportCate.htm");
        Map<String, String> dateMap = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starttime = sdf.parse(startTime);
        Date endtime = sdf.parse(endTime);
        dateMap.put("today", DateUtil.dateToString(new Date()));
        dateMap.put("recentOneMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),1));
        dateMap.put("recentThreeMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),3));
        dateMap.put("recentSixMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),6));
        return new ModelAndView(ReportPath.BACKTHIRDREPORTLIST)
                .addObject("pb", reportService.selectReportBackOrderCateList(storeId, starttime, endtime, pb))
                .addObject("store", auditService.selectByCustomerId(storeId))
                .addObject("storeId", storeId)
                .addObject("dateMap", dateMap)
                .addObject("attrName",attrName)
                .addObject("attrValue",attrValue);
    }

    /**
     * 查询报表订单
     * @param pb
     * @param storeId
     * @param attrName
     * @param attrValue
     * @return
     */
    @RequestMapping("queryThirdReportOrderCate")
    public ModelAndView queryThirdReportOrderCate(PageBean pb,Long storeId,String startTime,String endTime,String attrName,String attrValue) throws ParseException {
        pb.setUrl("queryReportCate.htm");
        Map<String, String> dateMap = new HashMap<String, String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starttime = sdf.parse(startTime);
        Date endtime = sdf.parse(endTime);
        dateMap.put("today", DateUtil.dateToString(new Date()));
        dateMap.put("recentOneMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),1));
        dateMap.put("recentThreeMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),3));
        dateMap.put("recentSixMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),6));
        return new ModelAndView(ReportPath.ORDERTHIRDREPORTLIST)
                .addObject("pb", reportService.selectReportOrderCateList(storeId,starttime,endtime, pb))
                .addObject("store", auditService.selectByCustomerId(storeId))
                .addObject("storeId", storeId)
                .addObject("dateMap", dateMap)
                .addObject("attrName",attrName)
                .addObject("attrValue",attrValue)
                .addObject("startTime",startTime)
                .addObject("endTime",endTime)
                .addObject("storeId",storeId);
    }

    /**
     *查询订单明细
     * @param pb
     * @param storeId
     * @param attrName
     * @param attrValue
     * @return
     * @throws ParseException
     */
    @RequestMapping("queryThirdReportOrderCateDetail")
    public ModelAndView queryThirdReportOrderCateDetail(PageBean pb,Long storeId,Long orderId,String attrName,String attrValue) throws ParseException {
        pb.setUrl("queryReportCate.htm");
        Map<String, String> dateMap = new HashMap<String, String>();
        dateMap.put("today", DateUtil.dateToString(new Date()));
        dateMap.put("recentOneMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),1));
        dateMap.put("recentThreeMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),3));
        dateMap.put("recentSixMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),6));
        return new ModelAndView(ReportPath.ORDERTHIRDREPORTDETAIL)
                .addObject("pb", reportService.selectReportOrderCateDdetailList(storeId,orderId, pb))
                .addObject("store", auditService.selectByCustomerId(storeId))
                .addObject("storeId", storeId)
                .addObject("dateMap", dateMap)
                .addObject("attrName",attrName)
                .addObject("attrValue", attrValue);
    }

    /**
     *查询退款明细
     * @param pb
     * @param storeId
     * @param attrName
     * @param attrValue
     * @return
     * @throws ParseException
     */
    @RequestMapping("queryThirdReportBackOrderCateDetail")
    public ModelAndView queryThirdReportBackOrderCateDetail(PageBean pb,Long storeId,Long orderId,String attrName,String attrValue) throws ParseException {
        pb.setUrl("queryReportCate.htm");
        Map<String, String> dateMap = new HashMap<String, String>();
        dateMap.put("today", DateUtil.dateToString(new Date()));
        dateMap.put("recentOneMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),1));
        dateMap.put("recentThreeMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),3));
        dateMap.put("recentSixMonth", DateUtil.getDateOfLastNMonth(DateUtil.dateToString(new Date()),6));
        return new ModelAndView(ReportPath.BACKTHIRDREPORTDETAIL)
                .addObject("pb", reportService.selectReportBackOrderCateDdetailList(storeId,orderId, pb))
                .addObject("store", auditService.selectByCustomerId(storeId))
                .addObject("storeId", storeId)
                .addObject("dateMap", dateMap)
                .addObject("attrName",attrName)
                .addObject("attrValue", attrValue);
    }

    /**
     * 查询报表详情
     * @param reportId   报表id
     * @return
     */
    @RequestMapping("thirdReportDetail")
    public ModelAndView thirdReportDetail(Long reportId) {
        //重定向到要跳转的页面 装载以及根据报表ID获取的单个的报表对象
        return new ModelAndView(ReportPath.THIRDREPORTDETAIL).addObject("report", reportService.select(reportId));
    }
    
    /**
     * 导出第三方报表（根据条件）
     * @param report    报表查询参数
     * @param month 月份 yyyy-MM
     * @param request
     * @param response
     */
    
    @RequestMapping("exportThirdReport")
    public void exportThirdReport(Report report,String month ,HttpServletRequest request,HttpServletResponse response) {
        //判断是否按照月份进行筛选
        if(month!=null && !"".equals(month)) {
            //设置起始月份
            report.setStartDate(month+"-01");
            //设置终止月份
            report.setEndDate(DateUtil.getDateOfLastNMonth(month+"-01", 0));
        }
        //按结束时间查询 时间类型，查询使用，0按开始时间/1结束时间*/
        report.setTimeType("1");
        //获取商家ID
        report.setStoreId((Long) request.getSession().getAttribute("thirdId"));
        //导出类目报表
        reportService.exportCateReport(report, response);
    }
    
}
