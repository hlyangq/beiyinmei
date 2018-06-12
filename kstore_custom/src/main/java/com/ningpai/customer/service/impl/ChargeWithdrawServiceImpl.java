package com.ningpai.customer.service.impl;

import com.ningpai.customer.bean.ChargeWithdraw;
import com.ningpai.customer.dao.ChargeWithdrawMapper;
import com.ningpai.customer.service.ChargeWithdrawService;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员提现service实现
 * Created by CHENLI on 2016/10/8.
 */
@Service
public class ChargeWithdrawServiceImpl implements ChargeWithdrawService {
    /**
     * 日志
     * */
    public static final MyLogger LOGGER = new MyLogger(ChargeWithdrawServiceImpl.class);

    @Autowired
    private ChargeWithdrawMapper chargeWithdrawMapper;
    /**
     * 查询提现记录总数
     * @param depositInfo
     * @return
     */
    @Override
    public Long selectTotalChargeWithdraw(DepositInfoVo depositInfo) {
        Long result = null;
        LOGGER.info("查询会员提现记录列表总数据条数...");
        try {
            result = chargeWithdrawMapper.selectTotalChargeWithdraw(depositInfo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 查询会员提现记录列表
     *
     * @param depositInfo
     * @return
     */
    @Override
    public PageBean selectChargeWithdrawList(DepositInfoVo depositInfo ,PageBean pageBean) {
        //总数据条数
        Long rowNos = this.selectTotalChargeWithdraw(depositInfo);
        pageBean.setRows(rowNos.intValue());

        depositInfo.setStartRowNum(pageBean.getStartRowNum());
        depositInfo.setEndRowNum(pageBean.getEndRowNum());
        List<Object> chargeWithdrawList;
        try {
            LOGGER.info("查询会员提现记录列表...");
            chargeWithdrawList = chargeWithdrawMapper.selectChargeWithdrawList(depositInfo);
            if (CollectionUtils.isNotEmpty(chargeWithdrawList)){
                pageBean.setList(chargeWithdrawList);
            }
            pageBean.setObjectBean(depositInfo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return pageBean;
    }

    /**
     * 根据提现记录ID查询该条提现信息
     *
     * @param withdrawId
     * @return
     */
    @Override
    public ChargeWithdraw selectChargeWithdrawById(Long withdrawId) {
        LOGGER.error("根据提现记录ID查询该条提现信息...");
        ChargeWithdraw chargeWithdraw = null;
        DepositInfoVo depositInfoVo = new DepositInfoVo();
        try {
            if(withdrawId != null){
                depositInfoVo.setWithdrawId(withdrawId);
                //查询一条数据
                depositInfoVo.setEndRowNum(1);
                chargeWithdraw = chargeWithdrawMapper.selectChargeWithdrawById(depositInfoVo);
            }
        }catch (Exception e){
            LOGGER.error("根据提现记录ID查询该条提现信息：",e);
        }
        return chargeWithdraw;
    }
}
