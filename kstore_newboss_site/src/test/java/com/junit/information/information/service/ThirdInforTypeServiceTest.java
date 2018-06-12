package com.junit.information.information.service;

import com.ningpai.information.bean.Information;
import com.ningpai.information.bean.InformationType;
import com.ningpai.information.dao.ThirdInforMapper;
import com.ningpai.information.dao.ThirdInforTypeMapper;
import com.ningpai.information.service.ThirdInforService;
import com.ningpai.information.service.ThirdInforTypeService;
import com.ningpai.information.service.impl.ThirdInforServiceImpl;
import com.ningpai.information.service.impl.ThirdInforTypeServiceImpl;
import com.ningpai.information.vo.InformationTypeVo;
import com.ningpai.information.vo.InformationVo;
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
public class ThirdInforTypeServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private ThirdInforTypeService thirdInforTypeService = new ThirdInforTypeServiceImpl();

    @InjectIntoByType
    private Mock<ThirdInforTypeMapper> thirdInforTypeMapperMock;

    /**
     * 资讯类型实体
     */
    private InformationType informationType = new InformationType();

    /**
     * VO实体类-资讯栏目
     */
    private InformationTypeVo informationTypeVo = new InformationTypeVo();


    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();

    /**
     * 初始化数据
     */
    @Override
    public void setUp(){
        informationType.setInfoTypeId(1L);
        informationType.setTemp2("temp2");
    }

    /**
     * 根据主键删除文章栏目测试
     */
    @Test
    public void testDelInformation(){
        thirdInforTypeMapperMock.returns(informationType).selectByPrimaryKey(1L);
        informationType.setDelflag("1");
        informationType.setUpdateUserId(2L);
        informationType.setUpdateDate(new Date());
        informationType.setParentId(0L);
        thirdInforTypeMapperMock.returns(1).selectByPrimaryKey(0L);
        thirdInforTypeMapperMock.returns(1).updateByPrimaryKeySelective(informationType);
        thirdInforTypeService.delInformation(1L,2L);
    }

    /**
     * 批量删除资讯类型测试
     */
    @Test
    public void testBatchDelInformation(){
        thirdInforTypeMapperMock.returns(informationType).selectByPrimaryKey(1L);
        informationType.setDelflag("1");
        informationType.setUpdateUserId(2L);
        informationType.setUpdateDate(new Date());
        informationType.setParentId(0L);
        thirdInforTypeMapperMock.returns(1).selectByPrimaryKey(0L);
        thirdInforTypeMapperMock.returns(1).updateByPrimaryKeySelective(informationType);
        thirdInforTypeService.batchDelInformation(new Long[]{1L},2L);
    }

    /**
     * 添加资讯类型测试
     */
    @Test
    public void testSaveInformation(){
        informationType.setParentId(0L);
        thirdInforTypeMapperMock.returns(informationType).selectByPrimaryKey(0L);
        informationType.setTemp1("1");
        informationType.setTemp2("temp2");
        Date date = new Date();
        informationType.setCreateDate(date);
        informationType.setUpdateDate(date);
        thirdInforTypeMapperMock.returns(1).insertSelective(informationType);
        thirdInforTypeService.saveInformation(informationType,"temp2");
    }

    /**
     * 更新资讯类型测试
     */
    @Test
    public void testUpdateInformation(){
        informationType.setParentId(0L);
        thirdInforTypeMapperMock.returns(informationType).selectByPrimaryKey(0L);
        informationType.setUpdateDate(new Date());
        thirdInforTypeMapperMock.returns(1).updateByPrimaryKeySelective(informationType);
        thirdInforTypeService.updateInformation(informationType);
    }

    /**
     * 根据主键查找资讯类型测试
     */
    @Test
    public void testSelectByPrimaryKey(){
        thirdInforTypeMapperMock.returns(informationType).selectByPrimaryKey(1L);
        assertNotNull(thirdInforTypeService.selectByPrimaryKey(1L));
    }

    /**
     * 根据分页参数和第三方商家ID查询资讯类型列表测试
     */
    @Test
    public void testQueryByPageBean(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("temp2", "temp2");
        thirdInforTypeMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum", pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(informationType);
        thirdInforTypeMapperMock.returns(list).queryByPageBean(map);
        assertEquals(1, thirdInforTypeService.queryByPageBean(pageBean, "", "temp2").getList().size());
        map.clear();
        map.put("searchText", "typeName");
        map.put("temp2", "temp2");
        thirdInforTypeMapperMock.returns(1).queryTotalCountForSearch(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum", pageBean.getEndRowNum());
        thirdInforTypeMapperMock.returns(list).queryByPageBeanForSearch(map);
        assertEquals(1,thirdInforTypeService.queryByPageBean(pageBean,"typeName","temp2").getList().size());
    }

    /**
     * 根据第三方商家ID查找所有的资讯类型测试
     */
    @Test
    public void testSelectAllByThirdId(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        thirdInforTypeMapperMock.returns(list).selectAllByThirdId("111");
        assertEquals(1,thirdInforTypeService.selectAllByThirdId("111").size());
    }

    /**
     * 根据第三方商家ID查找所有可发布文章的栏目测试
     */
    @Test
    public void testSelectInfoTypeByAttr(){
        List<InformationType> list = new ArrayList<>();
        list.add(informationType);
        thirdInforTypeMapperMock.returns(list).selectInfoTypeByAttr("111");
        assertEquals(1,thirdInforTypeService.selectInfoTypeByAttr("111").size());
    }

    /**
     * 判断资讯类型是否可删除测试
     */
    @Test
    public void testCheckDelWithInfoTypeId(){
        thirdInforTypeMapperMock.returns(0).selectCountByParentId(0L);
        assertTrue(thirdInforTypeService.checkDelWithInfoTypeId(0L));
    }

    /**
     * 根据第三方商家ID和栏目名称验证是否可添加测试
     */
    @Test
    public void testCheckAddInfoTypeByName(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "name");
        map.put("temp2", "temp2");
        thirdInforTypeMapperMock.returns(0).selectInfoTypeCountByName(map);
        assertTrue(thirdInforTypeService.checkAddInfoTypeByName("name","temp2"));
    }

    /**
     * 修改栏目-根据栏目名称和栏目ID验证是否可修改测试
     */
    @Test
    public void testCheckAddInfoTypeByNameTwo(){
        thirdInforTypeMapperMock.returns(informationType).selectByPrimaryKey(1L);
        informationType.setName("name");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "name");
        map.put("temp2", "temp2");
        thirdInforTypeMapperMock.returns(0).selectInfoTypeCountByName(map);
        assertTrue(thirdInforTypeService.checkAddInfoTypeByName("name","temp2",1L));
    }


    /**
     * 获得资讯类型下的所有的子类型测试
     */
    @Test
    public void testGetInfoTypeList(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        thirdInforTypeMapperMock.returns(list).selectByParentId(0L);
        assertEquals(1,thirdInforTypeService.getInfoTypeList(0L).size());
    }
}

