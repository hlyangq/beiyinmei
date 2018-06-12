package com.junit.custom.customer.service;

import com.ningpai.customer.bean.PunishRecord;
import com.ningpai.customer.dao.PunishRecordMapper;
import com.ningpai.customer.service.PunishRecordService;
import com.ningpai.customer.service.impl.PunishRecordServiceImpl;
import com.ningpai.manager.bean.Page;
import com.ningpai.util.PageBean;
import com.ningpai.util.SelectBean;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * PunishRecordServiceTest
 *
 * @author djk
 * @date 2016/3/21
 */
public class PunishRecordServiceTest  extends UnitilsJUnit3 {

    /**
     * 需要测试的service
     */
    @TestedObject
    private PunishRecordService punishRecordService = new PunishRecordServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<PunishRecordMapper> punishRecordMapperMock;

    /**
     * 添加一条记录测试
     */
    @Test
    public void testAddPunishRecord() {
        PunishRecord punishRecord = new PunishRecord();
        punishRecordMapperMock.returns(1).insertSelective(punishRecord);
        assertEquals(1, punishRecordService.addPunishRecord(punishRecord));
    }

    /**
     * 根据商家id查询测试
     */
    @Test
    public void testQueryInfoByThirdId() {
        punishRecordMapperMock.returns(new PunishRecord()).queryInfoByThirdId(1L);
        assertNotNull(punishRecordService.queryInfoByThirdId(1L));
    }

    /**
     * 询记录测试
     */
    @Test
    public void testQueryInfoByTidandDate() {
        punishRecordMapperMock.returns(new ArrayList<>()).queryInfoByTidandDate(1L);
        assertNotNull(punishRecordService.queryInfoByTidandDate(1L));
    }

    /**
     * 第三方显示处罚记录
     */
    @Test
    public void testSelectRecordByPage() {
        PageBean pageBean = new PageBean();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("startRowNum", pageBean.getStartRowNum());
        paraMap.put("endRowNum", pageBean.getEndRowNum());
        paraMap.put("thirdId", 1L);
        punishRecordMapperMock.returns(new ArrayList<>()).selectRecordByPage(paraMap);
        punishRecordMapperMock.returns(1).selectAllCountByTid(1L);
        assertNotNull(punishRecordService.selectRecordByPage(pageBean, 1L));
    }

    @Test
    public void testSelectPunishedRecordByPage() {

        PageBean pageBean = new PageBean();
        SelectBean selectBean = new SelectBean();
        selectBean.setSearchText("a");
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("startRowNum", pageBean.getStartRowNum());
        paraMap.put("endRowNum", pageBean.getEndRowNum());
        paraMap.put("condition", selectBean.getCondition());
        paraMap.put("searchText", selectBean.getSearchText().trim());
        punishRecordMapperMock.returns(new ArrayList<>()).selectPunishedRecordByPage(paraMap);
        punishRecordMapperMock.returns(1).selectPunishedAllCountByTid(paraMap);
        assertNotNull(punishRecordService.selectPunishedRecordByPage(pageBean, selectBean));
    }


}
