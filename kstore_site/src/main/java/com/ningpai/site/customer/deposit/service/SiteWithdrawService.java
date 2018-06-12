package com.ningpai.site.customer.deposit.service;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.customer.bean.Customer;

import com.ningpai.deposit.bean.*;
import com.ningpai.deposit.bean.Withdraw;
import com.ningpai.deposit.mapper.WithDrawMapper;
import com.ningpai.deposit.service.WithdrawService;
import com.ningpai.site.customer.deposit.bean.*;
import com.ningpai.site.customer.service.CustomerServiceInterface;
import com.ningpai.util.PageBean;
import com.ningpai.util.UtilDate;
import com.ningpai.util.web.ReturnJsonBuilder;
import com.ningpai.util.web.ReturnJsonBuilderFactory;
import com.ningpai.utils.SecurityUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youzipi on 16/10/8.
 */
@Service
public class SiteWithdrawService extends WithdrawService {
    private Logger LOGGER = Logger.getLogger(SiteWithdrawService.class);


    @Autowired
    private WithDrawMapper mapper;

    @Autowired
    private SiteDepositService depositService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private BankService bankService;


    @Autowired
    private CustomerServiceInterface customerService;


    public Withdraw findById(Long tradeId) {
        return mapper.selectByPrimaryKey(tradeId);
    }

    @Transactional
    public JSONObject create(WithdrawVo vo) throws IllegalArgumentException {

        JSONObject returnVal = new JSONObject();

        Long customerId = vo.getCustomerId();
        Customer customer = customerService.queryCustomerById(customerId);
        String encodePwd = SecurityUtil.getStoreLogpwd(customer.getUniqueCode(), vo.getPayPwd(), customer.getSaltVal());

        BigDecimal amount = vo.getAmount();
        Deposit deposit = depositService.findByCustomerId(customerId);
        // 操作成功后 余额
        BigDecimal nextDeposit = deposit.getPreDeposit().subtract(amount);
        BigDecimal nextFrozenDeposit = deposit.getFreezePreDeposit().add(amount);
//        vo.setCurrentPrice(nextDeposit);
        vo.setCurrentPrice(deposit.getPreDeposit().add(deposit.getFreezePreDeposit()));
        String payPassword = deposit.getPayPassword();

        if (StringUtils.isBlank(payPassword)) {
            // 支付密码未设置
            return ReturnJsonBuilderFactory
                    .builder()
                    .code("1")
                    .msg(DepositConst.NOT_SET_DEPOSIT_PAY_PASSWORD)
                    .build();

        } else if (deposit.getPasswordErrorCount() >= DepositConst.ERROR_TRY_COUNT) {
            // 密码错误次数达到锁定限制
            returnVal.put("code", "2");
            //returnVal.put("msg", DepositConst.REACH_ERROR_COUNT_THRESHOLD);
            returnVal.put("msg", DepositConst.CANNOT_WITHDRAW);
            return returnVal;
        } else if (!encodePwd.equals(payPassword)) {            // 支付密码错误
            int nextErrorCount = deposit.addPwdErrorCount();
            depositService.updateErrorCount(customerId,nextErrorCount);
            returnVal.put("code", "3");
            returnVal.put("msg", MessageFormat.format(DepositConst.PIN_ERROR, 3 - nextErrorCount));
            return returnVal;
        } else if (nextDeposit.compareTo(BigDecimal.ZERO) == -1) {
            // 余额不足
            returnVal.put("code", "4");
            returnVal.put("msg", DepositConst.INSUFFICIENCE_DEPOSIT);
            return returnVal;
        }
        else if (nextFrozenDeposit.compareTo(BigDecimal.valueOf(0)) <= 0) {
            // 冻结预存款超出范围
            returnVal.put("code", "4");
            returnVal.put("msg", DepositConst.INVALID_PRICE);
            return returnVal;
        }
        else if (nextFrozenDeposit.compareTo(BigDecimal.valueOf(99999999.99)) == 1) {
            // 冻结预存款超出范围
            returnVal.put("code", "4");
            returnVal.put("msg", DepositConst.FROZNE_DEPOSIT_OUT_OF_RANGE);
            return returnVal;
        }

        Long bankId = vo.getReceivingBank();
        if (bankId != -1) {
            Bank bank = bankService.findById(bankId);
            vo.setBankName(bank.getBankName());//todo bank not exist
        }

        /**
         * 冻结预存款(取现数额)
         */
        freezeDeposit(deposit, amount);
        /**
         * 添加 trade 记录
         */
        Date createTime = new Date();
        String orderCode = saveTrade(vo, createTime);

        Trade nextTrade = tradeService.findByOrderCode(orderCode);
        Long tradeId = nextTrade.getId();
        /**
         * 添加 withdraw 记录
         */
        Withdraw withdraw = new Withdraw();
        BeanUtils.copyProperties(vo, withdraw);
        withdraw.setTradeInfoId(tradeId);
        withdraw.setCreateTime(createTime);
        save(withdraw);

        returnVal.put("code", "0");
        returnVal.put("msg", "success");
        return ReturnJsonBuilderFactory
                .builder()
                .data("tradeId", tradeId)
                .defaultVal();
    }

    private String saveTrade(WithdrawVo vo, Date createTime) {
        Long customerId = vo.getCustomerId();
        String orderCode = "W" + UtilDate.mathString(createTime) + (RandomUtils.nextInt(9999 - 1000 + 1) + 1000);

        Trade trade = new Trade();
        trade.setCustomerId(customerId);
        trade.setCreatePerson(customerId);
        trade.setOrderPrice(vo.getAmount());
        trade.setCurrentPrice(vo.getCurrentPrice());
//        trade.setDelFlag("0");
        trade.setOrderCode(orderCode);
        trade.setOrderStatus("0");
        trade.setOrderType("2");
        trade.setTradeRemark(vo.getRemark());
        trade.setCreateTime(createTime);

        tradeService.saveTrade(trade);
        return orderCode;
    }


