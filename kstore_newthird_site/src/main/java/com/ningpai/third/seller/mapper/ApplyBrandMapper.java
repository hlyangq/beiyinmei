package com.ningpai.third.seller.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ningpai.third.seller.bean.ApplyBrand;

/**
 * 自定义品牌接口
 */
public interface ApplyBrandMapper {

    /**
     * 查询申请的品牌
     * 
     * @param thirdId
     * @return List
     */
    List<ApplyBrand> selectApplyBrand(Long thirdId);

    /**
     * 删除自定义品牌
     * 
     * @param map
     * @return int
     */
    int delApplyBrand(Map<String, Object> map);

    /**
     * 根据自定义品牌id和商家id查询自定义品牌详情
     * @param map 自定义品牌id和商家id
     * @return 自定义品牌详情
     */
    ApplyBrand selectApplyBrandByIds(Map<String, Object> map);

    /**
     * 添加自定义品牌
     * 
     * @param applyBrand
     * @return int
     */
    int addApplyBrand(ApplyBrand applyBrand);

    /**
     * 
     * 查询最新Id
     * 
     * @return Long
     */
    Long selectLastId();
    
    /**
     * 查询自定义品牌数量
     * @param pmap
     * @return
     */
    int queryApplyBrandCount(Map<String, Object> pmap);
    
    /**
     * 查询自定义品牌列表
     * @param pmap
     * @return
     */
    List<Object> queryApplyBrand(Map<String, Object> pmap);
    
    /**
     * 批量删除自定义品牌
     * @param map
     * @return
     */
    int delApplyBrands(Map<String, Object> map);

    /**
     * 第三方与品牌的绑定关联
     * @param map
     * @return
     */
    int updateThirdApplyBrand(Map<String, Object> map);

    /**
     * 根据自定义品牌名称修改商品品牌状态
     * 第三方后台删除自定义品牌时，如果是boss后台已经审核通过的，则把审核通过后goods_brand表里的数据也删除
     * @param applyBrandName 自定义品牌名称
     * @return 修改结果
     */
    int updateGoodsBrandByName(String applyBrandName);
}
