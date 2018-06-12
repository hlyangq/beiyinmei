package com.junit.test.weixin.service;

import com.ningpai.m.weixin.bean.WXGroup;
import com.ningpai.m.weixin.dao.WXGroupMapper;
import com.ningpai.m.weixin.service.WXActivityService;
import com.ningpai.m.weixin.service.impl.WXActivityServiceImpl;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.Date;

/**
 * Created by wtp on 2016/3/30.
 */
public class WXActivityServiceTest extends UnitilsJUnit3 {

    /**
     * 需要测试的Service
     */
    @TestedObject
    private WXActivityService wxActivityService= new WXActivityServiceImpl();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<WXGroupMapper> wxGroupMapperMock;

    /**
     * 将微信用户的openid添加到群发组当中去
     */
    @Test
    public void testAddWxUserIdToGroup() {
        WXGroup group = new WXGroup();
        group.setOpenid("1");
        group.setGetTime(new Date());
        wxGroupMapperMock.returns(1).insertSelective(group);
        assertNotNull(wxActivityService.addWxUserIdToGroup("1"));
    }

    /**
     * 将微信用户的openid添加到群发组当中去
     */
    @Test
    public void testCheckOpenIdExist() {
        assertNotNull(wxActivityService.checkOpenIdExist("1"));
    }
}
