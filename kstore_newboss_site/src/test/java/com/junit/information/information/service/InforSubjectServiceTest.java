package com.junit.information.information.service;

import com.ningpai.information.bean.InforSubject;
import com.ningpai.information.dao.InforSubjectMapper;
import com.ningpai.information.service.InfoUserDefinedService;
import com.ningpai.information.service.InforSubjectServcie;
import com.ningpai.information.service.impl.InforSubjectServcieImpl;
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
public class InforSubjectServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的接口类
     */
    @TestedObject
    private InforSubjectServcie inforSubjectServcie = new InforSubjectServcieImpl();

    @InjectIntoByType
    private Mock<InforSubjectMapper> inforSubjectMapperMock;

    /**
     * 实体类-资讯专题
     */
    private InforSubject inforSubject = new InforSubject();

    /**
     * 分页辅助类
     */
    private PageBean pageBean = new PageBean();


    /**
     * 初始化数据
     */
    @Override
    public void setUp(){
        inforSubject.setTitle("111");
    }

    /**
     * 根据主键删除测试
     */
    @Test
    public void testDeleteSubject(){
        inforSubjectMapperMock.returns(1).deleteByPrimaryKey(1L);
        assertEquals(1,inforSubjectServcie.deleteSubject(1L));
    }

    /**
     * 保存测试
     */
    @Test
    public void testSaveSubject(){
        Date date = new Date();
        inforSubject.setCreateDate(date);
        inforSubject.setUpdateDate(date);
        inforSubjectMapperMock.returns(1).insertSelective(inforSubject);
        assertEquals(1,inforSubjectServcie.saveSubject(inforSubject));
    }

    /**
     * 根据主键查询测试
     */
    @Test
    public void testGetSubject(){
        inforSubjectMapperMock.returns(inforSubject).selectByPrimaryKey(1L);
        assertNotNull(inforSubjectServcie.getSubject(1L));
    }

    /**
     * 修改测试
     */
    @Test
    public void testUpdateSubject(){
        inforSubject.setUpdateDate(new Date());
        inforSubjectMapperMock.returns(1).updateByPrimaryKeySelective(inforSubject);
        assertEquals(1,inforSubjectServcie.updateSubject(inforSubject));
    }

    /**
     * 分页查询PC端列表测试
     */
    @Test
    public void testSelectPCSubjectByPageBean(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "111");
        map.put("temp2", "1");
        map.put("isShow", null);
        inforSubjectMapperMock.returns(1).selectCountByTitle("111");
        map.put("startRowNum", pageBean.getStartRowNum());
        map.put("endRowNum", pageBean.getEndRowNum());
        List<Object> list = new ArrayList<>();
        list.add(inforSubject);
        inforSubjectMapperMock.returns(list).selectByPageBean(map);
        assertEquals(1,inforSubjectServcie.selectPCSubjectByPageBean(pageBean,"111").getList().size());
    }

    /**
     * 批量删除测试
     */
    @Test
    public void testBatchDeleteSubject(){
        List<Long> list = new ArrayList<Long>();
        list.add(1L);
        inforSubjectMapperMock.returns(1).batchDeleteByList(list);
        assertEquals(1,inforSubjectServcie.batchDeleteSubject(new Long[]{1L}));
    }

    /**
     * 查询所有专题测试
     */
    @Test
    public void testSelectAllForSite(){
        List<InforSubject> list = new ArrayList<>();
        list.add(inforSubject);
        inforSubjectMapperMock.returns(list).selectAllForSite();
        assertEquals(1,inforSubjectServcie.selectAllForSite().size());
    }

    /**
     * 验证url的唯一性测试
     */
    @Test
    public void testCheckByUrl(){
        inforSubjectMapperMock.returns(0).selectCountByUrl("111");
        assertTrue(inforSubjectServcie.checkByUrl("111"));
    }

    /**
     * 根据ID查看启用的专题测试
     */
    @Test
    public void testSelectByIsShowAndId(){
        inforSubjectMapperMock.returns(inforSubject).selectByIsShowAndId(1L);
        assertNotNull(inforSubjectServcie.selectByIsShowAndId(1L));
    }

    /**
     * 测试分页查询移动端列表
     */
    @Test
    public void testSelectMobileSubjectByPageBean() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "111");
        /** 设置开始行数 */
        map.put("startRowNum", pageBean.getStartRowNum());
        /** 设置PageBean的行数 */
        map.put("endRowNum", pageBean.getEndRowNum());
        map.put("isShow", null);
        // 设置temp2, 0:手机端, 1:pc端
        map.put("temp2", "0");

        //定义返回值
        List<Object> list = new ArrayList<>();
        list.add(inforSubject);
        inforSubjectMapperMock.returns(list).selectByPageBean(map);

        assertEquals(1, inforSubjectServcie.selectMobileSubjectByPageBean(pageBean, "111").getList().size());
    }
}

