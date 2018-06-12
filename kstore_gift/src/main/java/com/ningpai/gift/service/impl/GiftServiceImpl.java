/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.gift.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.ningpai.gift.bean.GiftPic;
import com.ningpai.goods.bean.InfoImageManage;
import com.ningpai.goods.dao.ImageSetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ningpai.gift.bean.Gift;
import com.ningpai.gift.dao.GiftMapper;
import com.ningpai.gift.dao.GiftPicMapper;
import com.ningpai.gift.service.GiftService;
import com.ningpai.util.MapUtil;
import com.ningpai.util.PageBean;

/**
 * @author ggn 赠品service实现类
 */
@Service("GiftService")
public class GiftServiceImpl implements GiftService {

    private GiftMapper giftMapper;

    private GiftPicMapper giftPicMapper;

    // 图片规则DAO
    @Resource(name = "GoodsImageSetMapper")
    private ImageSetMapper imageSetMapper;


    /*
     * 
     * 
     * @see
     * com.ningpai.gift.service.GiftService#searchGiftList(com.ningpai.gift.
     * bean.Gift, com.ningpai.util.PageBean)
     */
    @Override
    public PageBean searchGiftList(Gift gift, PageBean pageBean) {
        gift.setDelFlag("0");
        // 分装实体类属性
        Map<String, Object> paramMap = MapUtil.getParamsMap(gift);
        // 查询总数
        pageBean.setRows(giftMapper.searchGiftListCount(paramMap));
        // 计算分页
        Integer no = pageBean.getRows() % pageBean.getPageSize() == 0 ? pageBean
                .getRows() / pageBean.getPageSize()
                : (pageBean.getRows() / pageBean.getPageSize() + 1);
        no = no == 0 ? 1 : no;
        if (pageBean.getPageNo() >= no) {
            pageBean.setPageNo(no);
            pageBean.setStartRowNum((no - 1) * pageBean.getPageSize());
            pageBean.setObjectBean(gift);
        }
        // 查询条件封装
        paramMap.put("start", pageBean.getStartRowNum());
        paramMap.put("number", pageBean.getEndRowNum());
        try {
            // 查询列表页
            pageBean.setList(giftMapper.searchGiftList(paramMap));
        } finally {
            paramMap = null;
        }
        return pageBean;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.gift.service.GiftService#doAddgift(com.ningpai.gift.bean.
     * Gift, java.util.List)
     */
    @Override
    @Transactional
    public int doAddGift(Gift gift) {
        int flag = 0;
        gift.setCreateTime(new Date());
        gift.setModifyTime(new Date());
        gift.setDelFlag("0");
        // 插入赠品
        flag = giftMapper.doAddGift(gift);
        if(flag > 0){
            if(gift.getPicUrl()!=null && !"".equals(gift.getPicUrl())){
                GiftPic image = image = new GiftPic();
                image.setDelFlag("0");
                image.setPicUrl(gift.getPicUrl());
                image.setGiftId(gift.getGiftId());
                if(gift.getPicUrl().indexOf(".com") != -1){
                    //设置大图
                    image.setPicBig(gift.getPicUrl()+"!352");
                    //设置中图
                    image.setPicMiddle(gift.getPicUrl()+"!160");
                    //设置小图
                    image.setPicLittle(gift.getPicUrl()+"!56");
                }else{
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("url", gift.getPicUrl());
                    InfoImageManage infoImageManage = imageSetMapper
                            .queryImageByUrl(paramMap);
                    image.setPicBig(infoImageManage.getBigImgUrl());
                    //设置中图
                    image.setPicMiddle(infoImageManage.getMiddleImgUrl());
                    //设置小图
                    image.setPicLittle(infoImageManage.getSmallImgUrl());
                }
                giftPicMapper.savePic(image);
            }
        }
       return flag;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.gift.service.GiftService#selectGiftDetailById(java.lang.Long)
     */
    @Override
    public Gift selectGiftDetailById(Long giftId) {
        Gift gift = giftMapper.selectGiftDetailById(giftId);
        gift.setGiftPicList(giftPicMapper.selectGiftPicByGiftId(giftId));
        return gift;
    }

    /*
     * 
     * 
     * @see
     * com.ningpai.gift.service.GiftService#doUpdateGift(com.ningpai.gift.bean
     * .Gift, java.util.List)
     */
    @Override
    @Transactional
    public int doUpdateGift(Gift gift) {
        gift.setModifyTime(new Date());
        return giftMapper.doUpdateGift(gift);
    }

    /*
     * 
     * 
     * @see com.ningpai.gift.service.GiftService#delGift(java.lang.Long)
     */
    @Override
    @Transactional
    public int delGift(Long giftId) {
        // 删除赠品
        int delFlag = giftMapper.delGift(giftId);
        if (delFlag == 1) {
            // 删除赠品图片
            giftPicMapper.deleteGiftPicByGiftId(giftId);
        }
        return delFlag;
    }

    /*
     * 
     * 
     * @see com.ningpai.gift.service.GiftService#delAllGift(java.lang.Long[])
     */
    @Override
    @Transactional
    public int delAllGift(Long[] giftId) {
        List<Long> list = new ArrayList<Long>();
        for (Long id : giftId) {
            list.add(id);
        }
        // 批量删除赠品
        int delFlag = giftMapper.delAllGift(list);
        if (delFlag > 0) {
            // 批量删除赠品图片
            giftPicMapper.deleteAllGiftPicByGiftId(list);
        }
        return delFlag;
    }

    public GiftMapper getGiftMapper() {
        return giftMapper;
    }

    @Resource(name = "GiftMapper")
    public void setGiftMapper(GiftMapper giftMapper) {
        this.giftMapper = giftMapper;
    }

    public GiftPicMapper getGiftPicMapper() {
        return giftPicMapper;
    }

    @Resource(name = "GiftPicMapper")
    public void setGiftPicMapper(GiftPicMapper giftPicMapper) {
        this.giftPicMapper = giftPicMapper;
    }

}
