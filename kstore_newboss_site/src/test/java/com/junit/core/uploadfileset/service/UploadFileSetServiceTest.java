package com.junit.core.uploadfileset.service;

import org.junit.Test;
import org.unitils.UnitilsJUnit3;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

import com.ningpai.uploadfileset.bean.UploadFileSet;
import com.ningpai.uploadfileset.dao.UploadFileSetMapper;
import com.ningpai.uploadfileset.service.UploadFileSetService;
import com.ningpai.uploadfileset.service.UploadFileSetServiceImpl;

/**
 * 上传文件设置类测试
 * @author djk
 *
 */
public class UploadFileSetServiceTest  extends UnitilsJUnit3 
{
    /**
     * 需要测试的接口类
     */
    @TestedObject
	private UploadFileSetService uploadFileSetService = new UploadFileSetServiceImpl();
    
    @InjectIntoByType
    private Mock<UploadFileSetMapper> uploadFileSetMapperMock;
    
    /**
     *  获取当前的上传文件设置测试
     */
    @Test
    public void testGetCurrUploadFileSet()
    {
    	uploadFileSetMapperMock.returns(new UploadFileSet()).selectUploadfileset();
    	assertNotNull(uploadFileSetService.getCurrUploadFileSet());
    }
    
    /**
     * 修改上传文件设置测试
     */
    @Test
	public void testUpdateUploadFileSet()
	{
    	UploadFileSet set = new UploadFileSet();
    	uploadFileSetMapperMock.returns(1).updateByPrimaryKeySelective(set);
		assertEquals(1, uploadFileSetService.updateUploadFileSet(set));
	}
}
