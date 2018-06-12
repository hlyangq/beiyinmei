/*
 * Copyright 2013 NingPai, Inc. All rights reserved.
 * NINGPAI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ningpai.common.util;

import com.ningpai.common.bean.Sms;
import com.ningpai.system.bean.MessageServer;
import com.ningpai.system.dao.MessageServerMapper;
import com.ningpai.system.service.BasicSetService;
import com.ningpai.util.MSMSendUtil;
import com.ningpai.util.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信验证码辅助类
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年6月12日 下午3:44:51
 * @version 0.0.1
 */
@Service
public class SmsPost {

    /** 站点信息业务接口 */
    private static BasicSetService basicSetService;

    /** 短信服务器接口 */
    private static MessageServerMapper messageServerMapper;

    /**
     * 私有构造函数
     */
    private SmsPost() {

    }

    /**
     * 短信发送
     * 
     * @param sms
     *            接口帮助类
     * @return
     * @throws IOException
     */
    public static boolean sendPost(Sms sms) throws IOException {
        String content;
        Map<String, Object> map = new HashMap<>();
        PageBean pb = new PageBean();
        map.put("startRowNum", pb.getStartRowNum());
        map.put("endRowNum", pb.getEndRowNum());
        List<Object> list= messageServerMapper.selectAllByPb(map);
        if(list != null && ((MessageServer)(list.get(0))).getSmsContent() != null){
            content = ((MessageServer)(list.get(0))).getSmsContent();
            //后台短信接口设置中短信内容，“@”替换为验证码
            sms.setMsgContext(content.replaceAll("@",sms.getMsgContext()));
        }
        return MSMSendUtil.sendMsm("",sms.getLoginName(),sms.getPassword(),new String[]{sms.getSendSim()},sms.getMsgContext(),sms.getSmsKind(),sms.getHttpUrl());
    }


    /**
     * 短信发送
     *
     * @param sms
     *            接口帮助类
     * @return
     * @throws IOException
     */
        public static boolean sendPostOrder(Sms sms) throws IOException {
        return MSMSendUtil.sendMsm("",sms.getLoginName(),sms.getPassword(),new String[]{sms.getSendSim()},sms.getMsgContext(),sms.getSmsKind(),sms.getHttpUrl());
    }


    /**
     * 获取站点信息业务接口
     * 
     * @return
     */
    public BasicSetService getBasicSetService() {
        return basicSetService;
    }

    /**
     * 设置站点信息业务接口
     * 
     * @param basicSetService
     */
    @Resource(name = "basicSetService")
    public void setBasicSetService(BasicSetService basicSetService) {
        SmsPost.basicSetService = basicSetService;
    }

    public MessageServerMapper getMessageServerMapper() {
        return messageServerMapper;
    }

    @Resource(name = "messageServerMapper")
    public void setMessageServerMapper(MessageServerMapper messageServerMapper) {
        SmsPost.messageServerMapper = messageServerMapper;
    }

}
