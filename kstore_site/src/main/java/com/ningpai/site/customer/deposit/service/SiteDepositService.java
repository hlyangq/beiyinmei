package com.ningpai.site.customer.deposit.service;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.customer.bean.Customer;
import com.ningpai.deposit.bean.Deposit;
import com.ningpai.deposit.bean.Trade;
import com.ningpai.deposit.mapper.DepositMapper;
import com.ningpai.deposit.service.DepositService;
import com.ningpai.order.bean.Order;
import com.ningpai.order.service.OrderService;
import com.ningpai.order.service.SynOrderService;
import com.ningpai.site.customer.deposit.bean.DepositConst;
import com.ningpai.site.customer.deposit.bean.TradeConst;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.site.order.service.SiteOrderService;
import com.ningpai.system.bean.Receivables;
import com.ningpai.system.service.ReceivablesService;
import com.ningpai.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Yang on 2016/9/19.
 */
@Service
public class SiteDepositService extends DepositService {

    @Autowired
    private DepositMapper depositMapper;

    @Autowired
    private TradeService tradeService;

    @Resource(name = "customerServiceSite")
    private CustomerServiceInterface customerServiceInterface;

    @Resource(name = "receivablesService")
    private ReceivablesService receivablesService;

    @Resource(name = "SiteOrderService")
    private SiteOrderService siteOrderService;

    @Autowired
    OrderService orderService;

    @Resource(name = "SynOrderService")
    private SynOrderService synOrderService;

    public List<Deposit> depositList(Map<String, Object> paramMap) {
        checkAndCreate(paramMap);
        List<Deposit> result = depositMapper.depositList(paramMap);
        return result;
    }



    @Transactional
    public int resetPayPassword(Map<String, Object> paramMap) {
        int result = depositMapper.resetPayPassword(paramMap);
        return result;
    }

    @Transactional
    public JSONObject pay(BigDecimal totalFee, Deposit deposit, String payPassword, Long orderId, String orderCode) {

        JSONObject retjson = new JSONObject();

        JSONObject checkjson = payCheck(totalFee, deposit, payPassword, orderId, orderCode);

        String retcode = checkjson.getString("retcode");
        if ("-1".equals(retcode)) {
            return checkjson;
        }
        /*
        long customerId = deposit.getCustomerId();
        BigDecimal preDeposit = deposit.getPreDeposit();
        //获取到当前用户信息
        //Long customerId = deposit.getCustomerId();
        Trade tTrade = new Trade();
        tTrade.setCustomerId(customerId);
        tTrade.setOrderPrice(totalFee);
        tTrade.setCreatePerson(customerId);
        tTrade.setCreateTime(new Date());
        tTrade.setOrderCode(orderCode);
        //类型为订单消费
        tTrade.setOrderType(TradeConst.TYPE_ORDER_CONSUME);
        //订单为已完成，三端统一为null
        //tTrade.setOrderStatus(TradeConst.TYPE_ORDER_STATUS_DONE);
        //删除标示
        tTrade.setDelFlag(TradeConst.TYPE_ORDER_DELETE_FLAG);
        //订单来源，消费
        tTrade.setTradeSource(TradeConst.TYPE_SOURCE_CONSUME);
        //写下描述信息
        StringBuilder sb = new StringBuilder();
        //sb.append("customerId:").append(customerId);
        //sb.append("orderId:").append(orderId);
        sb.append("订单编号:").append(orderCode);
        //sb.append("orderPrice").append(totalFee);
        tTrade.setTradeRemark(sb.toString());
        //总账减去订单金额
        deposit.setPreDeposit(preDeposit.subtract(totalFee));
        //日记账的当前金额为：可用金额 + 冻结金额
        tTrade.setCurrentPrice(deposit.getPreDeposit().add(deposit.getFreezePreDeposit()));
        //更新总账
        Map<String, Object> updateParam = new HashMap<>();
        updateParam.put("customerId", customerId);
        updateParam.put("preDeposit", preDeposit.subtract(totalFee));
        updateDeposit(updateParam);
        //保存日记账
        tradeService.saveTrade(tTrade);
        */

        Order order = siteOrderService.getPayOrder(orderId);
        List<Order> orderslist = siteOrderService.getPayOrderByOldCode(order.getOrderOldCode());

        // 多笔订单支付
        if (!CollectionUtils.isEmpty(orderslist)) {
            for (int i = 0; i < orderslist.size(); i++) {
                // 多笔订单支付
                Order childOrder = orderslist.get(i);
                // 更改订单那的支付状态
                chechOrderStatus(childOrder);
            }
        }else{//单笔订单
            //siteOrderService.payOrder(order.getOrderId());//改订单状态
            /*
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("orderId", order.getOrderId());
            updateMap.put("status", "1");
            updateMap.put("payId", DepositConst.PAY_ID);
            orderService.updateOrderStatusAndPayId(updateMap);

            Receivables receivables = receivablesService.queryByOrderCode(orderCode);
            receivables.setReceivablesTime(new Date());
            receivablesService.updatePayStatus(receivables);
            */
            chechOrderStatus(order);
        }

        retjson.put("msg", "支付完成");
        retjson.put("retcode", "0");
        return retjson;
    }

