package com.ningpai.marketing.dao.impl;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.marketing.bean.FullbuyPresentScope;
import com.ningpai.marketing.dao.FullbuyPresentScopeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author fb 2016-07-25
 *
 * 满赠促销实现类
 */
@Repository("fullbuyPresentScopeMapper")
public class FullbuyPresentScopeMapperImpl extends BasicSqlSupport
        implements FullbuyPresentScopeMapper {


    @Override
    public int deleteByPrimaryKey(Long presentScopeId){
        return this
                .update(
                        "com.ningpai.marketing.dao.FullbuyPresentScopeMapper.deleteByPrimaryKey",
                        presentScopeId);
    }
    @Override
    public int deleteByPresentMarketingId(Long fullbuyPresentMarketingId){
        return this
                .update(
                        "com.ningpai.marketing.dao.FullbuyPresentScopeMapper.deleteByPresentMarketingId",
                        fullbuyPresentMarketingId);
    }

    @Override
    public int insertSelective(FullbuyPresentScope record){
        return this
                .insert("com.ningpai.marketing.dao.FullbuyPresentScopeMapper.insertSelective",
                        record);
    }

    @Override
    public FullbuyPresentScope selectByPrimaryKey(Long presentScopeId){
        return this
                .selectOne("com.ningpai.marketing.dao.FullbuyPresentScopeMapper.selectByPrimaryKey",
                        presentScopeId);
    }

    @Override
    public int updateByPrimaryKeySelective(FullbuyPresentScope record) {
        return this
                .update("com.ningpai.marketing.dao.FullbuyPresentScopeMapper.updateByPrimaryKeySelective",
                        record);
    }
    @Override
    public int updateByPrimaryKey(FullbuyPresentScope record) {
        return this
                .update("com.ningpai.marketing.dao.FullbuyPresentScopeMapper.updateByPrimaryKey",
                        record);
    }
    @Override
    public List<FullbuyPresentScope> selectPresentsByPresentMarketingId(Long fullbuyPresentMarketingId) {
        return this
                .selectList("com.ningpai.marketing.dao.FullbuyPresentScopeMapper.selectPresentsByPresentMarketingId",
                        fullbuyPresentMarketingId);
    }

    /**
     * 根据赠品范围ids查询赠品范围list
     *
     * @param scopeIds 赠品范围ids
     * @return 赠品范围list
     */
    @Override
    public List<FullbuyPresentScope> queryByScopeIds(List<String> scopeIds) {
        return this.selectList("com.ningpai.marketing.dao.FullbuyPresentScopeMapper.queryByScopeIds", scopeIds);
    }
}
