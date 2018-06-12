package com.ningpai.mobile.dao.impl;

import com.ningpai.manager.base.BasicSqlSupport;
import com.ningpai.mobile.bean.MobPageCate;
import com.ningpai.mobile.dao.MobPageCateMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by dyt on 2016/10/19.
 */
@Repository("MobPageCateMapper")
public class MobPageCateMapperImpl  extends BasicSqlSupport implements MobPageCateMapper {

    @Override
    public List<Object> selectByPageBean(Map<String, Object> map) {
        return this.selectList("com.ningpai.mobile.dao.MobPageCateMapper.selectByPageBean", map);
    }

    @Override
    public int selectCount(Map<String, Object> map) {
        return this.selectOne("com.ningpai.mobile.dao.MobPageCateMapper.selectCount", map);
    }

    @Override
    public MobPageCate selectById(Long pageCateId) {
        return this.selectOne("com.ningpai.mobile.dao.MobPageCateMapper.selectById", pageCateId);
    }

    @Override
    public List<MobPageCate> selectAll(Map<String, Object> map) {
        return this.selectList("com.ningpai.mobile.dao.MobPageCateMapper.selectAll", map);
    }

    @Override
    public int deleteByList(List<Long> list) {
        return this.update("com.ningpai.mobile.dao.MobPageCateMapper.deleteByList", list);
    }

    @Override
    public int insert(MobPageCate record) {
        return this.insert("com.ningpai.mobile.dao.MobPageCateMapper.insert", record);
    }

    @Override
    public int updateByPrimaryKey(MobPageCate record) {
        return this.update("com.ningpai.mobile.dao.MobPageCateMapper.updateByPrimaryKey", record);
    }
}
