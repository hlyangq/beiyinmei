package com.junit.custom.thirdaudit.service;

import com.ningpai.customer.bean.Customer;
import com.ningpai.customer.dao.CustomerMapper;
import com.ningpai.goods.service.GoodsElasticSearchService;
import com.ningpai.thirdaudit.bean.StoreInfo;
import com.ningpai.thirdaudit.mapper.StoreInfoMapper;
import com.ningpai.thirdaudit.service.StoreInfoService;
import com.ningpai.thirdaudit.service.impl.StoreInfoServiceImpl;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * StoreInfoServiceTest
 *
 * @author djk
 * @date 2016/3/21
 */
public class StoreInfoServiceTest extends UnitilsJUnit3 {

    @TestedObject
    private StoreInfoService storeInfoService = new StoreInfoServiceImpl();

    @InjectIntoByType
    private Mock<StoreInfoMapper> storeInfoMapperMock;

    @InjectIntoByType
    private Mock<CustomerMapper> customerMapperMock;

    @InjectIntoByType
    private Mock<GoodsElasticSearchService> goodsElasticSearchServiceMock;

    /**
     * 删除商家测试
     */
    @Test
    public void testDelStoreInfoById() {
        Customer customer = new Customer();
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setCustomerid(1L);
        String[] storeInfoIds = {"1"};
        storeInfoMapperMock.returns(storeInfo).queryStoreBalanceByThirdId(1L);
        customerMapperMock.returns(customer).getCustomerByCusId(1L);
        customerMapperMock.returns(1).deleteEmp(1L);
        storeInfoMapperMock.returns(1).delmanagerauthority(1L);
        customerMapperMock.returns(1).updateCustomer(customer);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", "djk");
        map.put("deltime", new Date());
        map.put("idItems", storeInfoIds);
        storeInfoMapperMock.returns(1).deleGoodsInfo(map);
        storeInfoMapperMock.returns(1).deleGoods(map);
        Long [] ids = {1L};
        storeInfoMapperMock.returns(1).delStoreInfoById(ids);
        assertEquals(1, storeInfoService.delStoreInfoById(storeInfoIds, "djk"));
    }

    /**
     * 根据商家id查询测试
     */
    @Test
    public void testQueryStorePointByThirdId() {
        storeInfoMapperMock.returns(new StoreInfo()).queryStorePointByThirdId(1L);
        assertNotNull(storeInfoService.queryStorePointByThirdId(1L));
    }

    /**
     * 高级查询测试
     */
    @Test
    public void testUpStorePointByThirdId() {
        storeInfoMapperMock.returns(1).upStorePointByThirdId(null);
        assertEquals(1, storeInfoService.upStorePointByThirdId(null));
    }

    /**
     * 根据map修改记录测试
     */
    @Test
    public void testUpStoreBalanceByThirdId() {
        storeInfoMapperMock.returns(1).upStoreBalanceByThirdId(null);
        assertEquals(1, storeInfoService.upStoreBalanceByThirdId(null));
    }

    /**
     * 查询记录测试
     */
    @Test
    public void testQueryStoreBalanceByThirdId() {
        storeInfoMapperMock.returns(new StoreInfo()).queryStoreBalanceByThirdId(1L);
        assertNotNull(storeInfoService.queryStoreBalanceByThirdId(1L));
    }



}
