package com.junit.test.site.sld.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;



import com.ningpai.site.sld.bean.DomainCustom;
import com.ningpai.site.sld.mapper.impl.DomainCustomMapperImpl;
import com.ningpai.site.sld.service.DomainCustomService;
import com.ningpai.site.sld.service.impl.DomainCustomServiceImpl;

/**
 * 二级域名关联service测试
 * @author djk
 *
 */
public class DomainCustomServiceTest  extends UnitilsJUnit3
{
	/**
     * 需要测试的Service
     */
    @TestedObject
    private DomainCustomService domainCustomService = new DomainCustomServiceImpl();
    
    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<DomainCustomMapperImpl> domainCustomMapperImplMock;
    
    /**
     * 用户域名
     */
    private DomainCustom domainCustom = new DomainCustom();
    
    /**
     * 根据第三方id查询二级域名测试
     */
    @Test
    public void testFindDomainCustom()
    {
    	assertNull(domainCustomService.findDomainCustom(1L));
    }
    
    /**
     * 修改二级域名测试
     */
    @Test
    public void testUpdateDomain()
    {
    	assertEquals(0, domainCustomService.updateDomain(domainCustom));
    }
    
    /**
     * 根据id查询二级域名测试
     */
    @Test
    public void testQueryDomainByID()
    {
    	assertNull(domainCustomService.queryDomainByID(1L));
    }
    
    /**
     * 检查是否存在二级域名测试
     */
    @Test
    public void testQueryByDomain()
    {
    	assertNull(domainCustomService.queryByDomain("a"));
    }
    
    /**
     * 查询全部域名测试
     */
    @Test
    public void testFindAll()
    {
    	List<DomainCustom>  list = new ArrayList<>();
    	list.add(domainCustom);
    	domainCustomMapperImplMock.returns(list).findAll();
    	assertEquals(1, domainCustomService.findAll().size());
    }
}
