package com.ningpai.customer.controller;

import com.ningpai.customer.bean.ChargeWithdraw;
import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.bean.TradeInfo;
import com.ningpai.customer.service.ChargeWithdrawService;
import com.ningpai.customer.service.DepositInfoService;
import com.ningpai.customer.service.TradeInfoService;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.deposit.bean.Trade;
import com.ningpai.deposit.bean.TradeStatus;
import com.ningpai.deposit.service.WithdrawService;
import com.ningpai.manager.bean.Manager;
import com.ningpai.manager.mapper.ManagerMapper;
import com.ningpai.other.util.CustomerConstantStr;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import com.ningpai.util.UploadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 会员提现Controller
 * Created by CHENLI on 2016/10/8.
 */
@Controller
public class ChargeWithdrawController {
    private static final String PAGEBEAN = "pageBean";
    /**
     * 日志
     */
    public static final MyLogger LOGGER = new MyLogger(ChargeWithdrawController.class);

    @Autowired
    private ChargeWithdrawService chargeWithdrawService;
    @Autowired
    private TradeInfoService tradeInfoService;
    @Autowired
    private DepositInfoService depositInfoService;

    @Autowired
    private WithdrawService withdrawService;
    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 查询会员提现记录列表
     * @param request
     * @param depositInfo
     * @param pageBean
     * @return
     */
    @RequestMapping("/initChargeWithdrawList")
    public ModelAndView selectChargeWithdrawList(HttpServletRequest request, DepositInfoVo depositInfo, PageBean pageBean){
        String status = "";
        try {
            depositInfo.setOrderStatus(null);
            status = request.getParameter("status");
            if(StringUtils.isNotEmpty(status)){
                depositInfo.setOrderStatus(status);
            }
            pageBean.setUrl(CustomerConstantStr.INITCHARGEWITHDRAWLIST);
            LOGGER.info("查询会员提现记录！");
            pageBean = chargeWithdrawService.selectChargeWithdrawList(depositInfo, pageBean);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return new ModelAndView(CustomerConstantStr.INITCHARGEWITHDRAWJSP, PAGEBEAN, pageBean).addObject("status",status);
    }

    /**
     * 批量修改状态，通过提现单
     * @param request
     */
    @ResponseBody
    @RequestMapping("/batchUpdateChargeWithdrawStatus")
    public int batchUpdateChargeWithdrawStatus(HttpServletRequest request){
        int result = 0 ;
        try {
            String orderStatus = request.getParameter("orderStatus");
            String sendBack = request.getParameter("sendBack");
            String[] tags = request.getParameterValues("withdrawIds[]");
//            String username = request.getSession().getAttribute("name").toString();
            Long tradeInfoId;
            if(orderStatus.equals("1")){
                //打回，改变提现单状态，把冻结的资金解冻
                if(tags.length>0&&tags !=null){
                    for(int i=0;i< tags.length;i++){
                        tradeInfoId = Long.valueOf(tags[i]);
                        TradeInfo tradeInfo = tradeInfoService.getTradeInfoById(tradeInfoId);
                        tradeInfo.setOrderStatus(orderStatus);
                        tradeInfo.setReviewedRemark(sendBack);
                        tradeInfo.setPreOrderStatus(TradeStatus.PENDING);
//                        withdrawService.updateByIdAndStatus(trade,tradeInfoId, TradeStatus.PENDING);

                        result = tradeInfoService.updateTradeInfo(tradeInfo);
                        if(result == 1){
                            //打回修改会员预存款资金
                            result = updateDepositInfo(tradeInfo);
                        }
                    }
                }
            }else{//通过只需要该状态
                Long [] withdrawIds = null;
                if(tags.length>0&&tags !=null){
                    withdrawIds=new Long[tags.length];
                    for(int i=0;i< tags.length;i++){
                        withdrawIds[i]=Long.valueOf(tags[i]);
                    }
                }
                TradeInfo tradeInfo = new TradeInfo();
                tradeInfo.setOrderStatus(orderStatus);
                tradeInfo.setTradeInfoIds(withdrawIds);
                tradeInfo.setReviewedRemark(sendBack);
                result = tradeInfoService.batchUpdateTradeInfo(tradeInfo);
            }
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 单条修改状态，打回提现单
     * @param request
     */
    @ResponseBody
    @RequestMapping("/updateChargeWithdrawStatus")
    public int updateChargeWithdrawStatus(HttpServletRequest request){
        int result = 0;
        try {
            String orderStatus = request.getParameter("orderStatus");
            String sendBack = request.getParameter("sendBack");
            String tradeInfoId = request.getParameter("tradeInfoId");

            TradeInfo tradeInfo = tradeInfoService.getTradeInfoById(Long.parseLong(tradeInfoId));
            tradeInfo.setOrderStatus(orderStatus);
            tradeInfo.setReviewedRemark(sendBack);
            tradeInfo.setPreOrderStatus(TradeStatus.PENDING);
            if(orderStatus.equals("1")){
                result = tradeInfoService.updateTradeInfo(tradeInfo);
                if(result == 1){
                    //打回修改会员预存款资金
                    result = updateDepositInfo(tradeInfo);
                }
            }else{
                result = tradeInfoService.updateTradeInfo(tradeInfo);
            }
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 通过提现ID查询提现信息
     * @param withdrawId
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectChargeWithdrawById")
    public ChargeWithdraw selectChargeWithdrawById(Long withdrawId){
        ChargeWithdraw chargeWithdraw = null;
        try {
            chargeWithdraw = chargeWithdrawService.selectChargeWithdrawById(withdrawId);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return chargeWithdraw;
    }

    /**
     * 修改打款
     * @param request
     * @param chargeWithdraw
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateWithdrawPayStatus")
    public int updateWithdrawPayStatus(MultipartHttpServletRequest request, @Valid ChargeWithdraw chargeWithdraw){
        LOGGER.debug("==========线下打款==========");
        int result = 0;
        try {
            TradeInfo tradeInfo = tradeInfoService.getTradeInfoById(chargeWithdraw.getTradeInfoId());
            // 待文件上传区
            MultipartFile file = request.getFile("payImg");
            // 若有数据则上传文件
            if (file != null && !file.isEmpty()) {
                tradeInfo.setCertificateImg(UploadUtil.uploadFileOne(file, request));
            }
            tradeInfo.setPayBillNum(chargeWithdraw.getPayBillNum());
            tradeInfo.setPayRemark(chargeWithdraw.getPayRemark());
            tradeInfo.setOrderStatus("3");//已打款待用户确认
            tradeInfo.setPreOrderStatus(TradeStatus.AGREE);
            result = tradeInfoService.updateTradeInfo(tradeInfo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    public int updateDepositInfo(TradeInfo tradeInfo){
        int result;
        DepositInfo depositInfo = depositInfoService.selectDepositByCustId(tradeInfo.getCustomerId());
        depositInfo.setPreDeposit(depositInfo.getPreDeposit().add(tradeInfo.getOrderPrice()));
        depositInfo.setFreezePreDeposit(depositInfo.getFreezePreDeposit().subtract(tradeInfo.getOrderPrice()));
        result = depositInfoService.updateDeposit(depositInfo);
        return result;
    }

    public Long getManagerId(String userName){
        Long managerId = null;
        Manager manager = managerMapper.selectByName(userName);
        if (manager != null){
            managerId = manager.getId();
        }
        return managerId;
    }
}
