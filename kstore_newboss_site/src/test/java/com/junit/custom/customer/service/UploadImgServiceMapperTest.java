package com.junit.custom.customer.service;

import com.ningpai.customer.dao.UploadImgMapper;
import com.ningpai.customer.service.UploadImgServiceMapper;
import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import java.util.HashMap;
import java.util.Map;

/**
 * UploadImgServiceMapperTest
 *
 * @author djk
 * @date 2016/3/21
 */
public class UploadImgServiceMapperTest extends UnitilsJUnit3 {

    /**
     * 需要测试的service
     */
    @TestedObject
    private UploadImgServiceMapper uploadImgServiceMapper = new UploadImgServiceMapper();

    /**
     * 模拟
     */
    @InjectIntoByType
    private Mock<UploadImgMapper> uploadImgMapperMock;

    @Test
    public void testUploadImg() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", 1L);
        paramMap.put("customerImg", "1");
        uploadImgMapperMock.returns(1).updateImg(paramMap);
        assertEquals(1, uploadImgServiceMapper.uploadImg(1L,"1"));
    }

}
