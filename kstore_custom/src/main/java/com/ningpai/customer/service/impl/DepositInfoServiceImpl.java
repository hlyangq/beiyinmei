package com.ningpai.customer.service.impl;

import com.ningpai.customer.bean.DepositInfo;
import com.ningpai.customer.dao.DepositInfoMapper;
import com.ningpai.customer.service.DepositInfoService;
import com.ningpai.customer.vo.DepositInfoVo;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 平台会员预存款service实现
 * Created by CHENLI on 2016/9/14.
 */
@Service
public class DepositInfoServiceImpl implements DepositInfoService {
    /**
     * 日志
     * */
    public static final MyLogger LOGGER = new MyLogger(DepositInfoServiceImpl.class);

    @Autowired
    private DepositInfoMapper depositInfoMapper;
    /**
     * 平台会员预存款总额
     * 如果有customerID，则查询该会员的总金额
     * 否则查询平台会员预存款总额
     */
    @Override
    public Map selectTotalDesposit() {
        Map result = null;
        LOGGER.info("查询平台会员预存款总额");
        try {
            result = depositInfoMapper.selectTotalDesposit();
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 查询会员资金信息列表总条数，用于分页
     *
     * @param depositInfo
     */
    @Override
    public Long selectTotalDespositInfo(DepositInfoVo depositInfo) {
        Long result = null;
        LOGGER.info("查询会员资金信息列表总数据条数...");
        try {
            result = depositInfoMapper.selectTotalDespositInfo(depositInfo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 查询会员资金信息列表
     *
     * @param depositInfo
     * @param pageBean
     */
    @Override
    public PageBean selectDepositInfoList(DepositInfoVo depositInfo, PageBean pageBean) {
        //总数据条数
        Long rowNos = this.selectTotalDespositInfo(depositInfo);
        pageBean.setRows(rowNos.intValue());

        depositInfo.setStartRowNum(pageBean.getStartRowNum());
        depositInfo.setEndRowNum(pageBean.getEndRowNum());
        List<Object> depositInfoList;
        try {
            LOGGER.info("查询会员资金信息列表...");
            depositInfoList = depositInfoMapper.selectDespositInfoList(depositInfo);
            if (CollectionUtils.isNotEmpty(depositInfoList)){
                pageBean.setList(depositInfoList);
            }
            pageBean.setObjectBean(depositInfo);
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
        return pageBean;
    }

    /**
     * 根据会员IDcustomerID查询单条会员信息
     *
     * @param customerId
     * @return DepositInfo
     */
    @Override
    public DepositInfo selectDepositInfoById(Long customerId) {
        LOGGER.error("根据会员ID查询单条会员信息...");
        DepositInfo depositInfo = null;
        DepositInfoVo depositInfoVo = new DepositInfoVo();
        try {
            if(customerId != null){
                depositInfoVo.setCustomerId(customerId);
                //查询一条数据
                depositInfoVo.setEndRowNum(1);
                depositInfo = depositInfoMapper.selectDepositInfoById(depositInfoVo);
            }
        }catch (Exception e){
            LOGGER.error("根据会员ID查询单条会员信息：",e);
        }
        return depositInfo;
    }

    /**
     * 根据会员ID查询会员信息，用于修改数据
     *
     * @param customerId
     * @return
     */
    @Override
    public DepositInfo selectDepositByCustId(Long customerId) {
        return depositInfoMapper.selectDepositByCustId(customerId);
    }

    /**
     * 修改会员预存款信息
     *
     * @param depositInfo
     * @return
     */
    @Override
    @Transactional
    public int updateDeposit(DepositInfo depositInfo) {
        return depositInfoMapper.updateDeposit(depositInfo);
    }
}
