package com.junit.information.information.service;

import com.ningpai.information.bean.InfoUserDefined;
import com.ningpai.information.dao.InfoUserDefinedMapper;
import com.ningpai.information.service.InfoUserDefinedService;
import com.ningpai.information.service.impl.InfoUserDefinedServiceImpl;
import com.ningpai.util.PageBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.*;

/**
 * Created by fengbin on 2016/3/22.
 */
public class InfoUserDefinedServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private InfoUserDefinedService infoUserDefinedService = new InfoUserDefinedServiceImpl();

    @InjectIntoByType
    private Mock<InfoUserDefinedMapper> infoUDMapperMock;

    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();

    /**
     *  实体类-文章自定义属性
     */
    private InfoUserDefined infoUserDefined = new InfoUserDefined();

    /**
     * 初始化数据
     */
    @Override
    public void setUp(){
        infoUserDefined.setInfoUdId(1L);
    }

    /**
     * 根据主键删除测试
     */
    @Test
    public void testDeleteUserDefinedById(){
        infoUDMapperMock.returns(infoUserDefined).selectByPrimaryKey(1L);
        infoUserDefined.setDelflag("1");
        infoUserDefined.setUpdateDate(new Date());
        infoUDMapperMock.returns(1).updateByPrimaryKeySelective(infoUserDefined);
        assertEquals(1,infoUserDefinedService.deleteUserDefinedById(1L));
    }

    /**
     * 添加测试
     */
    @Test
    public void testCreateUserDefined(){
        Date date = new Date();
        infoUserDefined.setDelflag("0");
        infoUserDefined.setCreateDate(date);
        infoUserDefined.setUpdateDate(date);
        infoUDMapperMock.returns(1).insertSelective(infoUserDefined);
        assertEquals(1,infoUserDefinedService.createUserDefined(infoUserDefined));
    }

    /**
     * 修改测试
     */
    @Test
    public void testUpdateUserDefined(){
        infoUserDefined.setUpdateDate(new Date());
        infoUDMapperMock.returns(1).updateByPrimaryKeySelective(infoUserDefined);
        assertEquals(1,infoUserDefinedService.updateUserDefined(infoUserDefined));
    }

    /**
     * 查询所有测试
     */
    @Test
    public void testFindAllUserDefined(){
        List<InfoUserDefined> list = new ArrayList<>();
        list.add(infoUserDefined);
        infoUDMapperMock.returns(list).selectAll();
        assertEquals(1,infoUserDefinedService.findAllUserDefined().size());
    }

}

