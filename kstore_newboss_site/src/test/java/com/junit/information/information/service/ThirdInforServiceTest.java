package com.junit.information.information.service;

import com.ningpai.information.bean.Information;
import com.ningpai.information.dao.ThirdInforMapper;
import com.ningpai.information.service.ThirdInforService;
import com.ningpai.information.service.impl.ThirdInforServiceImpl;
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
public class ThirdInforServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private ThirdInforService thirdInforService = new ThirdInforServiceImpl();

    @InjectIntoByType
    private Mock<ThirdInforMapper> thirdInforMapperMock;

    /**
     * 实体类-资讯文章
     */
    private Information information = new Information();

    /**
     * VO实体类-资讯文章
     */
    private InformationVo informationVo = new InformationVo();

    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();

    /**
     * 初始化数据
     */
    @Override
    public void setUp(){
        information.setInfoTypeId(1L);
        informationVo.setInfoTypeId(1L);
    }

    /**
     * 根据分页参数查询资讯列表测试
     */
    @Test
    public void testQueryByPageBean(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("searchText", "111");
        map.put("typeId", 1L);
        map.put("temp2", "temp2");
        thirdInforMapperMock.returns(1).queryTotalCount(map);
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum", pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(information);
        thirdInforMapperMock.returns(list).queryByPageBean(map);
        assertEquals(1,thirdInforService.queryByPageBean(pageBean,"111",1L,"temp2").getList().size());
    }

    /**
     * 根据主键删除资讯测试
     */
    @Test
    public void testDelInfo(){
        thirdInforMapperMock.returns(1).deleteByPrimaryKey(1L);
        assertEquals(1,thirdInforService.delInfo(1L));
    }

    /**
     * 批量删除资讯测试
     */
    @Test
    public void testBatchDelInfo(){
        thirdInforMapperMock.returns(1).deleteByPrimaryKey(1L);
        thirdInforService.batchDelInfo(new Long[]{1L});
    }

    /**
     * 添加资讯测试
     */
    @Test
    public void testSaveInfo(){
        Date date = new Date();
        information.setDelflag("0");
        information.setTemp1("1");
        information.setCreateDate(date);
        information.setUpdateDate(date);
        thirdInforMapperMock.returns(1).insert(information);
        assertEquals(1,thirdInforService.saveInfo(information));
    }

    /**
     * 更新资讯测试
     */
    @Test
    public void testUpdateInfo(){
        information.setUpdateDate(new Date());
        thirdInforMapperMock.returns(1).updateByPrimaryKeySelective(information);
        assertEquals(1,thirdInforService.updateInfo(information));
    }

    /**
     * 根据主键查找资讯测试
     */
    @Test
    public void testSelectByPrimaryKey(){
        thirdInforMapperMock.returns(information).selectByPrimaryKey(1L);
        assertNotNull(thirdInforService.selectByPrimaryKey(1L));
    }

    /**
     * 查找所有的资讯测试
     */
    @Test
    public void testSelectAll(){
        List<InformationVo> list = new ArrayList<>();
        list.add(informationVo);
        thirdInforMapperMock.returns(list).selectAll("111");
        assertEquals(1,thirdInforService.selectAll("111").size());
    }

    /**
     * 根据栏目ID获取文章数量验证栏目是否能删除测试
     */
    @Test
    public void testCheckDelInfoTypeByInfoCount(){
        thirdInforMapperMock.returns(0).selectInfoCountByTypeId(1L);
        assertTrue(thirdInforService.checkDelInfoTypeByInfoCount(1L));
    }

    /**
     * 根据文章标题查询文章数量判断标题是否存在测试
     */
    @Test
    public void testCheckAddInfoByTitle(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "111");
        map.put("temp2", "temp2");
        thirdInforMapperMock.returns(0).selectInfoCountByTitle(map);
        assertTrue(thirdInforService.checkAddInfoByTitle("111","temp2"));
    }

    /**
     * 根据文章ID查询出文章标题，判断老标题和新标题是否一样测试
     */
    @Test
    public void testCheckAddInfoByTitleTwo(){
        thirdInforMapperMock.returns(information).selectByPrimaryKey(1L);
        information.setTitle("111");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "111");
        map.put("temp2", "temp2");
        thirdInforMapperMock.returns(0).selectInfoCountByTitle(map);
        assertTrue(thirdInforService.checkAddInfoByTitle("111","temp2",1L));
    }

    /**
     * 根据栏目ID查询文章测试
     */
    @Test
    public void testSelectByInfoType(){
        List<InformationVo> list = new ArrayList<>();
        list.add(informationVo);
        thirdInforMapperMock.returns(list).selectByInfoType(1L);
        assertEquals(1,thirdInforService.selectByInfoType(1L).size());
    }
}

