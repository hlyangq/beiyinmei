package com.ningpai.marketing.dao;

import com.ningpai.marketing.bean.FullbuyPresentScope;

import java.util.List;

/**
 * 满赠促销 2016-07-25
 *
 * @author fb
 *
 */
public interface FullbuyPresentScopeMapper {
    int deleteByPrimaryKey(Long presentScopeId);

    int deleteByPresentMarketingId(Long fullbuyPresentMarketingId);

    int insertSelective(FullbuyPresentScope record);

    FullbuyPresentScope selectByPrimaryKey(Long presentScopeId);

    int updateByPrimaryKeySelective(FullbuyPresentScope record);

    int updateByPrimaryKey(FullbuyPresentScope record);

    List<FullbuyPresentScope> selectPresentsByPresentMarketingId(Long fullbuyPresentMarketingId);

    /**
     * 根据赠品范围ids查询赠品范围list
     * @param scopeIds 赠品范围ids
     * @return 赠品范围list
     */
    List<FullbuyPresentScope> queryByScopeIds(List<String> scopeIds);
}