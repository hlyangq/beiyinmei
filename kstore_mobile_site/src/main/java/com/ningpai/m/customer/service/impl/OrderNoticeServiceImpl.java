/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.m.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ningpai.m.customer.bean.OrderNotice;
import com.ningpai.m.customer.mapper.OrderNoticeMapper;
import com.ningpai.m.customer.service.OrderNoticeService;
import com.ningpai.m.customer.vo.CustomerConstants;
import com.ningpai.util.PageBean;

/**
 * 手机端订单消息通知service实现
 * @see com.ningpai.m.customer.service.OrderNoticeService
 * @author QIANMI-ZHANGWENCHANG
 * @since 2015年10月20日
 */
@Service("orderNoticeServiceM")
public class OrderNoticeServiceImpl implements OrderNoticeService {
    /**
     * 注入dao层接口
     */
    @Resource(name="orderNoticeMapper")
    private OrderNoticeMapper orderNoticeMapper;
    
    /*
     * 查询订单通知消息
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.OrderNoticeService#selectBrowserecord(java.lang.Long, com.ningpai.util.PageBean)
     */
    @Override
    public PageBean selectOrderNotice(Long customerId, PageBean pb) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("customerId", customerId);
        //查询数量
        Long rows = orderNoticeMapper.queryNoticeCount(paramMap) ;
        if(rows!=null && rows>0){
            pb.setRows(rows.intValue());
        }else{
            pb.setRows(0);
        }
        pb.setPageSize(5);
        paramMap.put(CustomerConstants.STARTNUM, pb.getStartRowNum());
        paramMap.put(CustomerConstants.ENDNUM, pb.getEndRowNum());
        List<Object> oederNoticeList = this.orderNoticeMapper.queryOrderNotice(paramMap);
        pb.setList(oederNoticeList);
        return pb;
    }

    /*
     * 查询未读订单通知数量
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.OrderNoticeService#getIsNotReadCount(java.lang.Long)
     */
    @Override
    public Long getIsNotReadCount(Long customerId) {
        return this.orderNoticeMapper.selectNotRead(customerId);
    }

    /*
     * 标记为已读
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.OrderNoticeService#readNotice(java.lang.Long)
     */
    @Override
    public Long readNotice(Long noticeId) {
        OrderNotice record = new OrderNotice();
        record.setNoticeId(noticeId);
        record.setIsRead("1");
        return (long) this.orderNoticeMapper.readedNotice(record);
    }

    /*
     * 添加通知消息
     * (non-Javadoc)
     * @see com.ningpai.m.customer.service.OrderNoticeService#addNotice(com.ningpai.m.customer.bean.OrderNotice)
     */
    @Override
    public int addNotice(OrderNotice record) {
        return this.orderNoticeMapper.insertSelective(record);
    }

    /**
     * 查询所有订单消息
     * @param customerId
     *            会员编号
     * @param pb
     *         分页
     * @return
     */
    @Override
    public List<Object> selectOrderNotices(Long customerId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("customerId", customerId);
        return this.orderNoticeMapper.selectListNoPage(paramMap);
    }


}
