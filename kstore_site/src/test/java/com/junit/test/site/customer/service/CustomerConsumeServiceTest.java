package com.junit.test.site.customer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.site.customer.bean.CustomerConsume;
import com.ningpai.site.customer.mapper.CustomerConsumeMapper;
import com.ningpai.site.customer.service.CustomerConsumeService;
import com.ningpai.site.customer.service.impl.CustomerConsumeServiceImpl;
import com.ningpai.util.PageBean;

/**
 * 会员消费记录测试
 * @author djk
 *
 */
public class CustomerConsumeServiceTest extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private CustomerConsumeService customerConsumeService = new CustomerConsumeServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerConsumeMapper> customerConsumeMapperMock;
    
    /**
     * 会员消费记录测试
     */
    private CustomerConsume  customerConsume = new CustomerConsume();
    
    /**
     * 删除会员消费记录测试
     */
    @Test
    public void testDeleteConsume()
    {
    	customerConsumeMapperMock.returns(1).deleteByPrimaryKey(1L);
    	assertEquals(1, customerConsumeService.deleteConsume(1L));
    }
    
    /**
     *  添加会员消费记录测试
     */
    @Test
    public void testSaveConsume()
    {
    	customerConsumeMapperMock.returns(1).insertSelective(customerConsume);
    	assertEquals(1, customerConsumeService.saveConsume(customerConsume));
    }
    
    /**
     * 根据会员消费记录编号修改会员等级测试
     */
    @Test
    public void testUpdateConsume() 
    {
    	customerConsumeMapperMock.returns(1).updateByPrimaryKeySelective(customerConsume);
    	assertEquals(1, customerConsumeService.updateConsume(customerConsume));
    }
    
    /**
     * 根据会员消费记录编号获取会员消费记录测试
     */
    @Test
    public void testGetConsumeById()
    {
    	customerConsumeMapperMock.returns(customerConsume).selectByPrimaryKey(1L);
    	assertNotNull(customerConsumeService.getConsumeById(1L));
    }
    
    /**
     * 按会员编号和时间标记查询消费记录的分页数据测试
     */
    @Test
    public void testQueryAllConsumeByCid()
    {
    	PageBean pb = new PageBean();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("customerId", 1L);
        map.put("date", 1);
        customerConsumeMapperMock.returns(1).queryConsumeByCidCount(map);
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        List<Object> lists = new ArrayList<>();
        lists.add(new Object());
        customerConsumeMapperMock.returns(lists).queryAllConsumeByCid(map);
    	assertEquals(1, customerConsumeService.queryAllConsumeByCid(pb, 1L, 1).getList().size());
    }
    
    /**
     * 根据会员编号查询消费总和测试
     */
    @Test
    public void testSelectTotalNumByCid()
    {
    	BigDecimal result = new BigDecimal(1);
    	customerConsumeMapperMock.returns(result).selectTotalNumByCid(1L);
    	assertEquals(result, customerConsumeService.selectTotalNumByCid(1L));
    }
    
}