    //通过订单生成tradeInfo交易记录
    @Transactional
    public void genTradeByOrder(Order order,Long customerId){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("customerId",customerId);
        Deposit deposit = getDeposit(queryMap);

        BigDecimal preDeposit = deposit.getPreDeposit();

        BigDecimal totalFee = order.getOrderPrice();
        String orderCode = order.getOrderCode();
        Trade tTrade = new Trade();
        tTrade.setCustomerId(customerId);
        tTrade.setOrderPrice(totalFee);
        tTrade.setCreatePerson(customerId);
        tTrade.setCreateTime(new Date());
        tTrade.setOrderCode(orderCode);
        //类型为订单消费
        tTrade.setOrderType(TradeConst.TYPE_ORDER_CONSUME);
        //tTrade.setOrderStatus(null);
        //订单为已完成，三端统一为null
        //tTrade.setOrderStatus(TradeConst.TYPE_ORDER_STATUS_DONE);
        //删除标示
        tTrade.setDelFlag(TradeConst.TYPE_ORDER_DELETE_FLAG);
        //订单来源，消费
        tTrade.setTradeSource(TradeConst.TYPE_SOURCE_CONSUME);
        //写下描述信息
        StringBuilder sb = new StringBuilder();
        //sb.append("customerId:").append(customerId);
        //sb.append("orderId:").append(orderId);
        sb.append("订单编号:").append(orderCode);
        //sb.append("orderPrice").append(totalFee);
        tTrade.setTradeRemark(sb.toString());
        //总账减去订单金额
        deposit.setPreDeposit(preDeposit.subtract(totalFee));
        //日记账的当前金额为：可用金额 + 冻结金额
        tTrade.setCurrentPrice(deposit.getPreDeposit().add(deposit.getFreezePreDeposit()));
        //更新总账
        Map<String, Object> updateParam = new HashMap<>();
        updateParam.put("customerId", customerId);
        updateParam.put("preDeposit", preDeposit.subtract(totalFee));
        updateDeposit(updateParam);
        //保存日记账
        tradeService.saveTrade(tTrade);
    }


    /**
     * 多笔拆单，订单确认状态
     * @param order
     */
    public void chechOrderStatus(Order order) {
        Order or = order;
        String orderCode = or.getOrderCode();
        if ("0".equals(or.getOrderStatus())) {
            //更新订单状态为已支付，同事将payId设为5
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("orderId", or.getOrderId());
            updateMap.put("status", "1");
            updateMap.put("payId", DepositConst.PAY_ID);
            orderService.updateOrderStatusAndPayId(updateMap);

            Receivables receivables = this.receivablesService.queryByOrderCode(orderCode);
            if (null != receivables) {
                // 修改订单支付状态为支付成功
                receivables.setReceivablesTime(new Date());
                this.receivablesService.updatePayStatus(receivables);
                //直营店订单短信通知
                //siteOrderService.paySuccessSendSms(or);
                // 同步E店宝
                //synOrderService.SynOrder(or.getOrderId());
            }
            genTradeByOrder(order,order.getCustomerId());
        }
    }

