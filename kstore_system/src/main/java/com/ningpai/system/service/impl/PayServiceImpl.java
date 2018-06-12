/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.system.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.ningpai.system.bean.Pay;
import com.ningpai.system.dao.PayMapper;
import com.ningpai.system.service.PayService;
import com.ningpai.system.util.SelectBean;
import com.ningpai.util.MyLogger;
import com.ningpai.util.PageBean;

/**
 * 支付服务层实现类
 * 
 * @author NINGPAI-LiHaoZe
 * @since 2013年12月19日 上午9:38:35
 * @version 1.0
 */
@Service("payService")
public class PayServiceImpl implements PayService {

    /** 日志记录 */
    private static final MyLogger LOGGER = new MyLogger(PayServiceImpl.class);

    /** 标记值为0 */
    private String zero = "0";

    /** 标记值为1 */
    private String one = "1";

    /** 结束行数 */
    private static final int ENDROWNUMS = 10000;

    private static final String STARTROWNUM = "startRowNum";
    private static final String ENDROWNUM = "endRowNum";
    private static final String CONDITION = "condition";

    /** 支付服务层dao */
    @Resource(name = "payMapper")
    private PayMapper payMapper;

    /**
     * 根据主键删除支付接口
     */
    @Override
    public void deletePaySetById(Long payId) {
        payMapper.deleteByPrimaryKey(payId);
    }

    /**
     * 分页查询支付方式
     * 
     * @param pageBean
     * @return PageBean
     */
    public PageBean findByPageBean(PageBean pageBean, SelectBean selectBean) {
        // 查询总行数
        pageBean.setRows(payMapper.findTotalCount(selectBean));
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put(STARTROWNUM, pageBean.getStartRowNum());
        maps.put(ENDROWNUM, pageBean.getEndRowNum());
        maps.put(CONDITION, selectBean.getCondition());
        maps.put("searchText", selectBean.getSearchText());
        // 设置分页列表
        pageBean.setList(payMapper.findByPageBean(maps));
        return pageBean;
    }

    /**
     * 添加支付方式
     */
    public int insertPay(Pay pay) {
        // 标记为未删除
        pay.setDelFlag(zero);

        // 判断添加的支付方式是否为默认
        if (pay.getPayDefault().equals(one)) {
            // 查找有无默认的支付方式
            Pay pa = payMapper.selectByDefault(one);
            // 若有，则将默认方式改为“0”
            if (pa != null) {
                pa.setPayDefault(zero);
                payMapper.updateByPrimaryKeySelective(pa);
            }
        }

        return payMapper.insertSelective(pay);
    }

