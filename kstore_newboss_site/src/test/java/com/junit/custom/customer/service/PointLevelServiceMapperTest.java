package com.junit.custom.customer.service;

import com.ningpai.customer.bean.CustomerPointLevel;
import com.ningpai.customer.dao.CustomerPointLevelMapper;
import com.ningpai.customer.service.PointLevelServiceMapper;
import com.ningpai.customer.service.impl.PointLevelServiceMapperImpl;
import com.ningpai.util.PageBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * PointLevelServiceMapper
 *
 * @author djk
 * @date 2016/3/21
 */
public class PointLevelServiceMapperTest extends UnitilsJUnit3 {

    /**
     * 需要测试的service
     */
    @TestedObject
    private PointLevelServiceMapper pointLevelServiceMapper = new PointLevelServiceMapperImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<CustomerPointLevelMapper> customerPointLevelMapperMock;

    /**
     * 查询所有会员等级 用于等级页面展示
     */
    @Test
    public void testSelectAllPointLevel() {
        PageBean pageBean = new PageBean();

        HashMap paramMap = new HashMap<String, Integer>();
        paramMap.put("startRowNum", pageBean.getStartRowNum());
        paramMap.put("endRowNum", pageBean.getEndRowNum());
        customerPointLevelMapperMock.returns(1).selectAllCount();
        customerPointLevelMapperMock.returns(new ArrayList<>()).selectPointLevelByLimit(paramMap);
        assertNotNull(pointLevelServiceMapper.selectAllPointLevel(pageBean));
    }

    /**
     * 查询所有会员等级 用于添加会员时填充会员等级下拉列表
     */
    @Test
    public void testSelectAllPointLevel2() {
        customerPointLevelMapperMock.returns(new ArrayList<>()).selectAllPointLevel();
        assertNotNull(pointLevelServiceMapper.selectAllPointLevel());
    }

    /**
     * 添加会员等级测试
     */
    @Test
    public void testAddPointLevel() {
        CustomerPointLevel customerPointLevel = new CustomerPointLevel();
        customerPointLevelMapperMock.returns(1).insertSelective(customerPointLevel);
        assertEquals(1, pointLevelServiceMapper.addPointLevel(customerPointLevel));
    }

    /**
     * 根据指定编号获取指定会员等级测试
     */
    @Test
    public void testSelectPointLevelById() {
        customerPointLevelMapperMock.returns(new CustomerPointLevel()).selectByPrimaryKey(1L);
        assertNotNull(pointLevelServiceMapper.selectPointLevelById(1L));
    }


    /**
     * 修改会员等级
     */
    @Test
    public void testUpdatePointLevel() {
        CustomerPointLevel customerPointLevel = new CustomerPointLevel();
        customerPointLevelMapperMock.returns(1).updateByPrimaryKeySelective(customerPointLevel);
        assertEquals(1, pointLevelServiceMapper.updatePointLevel(customerPointLevel));
    }

    /**
     * 删除会员等级
     */
    @Test
    public void testDeletePointLevel() {
        String [] ids = {"1"};
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pointLevelIds", ids);
        customerPointLevelMapperMock.returns(1).deletePointLevelByIds(paramMap);
        assertEquals(1, pointLevelServiceMapper.deletePointLevel(ids));
    }

    /**
     * 根据指定等级名称获取指定会员等级,用于验证该等级是否存在
     */
    @Test
    public void testSelectPointLevelByName() {
        customerPointLevelMapperMock.returns(1L).selectByPrimaryName("1");
        assertNotNull(pointLevelServiceMapper.selectPointLevelByName("1"));
    }

    /**
     * 验证默认等级是否存在
     */
    @Test
    public void testSelectDefaultPointLevel() {
        customerPointLevelMapperMock.returns(1L).selectDefaultPointLevel();
        assertNotNull(pointLevelServiceMapper.selectDefaultPointLevel());
    }

    public void testCancelBeforeDefault() {
        customerPointLevelMapperMock.returns(1).cancelBeforeDefault();
        assertEquals(1,pointLevelServiceMapper.cancelBeforeDefault());
    }


}
