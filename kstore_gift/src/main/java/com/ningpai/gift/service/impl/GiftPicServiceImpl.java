/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.gift.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ningpai.gift.bean.GiftPic;
import com.ningpai.gift.dao.GiftPicMapper;
import com.ningpai.gift.service.GiftPicService;
import com.ningpai.goods.bean.InfoImageManage;
import com.ningpai.goods.dao.ImageSetMapper;
import com.ningpai.util.UploadUtil;

/**
 * @author ggn 赠品图片接口实现类
 */
@Service("GiftPicService")
public class GiftPicServiceImpl implements GiftPicService {

    private GiftPicMapper giftPicMapper;
    
    // 图片规则DAO
    @Resource(name = "GoodsImageSetMapper")
    private ImageSetMapper imageSetMapper;

    /**
     */
    @Override
    public int delGiftPicByPicId(Long picId) {
        return giftPicMapper.delGiftPicByPicId(picId);
    }

    /**
     */
    @Override
    public List<GiftPic> selectGiftPicByGiftId(Long giftId) {
        return giftPicMapper.selectGiftPicByGiftId(giftId);
    }

    /**
     */
    @Override
    public Boolean newuploadImage(Long giftId, String[] url) {
        GiftPic image = null;
        if (null != url && url.length > 0) {
            for (int i = 0; i < url.length; i++) {
                image = new GiftPic();
                image.setDelFlag("0");
                image.setPicUrl(url[i]);
                image.setGiftId(giftId);
                if(url[i].indexOf(".com") != -1){
                    //设置大图
                    image.setPicBig(url[i]+"!352");
                    //设置中图
                    image.setPicMiddle(url[i]+"!160");
                    //设置小图
                    image.setPicLittle(url[i]+"!56");
                }else{
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    paramMap.put("url", url[i]);
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
            return true;
        } else {
            return false;
        }

    }

    @Override
    public GiftPic uploadImage(Long giftId, String attribute, List<MultipartFile> fileList, HttpServletRequest request) {

        GiftPic image = null;
        try {
            // 判断文件列表不为空
            if (null != fileList && !fileList.isEmpty()) {

                // 循环
                for (int i = 0; i < fileList.size(); i++) {
                    if (!fileList.get(i).isEmpty()) {
                        image = new GiftPic();
                        // 设置货品ID
                        image.setGiftId(giftId);
                        // 上传原图
                        Map<String, String> resultMap = UploadUtil.uploadFile(fileList.get(i), request);

                        image.setPicUrl(resultMap.get("oldimg"));
                        image.setPicLittle(resultMap.get("0"));
                        image.setPicMiddle(resultMap.get("1"));
                        image.setPicBig(resultMap.get("2"));
                        image.setDelFlag("0");
                        image.setPicId(giftPicMapper.savePic(image));
                    }
                }
            }
            return image;
        } finally {
            image = null;
        }
    }

    public GiftPicMapper getGiftPicMapper() {
        return giftPicMapper;
    }

    @Resource(name = "GiftPicMapper")
    public void setGiftPicMapper(GiftPicMapper giftPicMapper) {
        this.giftPicMapper = giftPicMapper;
    }

}
