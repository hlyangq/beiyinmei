/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
/**
 * 
 */
package com.ningpai.m.catebar.controller;

import com.ningpai.m.common.service.SeoService;
import com.ningpai.mobile.bean.MobCateBar;
import com.ningpai.mobile.service.MobCateBarService;
import com.ningpai.mobile.vo.MobCateBarVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 控制器-移动版分类导航
 * 
 * @author NINGPAI-WangHaiYang
 * @since 2014年8月20日下午7:44:20
 */
@Controller
public class MobCateBarSiteController {

    @Resource(name = "MobCateBarService")
    private MobCateBarService mobCateBarService;
    @Resource(name = "SeoService")
    private SeoService seoService;

    /**
     * 获取所有未删除、已启用的移动版分类导航
     *
     * @return
     */
    @RequestMapping("/queryMobCateBar")
    public ModelAndView queryMobCateBar(Long mobcatebarId) {
        //记录分类编号
        Long cateId=null;
        //子分类集合
        List<MobCateBarVo> cateBarVos=null;
        //一级分类集合
        List<MobCateBar> cates = this.mobCateBarService.selectOneMobCate();
        if(cates!=null){
            if (mobcatebarId == null) {
                //默认第一个分类的编号
                cateId = cates.get(0).getCateBarId();
            } else {
                //赋值
                cateId = mobcatebarId;
            }
            //查询子分类集合
            cateBarVos = mobCateBarService.queryUsingMobCateBar(cateId);
        }

        return seoService.getCurrSeo(new ModelAndView("catebar/cates", "catebars", cates).addObject("cateBarVos", cateBarVos).addObject("cateId", cateId));
    }

}