    /**
     * 删除支付方式
     */
    public int deletePay(String[] payIds) {

        int count = 0;
        // 循环删除支付信息
        for (String id : payIds) {
            count += payMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        return count;
    }

    /**
     * 根据payId查询支付信息
     * 
     * @param payId
     * @return
     */
    public Pay findByPayId(Long payId) {

        return payMapper.selectByPrimaryKey(payId);
    }

    /**
     * 修改支付设置信息
     */
    public Map updatePay(Pay pay) {
//    public int updatePay(Pay pay) {
        Map resultMap = new HashedMap();
        int result = 0;
        String msg = "修改成功";
        pay.setModifyTime(new Date());
        List<Pay> openList;
        // 判断添加的支付方式是否为默认
        try{
            //禁用
            openList = payMapper.selectOpenList(one);
            if(pay.getIsOpen().equals(zero)){
                if(openList != null && openList.size() == 1){
                    //已经是最后一个启用的支付方式了，要提示不能禁用，必须启用一个支付方式
                    msg = "必须启用一个支付方式";
                }else{
                    pay.setPayDefault(zero);
                    //是否有默认的支付方式
                    Pay payDefault = payMapper.selectByDefault(one);
                    if(payDefault != null){
                        if(pay.getPayId() == payDefault.getPayId()){
                            for (Pay payOpen : openList){
                                if(payOpen.getPayId() != pay.getPayId()){
                                    payOpen.setPayDefault(one);
                                    result = payMapper.updateByPrimaryKeySelective(payOpen);
                                    result = payMapper.updateByPrimaryKeySelective(pay);
                                    break;
                                }
                            }
                        }else{
                            result = payMapper.updateByPrimaryKeySelective(pay);
                        }
                    }else{
                        msg="请设置一个默认的支付方式";
                    }
                }
            }else{//启用
                if(pay.getPayDefault().equals(one)){
                    // 查找有无默认的支付方式
                    Pay pa = payMapper.selectByDefault(one);
                    // 若有，则将默认方式改为“0”
                    if (pa != null) {
                        pa.setPayDefault(zero);
                        result = payMapper.updateByPrimaryKeySelective(pa);
                    }
                    result = payMapper.updateByPrimaryKeySelective(pay);
                }else{//取消设置默认
                    if(openList != null && openList.size() == 1){
                        //已经是最后一个启用的支付方式了，要提示不能禁用，必须启用一个支付方式
                        msg = "必须设置一个默认的支付方式";
                    }else{
                        pay.setPayDefault(zero);
                        //是否有默认的支付方式
                        Pay payDefault = payMapper.selectByDefault(one);
                        if(payDefault != null){
                            if(pay.getPayId() == payDefault.getPayId()){
                                for (Pay payOpen : openList){
                                    if(payOpen.getPayId() != pay.getPayId()){
                                        payOpen.setPayDefault(one);
                                        result = payMapper.updateByPrimaryKeySelective(payOpen);
                                        result = payMapper.updateByPrimaryKeySelective(pay);
                                        break;
                                    }
                                }
                            }else{
                                result = payMapper.updateByPrimaryKeySelective(pay);
                            }
                        }else{
                            msg="请设置一个默认的支付方式";
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("修改支付方式错误：",e);
        }
        resultMap.put("result",result);
        resultMap.put("msg",msg);

        return resultMap;
    }

    /**
     * 查询所有的支付方式
     * 
     * @see com.ningpai.system.service.PayService#queryAllPaySet()
     */
    public List<Object> queryAllPaySet() {
        // 查询总行数
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put(STARTROWNUM, 0);
        maps.put(ENDROWNUM, ENDROWNUMS);
        maps.put(CONDITION, -1);
        // 返回查询到的列表
        List<Object> objectList = payMapper.findByPageBean(maps);
        Collections.sort(objectList, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Pay) o1).getPayDefault().compareTo(((Pay) o2).getPayDefault());
            }
        });
        Collections.reverse(objectList);
        return objectList;
    }

    /**
     * 根据ID修改支付接口启用状态<br/>
     * 已启用改为不启用，不启用改为已启用
     * 
     * @see com.ningpai.system.service.PayService#updateUserdStatus(java.lang.Long)
     */
    @Override
    public boolean updateUserdStatus(Long payId) {
        Pay pay = this.payMapper.selectByPrimaryKey(payId);
        if ("0".equals(pay.getIsOpen())) {
            pay.setIsOpen("1");
        } else {
            pay.setIsOpen("0");
        }
        pay.setModifyTime(new Date());
        return this.payMapper.updateByPrimaryKeySelective(pay) > 0;
    }

    /**
     * 根据ID修改为默认支付方式<br/>
     * 其他支付接口设置取消默认
     * 
     * @see com.ningpai.system.service.PayService#changeDefault(java.lang.Long)
     */
    @Override
    public boolean changeDefault(Long payId) {
        try {
            this.payMapper.changeDefaultToNO(payId);
            Pay pay = this.payMapper.selectByPrimaryKey(payId);
            pay.setPayDefault("1");
            pay.setModifyTime(new Date());
            return this.payMapper.updateByPrimaryKeySelective(pay) > 0;
        } catch (Exception e) {
            LOGGER.error("修改默认支付方式错误：=>" ,e);
            return false;
        }
    }

    /**
     * 分页查询支付方式为微信支付的数据
     */
    @Override
    public PageBean findPayByPayType(PageBean pageBean, SelectBean selectBean) {
        // 查询总行数
        pageBean.setRows(payMapper.findTotalCountByPayType(selectBean));
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put(STARTROWNUM, pageBean.getStartRowNum());
        maps.put(ENDROWNUM, pageBean.getEndRowNum());
        maps.put(CONDITION, selectBean.getCondition());
        maps.put("searchText", selectBean.getSearchText());
        // 设置分页列表
        pageBean.setList(payMapper.findPayByPayType(maps));
        return pageBean;
    }

    /**
     * 修改支付问题描述
     */
    @Override
    public int updatePayHelp(Pay pay) {
        return payMapper.upadtePayHelp(pay);
    }

    /**
     * 查询移动端充值支付方式
     *
     * @return
     */
    @Override
    public List<Pay> selectMobilePay() {
        return payMapper.selectMobilePay();
    }

    /**
     * 查询微信的支付接口
     *
     * @return
     */
    @Override
    public Pay selectWxPay() {
        return payMapper.selectWxPay();
    }

    /**
     * 根据支付类型查询支付方式
     * @param type 支付类型
     * @return
     */
    @Override
    public Pay selectPayByType(String type) {
        return payMapper.selectPayByType(type);
    }
}