    public JSONObject preCheck(BigDecimal totalFee, Deposit deposit, String payPassword, Long orderId, String orderCode) {

        JSONObject retjson = new JSONObject();
        int errorCount = deposit.getPasswordErrorCount();
        String tpayPassword = deposit.getPayPassword();
        long customerId = deposit.getCustomerId();
        //余额账户的支付密码未设置
        if (org.apache.commons.lang3.StringUtils.isBlank(tpayPassword)) {
            retjson.put("retcode", "-1");
            retjson.put("predeposit", deposit.getPreDeposit());
            retjson.put("msg", DepositConst.NOT_SET_DEPOSIT_PAY_PASSWORD);
            //账户信息错误
            retjson.put("depositerror", DepositConst.NOT_SET_DEPOSIT_PAY_PASSWORD);
            retjson.put("errorcode", "1");//错误码
            return retjson;
        }
        //试错到了阈值
        else if (errorCount >= DepositConst.ERROR_TRY_COUNT) {
            retjson.put("retcode", "-1");
            retjson.put("msg", DepositConst.REACH_ERROR_COUNT_THRESHOLD);
            //密码信息错误
            retjson.put("passwordError", DepositConst.REACH_ERROR_COUNT_THRESHOLD);
            retjson.put("predeposit", deposit.getPreDeposit());
            retjson.put("orderprice", totalFee);
            retjson.put("errorcode", "2");//错误码
            return retjson;
        } else {

        }
        if (StringUtils.isBlank(orderCode)) {
            retjson.put("msg", "订单code为空");
            retjson.put("retcode", "-1");
            return retjson;
        }
        BigDecimal preDeposit = deposit.getPreDeposit();
        retjson.put("predeposit", preDeposit);
        retjson.put("orderprice", totalFee);
        //余额账户金额小于订单总金额
        if (preDeposit.compareTo(totalFee) < 0) {
            retjson.put("msg", DepositConst.INSUFFICIENCE_DEPOSIT);
            retjson.put("retcode", "-1");
        } else {
            retjson.put("msg", DepositConst.OK);
            retjson.put("retcode", "0");
        }
        return retjson;
    }

    //支付检查
    @Transactional
    public JSONObject payCheck(BigDecimal totalFee, Deposit deposit, String payPassword, Long orderId, String orderCode) {

        JSONObject retjson = new JSONObject();
        int errorCount = deposit.getPasswordErrorCount();
        //本次

        String tpayPassword = deposit.getPayPassword();
        long customerId = deposit.getCustomerId();
        //余额账户的支付密码未设置
        if (org.apache.commons.lang3.StringUtils.isBlank(tpayPassword)) {
            retjson.put("retcode", "-1");
            retjson.put("msg", DepositConst.NOT_SET_DEPOSIT_PAY_PASSWORD);
            return retjson;
        }
        //试错到了阈值
        else if (errorCount >= DepositConst.ERROR_TRY_COUNT) {
            retjson.put("retcode", "-1");
            retjson.put("msg", DepositConst.REACH_ERROR_COUNT_THRESHOLD);
            return retjson;
        } else {
            //支付密码
            Customer customer = customerServiceInterface.queryCustomerById(customerId);
            String encodePwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), payPassword, customer.getSaltVal());
            //输入的密码和数据库不一样
            if (!org.apache.commons.lang3.StringUtils.equals(encodePwd, tpayPassword)) {
                retjson.put("retcode", "-1");
                retjson.put("msg", DepositConst.PIN_ERROR);
                //密码错误的情况
                //int t_errorCount = deposit.getPasswordErrorCount();
                //t_errorCount++;
                errorCount++;
                //更新账号试错次数
                Map<String, Object> updataParam = new HashMap<>();
                updataParam.put("customerId", deposit.getCustomerId());
                updataParam.put("passwordErrorCount", errorCount);
                updateDeposit(updataParam);

                if (errorCount >= DepositConst.ERROR_TRY_COUNT) {
                    retjson.put("msg", DepositConst.REACH_ERROR_COUNT_THRESHOLD);
                } else {
                    //update deposit
                }
                int leftCount = DepositConst.ERROR_TRY_COUNT - errorCount;
                //pin码，错误信息
                if (leftCount > 0) {
                    retjson.put("msg", "支付密码错误，您还有" + leftCount + "次机会");
                }
                return retjson;
            } else {

            }
        }
        if (StringUtils.isBlank(orderCode)) {
            retjson.put("msg", "订单code为空");
            retjson.put("retcode", "-1");
        }
        BigDecimal preDeposit = deposit.getPreDeposit();
        retjson.put("predeposit", preDeposit);
        retjson.put("orderprice", totalFee);
        //余额账户金额小于订单总金额
        if (preDeposit.compareTo(totalFee) < 0) {
            retjson.put("msg", DepositConst.INSUFFICIENCE_DEPOSIT);
            retjson.put("retcode", "-1");
        } else {
            retjson.put("msg", DepositConst.OK);
        }
        return retjson;
    }


}
