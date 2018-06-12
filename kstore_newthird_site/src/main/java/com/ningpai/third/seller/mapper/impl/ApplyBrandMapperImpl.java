package com.ningpai.third.seller.mapper.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.third.seller.bean.ApplyBrand;
import com.ningpai.third.seller.mapper.ApplyBrandMapper;

/**
 * 自定义品牌的接口实现类
 */
@Repository("ApplyBrandMapper")
public class ApplyBrandMapperImpl extends BasicSqlSupport implements ApplyBrandMapper {

    /**
     * 查询申请的自定义品牌
     * @param thirdId 商家ID
     * @return
     */
    @Override
    public List<ApplyBrand> selectApplyBrand(Long thirdId) {
        return this.selectList("com.ningpai.third.seller.mapper.ApplyBrandMapper.selectApplyBrand", thirdId);
    }

    /**
     * 批量删除自定义品牌
     * @param map
     * @return
     */
    @Override
    public int delApplyBrand(Map<String, Object> map) {
        return this.update("com.ningpai.third.seller.mapper.ApplyBrandMapper.delApplyBrand", map);
    }

    /**
     * 根据自定义品牌id和商家id查询自定义品牌详情
     * @param map 自定义品牌id和商家id
     * @return 自定义品牌详情
     */
    @Override
    public ApplyBrand selectApplyBrandByIds(Map<String, Object> map) {
        return this.selectOne("com.ningpai.third.seller.mapper.ApplyBrandMapper.selectApplyBrandByIds", map);
    }

    /**
     * 新增自定义品牌
     * @param applyBrand
     * @return
     */
    @Override
    public int addApplyBrand(ApplyBrand applyBrand) {
        return this.insert("com.ningpai.third.seller.mapper.ApplyBrandMapper.addApplyBrand", applyBrand);
    }

    /**
     ＊ 获取新保存的自定义品牌的ID
     * @return
     */
    @Override
    public Long selectLastId() {
        return this.selectOne("com.ningpai.third.seller.mapper.ApplyBrandMapper.selectLastId");
    }

    /**
     * 获取自定义品牌的数量
     * @param pmap
     * @return
     */
    @Override
    public int queryApplyBrandCount(Map<String, Object> pmap) {
        return this.selectOne("com.ningpai.third.seller.mapper.ApplyBrandMapper.queryApplyBrandCount", pmap);
    }

    /**
     * 根据条件获取自定义品牌的集合
     * @param pmap
     * @return
     */
    @Override
    public List<Object> queryApplyBrand(Map<String, Object> pmap) {
        return this.selectList("com.ningpai.third.seller.mapper.ApplyBrandMapper.queryApplyBrand", pmap);
    }

    /**
     * 根据条件删除一条自定义品牌
     * @param map
     * @return
     */
    @Override
    public int delApplyBrands(Map<String, Object> map) {
        return this.update("com.ningpai.third.seller.mapper.ApplyBrandMapper.delApplyBrands", map);
    }

    /**
     * 根据条件更新一条自定义品牌
     * @param map
     * @return
     */
    @Override
    public int updateThirdApplyBrand(Map<String, Object> map) {
        return this.update("com.ningpai.third.seller.mapper.ApplyBrandMapper.updateThirdApplyBrand", map);
    }

    /**
     * 根据自定义品牌名称修改商品品牌状态
     * 第三方后台删除自定义品牌时，如果是boss后台已经审核通过的，则把审核通过后goods_brand表里的数据也删除
     * @param applyBrandName 自定义品牌名称
     * @return 修改结果
     */
    @Override
    public int updateGoodsBrandByName(String applyBrandName) {
        return this.update("com.ningpai.third.seller.mapper.ApplyBrandMapper.updateGoodsBrandByName", applyBrandName);
    }
}
