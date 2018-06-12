package com.junit.test.store.service;

import com.ningpai.m.store.mapper.StoreListMapper;
import com.ningpai.m.store.service.StoreListService;
import com.ningpai.m.store.service.impl.StoreListServiceImpl;
import com.ningpai.thirdaudit.bean.GoodsCateGory;
import com.ningpai.util.MapUtil;
import com.ningpai.util.PageBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wtp on 2016/3/30.
 */
public class StoreListServiceTest extends UnitilsJUnit3 {
    /**
     * 需要测试的Service
     */
    @TestedObject
    private StoreListService storeListService= new StoreListServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<StoreListMapper> storeListMapperMock;

    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();

    /**
     * 删除关注记录
     */
    @Test
    public void testDeleteController() {
        storeListMapperMock.returns(1).deleteController(1L,1L);
        assertEquals(1, storeListService.deleteController(1L, 1L));
    }

    /**
     * 判断此是否关注此店铺
     */
    @Test
    public void testSelectController() {
        storeListMapperMock.returns(1).IsCollection(1L, 1L);
        assertEquals(1, storeListService.selectController(1L, 1L));
    }

    /**
     * 查询所有的商品一级分类
     */
    @Test
    public void testSelectgoodscatebyone() {
        List<GoodsCateGory> goodscategory = new ArrayList<GoodsCateGory>();
        storeListMapperMock.returns(goodscategory).selectgoodscatebyone();
        assertNotNull(storeListService.selectgoodscatebyone());
    }

    /**
     * 查询最近上架的商品集合
     */
    @Test
    public void testSetStoreNewProcudtList() {
        PageBean pb = new PageBean();
        List<Object> list = new ArrayList<Object>();
        storeListMapperMock.returns(list).StoreNewProcudtList(1L);
        assertNotNull(storeListService.setStoreNewProcudtList(pb, 1L));
    }

    /**
     * 关注店铺
     */
    @Test
    public void testAddCollectionSeller() {
        storeListMapperMock.returns(1).addCollectionSeller(1L, 1l);
        assertEquals(1, storeListService.addCollectionSeller(1l, 1L));
    }

    /**
     * Store列表
     */
    @Test
    public void testSelectStoreList() {
        List<Long> lIds = new ArrayList<>();
        Long [] ids = {1L};
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startRowNum", pageBean.getStartRowNum());
        paramMap.put("endRowNum", pageBean.getEndRowNum());
        paramMap.put("cateId", 1L);
        List<Object> storeInfos = new ArrayList<Object>();
        storeListMapperMock.returns(storeInfos).selectStoreList(paramMap);
        assertNotNull(storeListService.selectStoreList(pageBean, 1L, 1L));
    }
}
