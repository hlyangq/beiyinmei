package com.ningpai.mobile.dao;

import com.ningpai.channel.bean.ChannelAdver;
import com.ningpai.mobile.bean.MobCateBar;
import com.ningpai.mobile.vo.MobCateBarVo;

import java.util.List;
import java.util.Map;

/**
 * DAO-移动版分类导航
 * 
 * @author NINGPAI-WangHaiYang
 * @since 2014年8月19日上午11:56:30
 */
public interface MobCateBarMapper {

    /**
     * 查询移动端店铺页轮播大广告
     * 
     * @return 获取的移动端轮播大广告图片集合
     * @author zhanghl
     */
    List<ChannelAdver> selectStoreListImage(String userStatus);

    /**
     * 查询移动端抢购页面
     *
     * @param map  参数
     * @return 返回抢购广告信息
     */
    List<Object> selectStoreListQianggouImage(Map<String, Object> map);

    /**
     * 删除分类导航
     * 
     * @param cateBarId
     * @return
     */
    int deleteByPrimaryKey(Long cateBarId);

    /**
     * 添加分类导航
     * 
     * @param record
     * @return
     */
    int insert(MobCateBar record);

    /**
     * 添加分类导航-字段可选
     * 
     * @param record
     * @return
     */
    int insertSelective(MobCateBar record);

    /**
     * 修改分类导航-字段可选
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MobCateBar record);

    /**
     * 修改分类导航
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKey(MobCateBar record);

    /**
     * 根据主键查询
     * 
     * @param cateBarId
     * @return
     */
    MobCateBar selectByPrimaryKey(Long cateBarId);

    /**
     * 根据父ID查询未删除的
     * 
     * @param parentId
     * @return
     */
    List<MobCateBar> selectByParentId(Long parentId);

    /**
     * 根据商品分类ID查询未删除的分类导航数量
     * 
     * @param cateId
     * @return
     */
    int selectCountByCateId(Long cateId);

    /**
     * 查询所有未删除、已启用的一级分类导航,前台用
     * 
     * @return
     */
    List<MobCateBarVo> selectAll();

    /**
     * 查询所有未删除、已启用的分类导航,前台用
     * 
     * @return
     */
    List<MobCateBar> selectAllForMobChoose();

    /**
     * 查询未删除的一级分类导航数量
     * 
     * @return
     */
    int selectCountByPb();

    /**
     * 查询抢购广告数量
     *
     * @return 返回抢购广告数量
     */
    int selectStoreListQianggouImageCount();

    /**
     * 分页查询未删除的一级分类导航
     * 
     * @param map
     * @return
     */
    List<Object> selectAllByPb(Map<String, Object> map);
    /**
     * 查询所有未删除、已启用的一级分类导航，返回List，前台调用不包含子分类
     *
     * @return List<MobCateBar>
     */
    List<MobCateBar> selectOneMobCate();
    /**
     * 根据ID查询它的子分类集合
     *
     * @param mobcatebarId 父分类编号
     * @return   List<MobCateBarVo>
     */
    List<MobCateBarVo> queryMobcateBarById(Long mobcatebarId);

    /**
     * 根据父分类导航ID查询显示的未删除的子分类导航分类
     *
     * @param mobCateBarId 父分类导航id
     * @return 子分类导航集合
     */
    List<MobCateBarVo> queryUsingMobCateBar(Long mobCateBarId);

    /**
     * 查询移动端轮播打广告列表
     * @param map
     * @return
     */
    List<Object> selectTurnsAdvertsList(Map<String, Object> map);

    /**
     * 查询轮播打广告总数
     * @return
     */
    int selectTurnsAdvertsCount(Map<String, Object> map);

    /**
     * 根据父Id删除
     */
    int deleteByParentId(Long parentId);
}
