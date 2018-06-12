package com.junit.information.information.service;

import com.ningpai.information.bean.Information;
import com.ningpai.information.dao.InformationMapper;
import com.ningpai.information.service.InformationService;
import com.ningpai.information.service.impl.InformationServiceImpl;
import com.ningpai.util.PageBean;
import com.ningpai.util.SelectBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.*;

/**
 * Created by fengbin on 2016/3/21.
 */
public class InformationServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private InformationService informationService = new InformationServiceImpl();

    @InjectIntoByType
    private Mock<InformationMapper> informationMapperMock;

    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();

    /**
     * selectBean
     */
    private SelectBean selectBean = new SelectBean();

    /**
     * 实体类-资讯文章
     */
    private Information information = new Information();
    /**
     * 初始化数据
     */
    @Override
    public void setUp(){
        selectBean.setCondition("1");
        selectBean.setSearchText("1");
        information.setDelflag("0");
        information.setInfoId(1L);
        information.setTitle("111");
        information.setIsShow("1");
        information.setIsThirdNews("0");
        information.setTemp3("1");
        information.setInfoTypeId(1L);
        information.setCreateDate(new Date());
        information.setUpdateDate(new Date());
    }

    /**
     * 根据分页参数查询资讯列表测试
     */
    @Test
    public void testQueryByPageBean(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("condition",selectBean.getCondition());
        map.put("searchText",selectBean.getSearchText());
        map.put("typeId",1L);
        informationMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum",pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(new Object());
        informationMapperMock.returns(list).queryByPageBean(map);
        assertEquals(1,informationService.queryByPageBean(pageBean,selectBean,1L).getList().size());
    }

    /**
     * 根据主键删除资讯测试
     */
    @Test
    public void testDelInfo(){
        informationMapperMock.returns(1).deleteByPrimaryKey(1L);
        assertEquals(1, informationService.delInfo(1L));
    }

    /**
     * 批量删除资讯测试
     */
    @Test
    public void testBatchDelInfo(){
        Long[] ids = {1L,2L};
        informationMapperMock.returns(1).deleteByPrimaryKey(1L);
        informationService.batchDelInfo(ids);
    }

    /**
     * 添加资讯测试
     */
    @Test
    public void testSaveInfo(){
        informationMapperMock.returns(1).insert(information);
        assertEquals(1,informationService.saveInfo(information));
    }

    /**
     * 更新资讯测试
     */
    @Test
    public void testUpdateInfo(){
        Information information = new Information();
        information.setUpdateDate(new Date());
        informationMapperMock.returns(1).updateByPrimaryKeySelective(information);
        assertEquals(1,informationService.updateInfo(information));
    }

    /**
     * 根据主键查找资讯测试
     */
    @Test
    public void testSelectByPrimaryKey(){
        informationMapperMock.returns(information).selectByPrimaryKey(1L);
        assertNotNull(informationService.selectByPrimaryKey(1L));
    }

    /**
     * 查找所有的资讯测试
     */
    @Test
    public void testSelectAll(){
        List<Information> list = new ArrayList<>();
        list.add(information);
        informationMapperMock.returns(list).selectAll();
        assertEquals(1,informationService.selectAll().size());
    }

    /**
     * 根据栏目ID获取文章数量验证栏目是否能删除测试
     */
    @Test
    public void testCheckDelInfoTypeByInfoCount(){
        informationMapperMock.returns(0).selectInfoCountByTypeId(1L);
        assertTrue(informationService.checkDelInfoTypeByInfoCount(1L));
    }

    /**
     * 根据文章标题查询文章数量判断标题是否存在测试
     */
    @Test
    public void testCheckAddInfoByTitle(){
        informationMapperMock.returns(0).selectInfoCountByTitle("111");
        assertTrue(informationService.checkAddInfoByTitle("111"));
    }

    /**
     * 根据文章标题查询文章数量判断标题是否存在测试
     */
    @Test
    public void testCheckAddInfoByTitleTwo(){
        informationMapperMock.returns(information).selectByPrimaryKey(1L);
        informationMapperMock.returns(0).selectInfoCountByTitle("111");
        assertTrue(informationService.checkAddInfoByTitle("111",1L));
    }

    /**
     * 根据栏目ID查询文章测试
     */
    @Test
    public void testSelectByInfoType(){
        List<Information> list = new ArrayList<>();
        list.add(information);
        informationMapperMock.returns(list).selectByInfoType(1L);
        assertEquals(1,informationService.selectByInfoType(1L).size());
    }

    /**
     * 根据分页参数查询资讯列表4测试-前台用
     */
    @Test
    public void testQueryByPageBeanForSite(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("condition", selectBean.getCondition());
        map.put("searchText", selectBean.getSearchText());
        map.put("typeId", "1");
        map.put("isShow", "1");
        map.put("isThirdNews", "0");
        map.put("temp3", "0");
        informationMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum",pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(new Object());
        informationMapperMock.returns(list).queryByPageBean(map);
        assertEquals(0,informationService.queryByPageBeanForSite(pageBean,selectBean,1L).getList().size());
    }

    /**
     * 根据分页参数查询资讯列表测试-移动版前台用
     */
    @Test
    public void testQueryByPageBeanForMobSite(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeId", "1");
        map.put("isShow", "1");
        map.put("isThirdNews", "0");
        map.put("temp3", "1");
        informationMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum",pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(information);
        informationMapperMock.returns(list).queryByPageBean(map);
        assertEquals(0,informationService.queryByPageBeanForMobSite(pageBean,selectBean,1L).getList().size());

    }
}
