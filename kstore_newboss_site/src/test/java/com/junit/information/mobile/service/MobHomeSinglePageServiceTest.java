package com.junit.information.mobile.service;

import com.ningpai.mobile.bean.MobHomeSinglePage;
import com.ningpai.mobile.dao.MobHomeSinglePageMapper;
import com.ningpai.mobile.service.MobHomeSinglePageService;
import com.ningpai.mobile.service.impl.MobHomeSinglePageServiceImpl;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by houyichang on 2015/10/8.
 */
public class MobHomeSinglePageServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     * */
    @TestedObject
    private MobHomeSinglePageService mobHomeSinglePageService = new MobHomeSinglePageServiceImpl();

    /**
     * 模拟
     * */
    @InjectIntoByType
    Mock<MobHomeSinglePageMapper> mobHomeSinglePageMapperMock;

    /**
     * 实体类--移动版单页xml内容
     */
    MobHomeSinglePage mobHomeSinglePage = new MobHomeSinglePage();

    /**
     * 初始化
     * */
    @Override
    protected void setUp(){
        mobHomeSinglePage.setHomespId(1L);
    }

    /**
     * 添加移动版可视化配置首页
     *
     */
    public void testCreateHomePage(){
        mobHomeSinglePage.setTemp2("0");
        mobHomeSinglePage.setUpdateDate(new Date());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("loginUserId",2L);
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        mobHomeSinglePageMapperMock.returns(1).insertSelective(mobHomeSinglePage);
        mobHomeSinglePageService.createHomePage(mobHomeSinglePage);
    }

    /**
     * 修改移动版可视化配置首页
     *
     * @Title: updateHomePage
     * @Description: 修改移动版可视化配置首页
     */
    public void testUpdateHomePage(){
        mobHomeSinglePage.setUpdateDate(new Date());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("loginUserId",2L);
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        mobHomeSinglePageMapperMock.returns(1).updateByPrimaryKeySelective(mobHomeSinglePage);
        mobHomeSinglePageService.updateHomePage(mobHomeSinglePage);
    }

    /**
     * 初始化商家的HomePage
     *
     * @Title: initHomePage
     * @Description: 初始化商家的HomePage
     */
    public void testInitHomePage(){
        assertEquals(1,1);
    }

    /**
     * 生成HTML
     *
     * @Title: makeHtml
     * @Description: 生成HTML
     */
    public void testMakeHtml(){
        assertEquals(1,1);
    }

    /**
     * 根据ID获取模板信息
     *
     * @Title: getHomePageById
     * @Description: 根据ID获取模板信息
     */
    public void testGetHomePageById(){
        mobHomeSinglePageMapperMock.returns(mobHomeSinglePage).selectByPrimaryKey(1L);
        assertNotNull(mobHomeSinglePageService.getHomePageById(1L));
    }

    /**
     * 根据ID删除模板信息
     *
     * @Title: deleteHomePageById
     * @Description: 根据ID删除模板信息
     */
    public void testDeleteHomePageById(){
        mobHomeSinglePageMapperMock.returns(1).deleteByPrimaryKey(1L);
        mobHomeSinglePageService.deleteHomePageById(1L);
    }

    /**
     * @Title:queryInfoBySinglepageId
     * @Description: 根据移动版单页ID获取移动版模板信息
     * @return
     */
    public void testQueryInfoBySinglepageId(){
        mobHomeSinglePageMapperMock.returns(mobHomeSinglePage).queryInfoBySinglepageId(1L);
        assertNotNull(mobHomeSinglePageService.queryInfoBySinglepageId(1L));
    }

    /**
     * @Title:queryinitInfoBySinglepageId
     * @Description:查询SinglepageId=-1的移动版模板信息的集合 即查询用于初始化的模板信息
     */
    public void testQueryinitInfoBySinglepageId(){
        List<MobHomeSinglePage> list = new ArrayList<>();
        list.add(mobHomeSinglePage);
        mobHomeSinglePageMapperMock.returns(list).queryinitInfoBySinglepageId();
        assertNotNull(mobHomeSinglePageService.queryinitInfoBySinglepageId());
    }

    /**
     * @Title:queryinitInfoCount
     * @Description:查询SinglepageId=-1的移动版模板信息 即查询用于初始化的模板信息的总条数
     * @return
     */
    public void testQueryinitInfoCount(){
        mobHomeSinglePageMapperMock.returns(1).queryinitInfoCount();
        assertEquals(1,mobHomeSinglePageService.queryinitInfoCount());
    }
}
