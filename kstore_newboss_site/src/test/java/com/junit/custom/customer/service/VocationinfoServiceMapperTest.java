package com.junit.custom.customer.service;

import com.ningpai.customer.bean.Vocationinfo;
import com.ningpai.customer.dao.VocationinfoMapper;
import com.ningpai.customer.service.VocationinfoServiceMapper;
import com.ningpai.customer.service.impl.VocationinfoServiceMapperImpl;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;

/**
 * VocationinfoServiceMapperTest
 *
 * @author djk
 * @date 2016/3/21
 */
public class VocationinfoServiceMapperTest extends UnitilsJUnit3 {

    /**
     * 需要测试的service
     */
    @TestedObject
    private VocationinfoServiceMapper vocationinfoServiceMapper = new VocationinfoServiceMapperImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<VocationinfoMapper> vocationinfoMapperMock;

    /**
     * 根据主键删除职业信息测试
     */
    @Test
    public void testDeleteByPrimaryKey() {
        vocationinfoMapperMock.returns(1).deleteByPrimaryKey(1L);
        assertEquals(1, vocationinfoServiceMapper.deleteByPrimaryKey(1L));
    }

    /**
     * 插入职业信息测试
     */
    @Test
    public void testInsert() {
        Vocationinfo vocationinfo = new Vocationinfo();
        vocationinfoMapperMock.returns(1).insert(vocationinfo);
        assertEquals(1, vocationinfoServiceMapper.insert(vocationinfo));
    }

    /**
     * 根据主键查询职业信息测试
     */
    @Test
    public void testSelectByPrimaryKey() {
        vocationinfoMapperMock.returns(new Vocationinfo()).selectByPrimaryKey(1L);
        assertNotNull(vocationinfoServiceMapper.selectByPrimaryKey(1L));
    }

    /**
     * 根据主键修改职业信息测试
     */
    @Test
    public void testUpdateByPrimaryKey() {
        Vocationinfo vocationinfo = new Vocationinfo();
        vocationinfoMapperMock.returns(1).updateByPrimaryKey(vocationinfo);
        vocationinfoServiceMapper.updateByPrimaryKey(vocationinfo);
    }

    /**
     * 查询全部职业信息测试
     */
    @Test
    public void testSelectAllVocation()
    {
        vocationinfoMapperMock.returns(new ArrayList<>()).selectAllVocation(1L);
        assertNotNull(vocationinfoServiceMapper.selectAllVocation(1L));
    }


}