    private int save(Withdraw withdraw) {
        return mapper.insertSelective(withdraw);
    }

    public PageBean findByCustomerIdAndStatus(WithdrawForm withdrawForm) {

        PageBean pb = new PageBean();
        pb.setPageNo(withdrawForm.getPageNo());
        pb.setPageSize(withdrawForm.getPageSize());
        HashMap<String, Object> map = new HashMap<>();
        map.put("customerId", withdrawForm.getCustomerId());
        String status = withdrawForm.getStatus();

        if (StringUtils.isNotBlank(status)) {
            if (status.equals(TradeStatus.REMITTED)) {
                map.put("statuses", new String[]{"2", "3"});
            } else {
                map.put("statuses", new String[]{status});
            }
        }
        map.put("types", new String[]{TradeConst.TYPE_WITHDRAW});
        PageBean pageBean = tradeService.pageTrade(map, pb);

        return pageBean;
    }

    public WithdrawVo findVoById(Long id,Long customerId) {
        Trade trade = tradeService.findByIdAndCustomerId(id,customerId);

        WithDrawExample example = new WithDrawExample();
        example.createCriteria().andTradeInfoIdEqualTo(id);
        Withdraw withdraw = mapper.selectByExample(example).get(0);
        WithdrawVo vo = new WithdrawVo();
        vo.setId(trade.getId());
        vo.setOrderCode(trade.getOrderCode());
        vo.setCreateTime(trade.getCreateTime());
        vo.setAccountName(withdraw.getAccountName());
        vo.setBankName(withdraw.getBankName());
        vo.setBankNo(withdraw.getBankNo());
        vo.setAmount(trade.getOrderPrice());
        vo.setRemark(trade.getTradeRemark());
        vo.setPayRemark(trade.getPayRemark());
        vo.setPayBillNum(trade.getPayBillNum());
        vo.setCertificateImg(trade.getCertificateImg());
        vo.setReviewedRemark(trade.getReviewedRemark());
        vo.setContactMobile(withdraw.getContactMobile());

        vo.setCustomerId(trade.getCustomerId());
        vo.setOrderStatus(trade.getOrderStatus());

        return vo;
    }

    /**
     * 取消提现
     *
     * @param id
     * @return
     */
    public JSONObject cancel(Long id,Long customerId) {

        Trade nextTrade = new Trade();
        nextTrade.setOrderStatus(TradeStatus.CANCELED);
        Integer result = tradeService.updateByIdAndStatus(nextTrade, id,customerId, TradeStatus.PENDING);

        if (result == 0) {
            return ReturnJsonBuilderFactory
                    .builder()
                    .code("1")
                    .msg("参数错误,操作失败")
                    .build();
        }

        Trade trade = tradeService.findById(id);
        Deposit deposit = depositService.findByCustomerId(customerId);
        unFreezeDeposit(deposit, trade.getOrderPrice());

        return new ReturnJsonBuilder()
                .code("0")
                .msg("success")
                .build();
    }

    /**
     * 确认收款
     *
     * @param id
     * @return
     */
    @Transactional
    public JSONObject confirm(Long id,Long customerId) {
// todo refactor customerId

        Trade nextTrade = new Trade();
        nextTrade.setOrderStatus(TradeStatus.CONFIRMED);
        Integer result = tradeService.updateByIdAndStatus(nextTrade, id,customerId, TradeStatus.REMITTED);



        if (result == 0) {
            return ReturnJsonBuilderFactory
                    .builder()
                    .code("1")
                    .msg("确认收款失败")
                    .build();
        }

        Trade trade = tradeService.findById(id);
        Deposit deposit = depositService.findByCustomerId(customerId);

        // 消耗 冻结预存款
        consumeDeposit(deposit, trade.getOrderPrice());

        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", TradeStatus.CONFIRMED);
        BigDecimal currentPrice = deposit.getFreezePreDeposit()
                .add(deposit.getPreDeposit())
                .subtract(trade.getOrderPrice());
        map.put("currentPrice", currentPrice);

        tradeService.updateById(map);

        return new ReturnJsonBuilder()
                .code("0")
                .msg("success")
                .build();
    }



    /**
     * 解冻预存款 【取消】
     *
     * @param deposit
     * @param amount
     */
    private Integer unFreezeDeposit(Deposit deposit, BigDecimal amount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", deposit.getId());
        paramMap.put("customerId", deposit.getCustomerId());
        paramMap.put("preDeposit", deposit.getPreDeposit().add(amount));
        BigDecimal freezePreDeposit = deposit.getFreezePreDeposit().subtract(amount);
        paramMap.put("freezePreDeposit", freezePreDeposit);
        if(freezePreDeposit.compareTo(BigDecimal.ZERO) == 0){
            paramMap.put("freezePreDeposit", "0");
        }
        return depositService.updateDeposit(paramMap);
    }

    /**
     * 冻结预存款 【提交提现申请】
     *
     * @param deposit
     * @param amount
     */
    private Integer freezeDeposit(Deposit deposit, BigDecimal amount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", deposit.getId());
        paramMap.put("customerId", deposit.getCustomerId());
        paramMap.put("preDeposit", deposit.getPreDeposit().subtract(amount));
        paramMap.put("freezePreDeposit", deposit.getFreezePreDeposit().add(amount));
        return depositService.updateDeposit(paramMap);
    }

}
