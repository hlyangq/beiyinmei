package com.ningpai.mobile.dao;

import com.ningpai.mobile.bean.MobPageCate;

import java.util.List;
import java.util.Map;

/**
 * DAO-移动端页面分类
 * 
 * @author NINGPAI-Daiyitian
 * @since 2014年7月24日上午10:38:19
 */
public interface MobPageCateMapper {

    /**
     * 根据分页参数查询
     * @param map
     * @return
     */
    List<Object> selectByPageBean(Map<String, Object> map);

    /**
     * 查询总行数
     * @param map
     * @return
     */
    int selectCount(Map<String, Object> map);

    /**
     * 根据主键查询
     * @param pageCateId
     * @return
     */
    MobPageCate selectById(Long pageCateId);

    /**
     * 查询所有
     *
     * @return
     */
    List<MobPageCate> selectAll(Map<String, Object> map);

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteByList(List<Long> list);

    /**
     * 添加
     * @param record
     * @return
     */
    int insert(MobPageCate record);

    /**
     * 修改-字段可选
     * @param record
     * @return
     */
    int updateByPrimaryKey(MobPageCate record);

}
