package com.junit.information.information.service;

import com.ningpai.information.bean.InformationType;
import com.ningpai.information.dao.InformationTypeMapper;
import com.ningpai.information.service.InformationTypeService;
import com.ningpai.information.service.impl.InformationTypeServiceImpl;
import com.ningpai.information.vo.InformationTypeVo;
import com.ningpai.util.PageBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.*;

/**
 * Created by fengbin on 2016/3/21.
 */
public class InformationTypeServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private InformationTypeService informationTypeService = new InformationTypeServiceImpl();

    @InjectIntoByType
    private Mock<InformationTypeMapper> informationTypeMapperMock;

    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();

    /**
     * VO实体类-资讯栏目
     */
    private InformationTypeVo informationTypeVo = new InformationTypeVo();

    /**
     * 资讯类型实体
     */
    private InformationType informationType = new InformationType();

    /**
     * 初始化数据
     */
    @Override
    public void setUp(){
        informationType.setInfoTypeId(10L);
        informationTypeVo.setInfoTypeId(10L);
        informationTypeVo.setParentId(1L);
    }

    /**
     * 根据主键删除资讯类型测试
     */
    @Test
    public void testDelInformation(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        informationTypeMapperMock.returns(list).selectByParentId(1L);
        informationTypeMapperMock.returns(informationType).selectByPrimaryKey(10L);
        informationType.setInfoTypeId(10L);
        informationType.setDelflag("1");
        informationType.setUpdateUserId(2L);
        informationType.setUpdateDate(new Date());
        informationTypeMapperMock.returns(1).updateByPrimaryKeySelective(informationType);
        informationTypeService.delInformation(10L,2L);
    }

    /**
     * 根据主键删除资讯类型-调用存储过程级联删除
     */
    @Test
    public void testDelInformationPro(){
        informationTypeMapperMock.returns(1).deleteByPrimaryKeyPro(10L);
        informationTypeService.delInformationPro(10L);
    }

    /**
     * 批量删除资讯类型测试
     */
    @Test
    public void testBatchDelInformation(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        informationTypeMapperMock.returns(list).selectByParentId(1L);
        informationTypeMapperMock.returns(informationType).selectByPrimaryKey(10L);
        informationType.setInfoTypeId(10L);
        informationType.setDelflag("1");
        informationType.setUpdateUserId(2L);
        informationType.setUpdateDate(new Date());
        informationTypeMapperMock.returns(1).updateByPrimaryKeySelective(informationType);
        informationTypeService.delInformation(10L,2L);
    }

    /**
     * 添加资讯类型测试
     */
    @Test
    public void testSaveInformation(){
        informationType.setParentId(0L);
        informationTypeMapperMock.returns(informationType).selectByPrimaryKey(10L);
        Date date = new Date();
        informationType.setCreateDate(date);
        informationType.setUpdateDate(date);
        informationTypeMapperMock.returns(1).insertSelective(informationType);
        informationTypeService.saveInformation(informationType);
    }

    /**
     * 更新资讯类型测试
     */
    @Test
    public void testUpdateInformation(){
        informationType.setParentId(0L);
        informationTypeMapperMock.returns(informationType).selectByPrimaryKey(10L);
        informationType.setUpdateDate(new Date());
        informationTypeMapperMock.returns(1).updateByPrimaryKeySelective(informationType);
        informationTypeService.updateInformation(informationType);
    }

    /**
     * 根据主键查找资讯类型测试
     */
    @Test
    public void testSelectByPrimaryKey(){
        informationTypeMapperMock.returns(informationType).selectByPrimaryKey(10L);
        assertNotNull(informationTypeService.selectByPrimaryKey(10L));
    }

    /**
     * 根据分页参数查询资讯类型列表测试
     */
    @Test
    public void testQueryByPageBean(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchText", "111");
        informationTypeMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum", pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(new Object());
        informationTypeMapperMock.returns(list).queryByPageBean(map);
        assertEquals(1,informationTypeService.queryByPageBean(pageBean,"111").getList().size());
    }

    /**
     * 查找所有的资讯类型测试
     */
    @Test
    public void testSelectAll(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        informationTypeMapperMock.returns(list).selectAll();
        assertEquals(1,informationTypeService.selectAll().size());
    }

    /**
     * 根据栏目属性查找所有可发布文章的资讯类型测试，添加文章用
     */
    @Test
    public void testSelectInfoTypeByAttrForAddInfo(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        informationTypeMapperMock.returns(list).selectInfoTypeByAttrForAddInfo();
        assertEquals(1, informationTypeService.selectInfoTypeByAttrForAddInfo().size());
    }

    /**
     * 根据栏目属性查找所有可发布文章的资讯类型测试，关联模板、频道用
     */
    @Test
    public void testSelectInfoTypeByAttrForTemp(){
        List<InformationTypeVo> list = new ArrayList<>();
        list.add(informationTypeVo);
        informationTypeMapperMock.returns(list).selectInfoTypeByAttrForTemp();
        assertEquals(1,informationTypeService.selectInfoTypeByAttrForTemp().size());
    }

    /**
     * 判断资讯类型是否可删除测试
     */
    @Test
    public void testCheckDelWithInfoTypeId(){
       informationTypeMapperMock.returns(0).selectCountByParentId(10L);
       assertTrue(informationTypeService.checkDelWithInfoTypeId(10L));
    }

    /**
     * 获得资讯类型下的所有的子类型测试
     */
    @Test
    public void testGetInfoTypeList(){
        InformationTypeVo informationTypeVo1 = new InformationTypeVo();
        informationTypeVo1.setInfoTypeId(1L);
        PageBean pb = new PageBean();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchText", "1");
        informationTypeMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum", pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(informationTypeVo1);
        informationTypeMapperMock.returns(list).queryByPageBean(map);
        List<InformationTypeVo> allInfoTypeList = new ArrayList<>();
        allInfoTypeList.add(informationTypeVo);
        informationTypeMapperMock.returns(allInfoTypeList).selectAll();
        informationTypeService.getInfoTypeList(pb,"1");
    }

    /**
     * 使用递归根据父分类ID计算所有的子分类测试
     */
    @Test
    public void testCalcInfoTypeVo(){
        List<InformationTypeVo> allInfoTypeVoList = new ArrayList<>();
        InformationTypeVo informationTypeVo = new InformationTypeVo();
        informationTypeVo.setInfoTypeId(1L);
        allInfoTypeVoList.add(informationTypeVo);
        informationTypeService.calcInfoTypeVo(1L,allInfoTypeVoList);
    }

    /**
     * 根据栏目名称验证是否可添加测试
     */
    @Test
    public void testCheckAddInfoTypeByName(){
        informationTypeMapperMock.returns(0).selectInfoTypeCountByName("栏目名称");
        assertTrue(informationTypeService.checkAddInfoTypeByName("栏目名称"));
    }

    /**
     * 修改栏目-根据栏目名称和栏目ID验证是否可修改测试
     */
    @Test
    public void testCheckAddInfoTypeByNameTwo(){
        informationTypeMapperMock.returns(informationType).selectByPrimaryKey(10L);
        informationType.setName("栏目名称");
        informationTypeMapperMock.returns(0).selectInfoTypeCountByName("栏目名称");
        assertTrue(informationTypeService.checkAddInfoTypeByName("栏目名称",10L));
    }

    /**
     * 查询栏目分类测试
     */
    @Test
    public void testQueryByPageBeanList(){
        InformationType informationType = new InformationType();
        informationType.setParentId(0L);
        informationType.setInfoTypeId(10L);
        List<InformationType> list = new ArrayList<>();
        list.add(informationType);
        informationTypeMapperMock.returns(list).selectAllType("111");
        List<InformationType> parentList = new ArrayList<>();
        parentList.add(informationType);
        informationTypeService.queryByPageBeanList(pageBean,"111");
    }


}

