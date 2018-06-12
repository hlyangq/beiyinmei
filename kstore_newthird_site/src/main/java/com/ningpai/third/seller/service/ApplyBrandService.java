package com.ningpai.third.seller.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ningpai.third.seller.bean.ApplyBrand;
import com.ningpai.util.PageBean;

/**
 * 申请品牌
 * 
 * @author ggn
 * 
 */
public interface ApplyBrandService {
    /**
     * 获取自定义品牌的集合
     * @param attribute
     * @return
     */
    List<ApplyBrand> selectApplyBrand(Long attribute);

    /**
     * 删除
     * 
     * @param applyBrandId
     * @return int
     */
    int delApplyBrand(Long applyBrandId, Long thirdId);

    /**
     * 根据自定义品牌id和商家id查询自定义品牌详情
     * @param applyBrandId 自定义品牌id
     * @param thirdId 商家id
     * @return 自定义品牌详情
     */
    ApplyBrand selectApplyBrandByIds(Long applyBrandId, Long thirdId);


    /**
     * 添加
     * 
     * @param request
     * @param applyBrand
     * @return ApplyBrand
     */
    ApplyBrand addApplyBrand(MultipartHttpServletRequest request, ApplyBrand applyBrand);

    /**
     * 查询自定义品牌列表
     * @param pb
     * @param thirdId
     * @return
     */
    PageBean queryApplyBrand(PageBean pb,Long thirdId);
    
    /**
     * 批量删除
     * @param applyBrandIds
     * @return
     */
    int delApplyBrands(Long[] applyBrandIds,Long thirdId);

    /**
     * 根据自定义品牌名称修改商品品牌状态
     * 第三方后台删除自定义品牌时，如果是boss后台已经审核通过的，则把审核通过后goods_brand表里的数据也删除
     * @param applyBrandName 自定义品牌名称
     * @return 修改结果
     */
    int updateGoodsBrandByName(String applyBrandName);
}
