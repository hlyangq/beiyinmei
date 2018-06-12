package com.ningpai.third.register.bean;

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
 * Created by zhanghailong on 2015/5/7.
 */
@Service
public class SmsPost {

    /** 站点信息业务接口 */
    private static BasicSetService basicSetService;

    /** 短信服务器接口 */
    private static MessageServerMapper messageServerMapper;


    /**
     * 构造方法
     */
    private SmsPost() {

    }

    /**
     * 短信发送
     *
     * @param sms
     *            接口帮助类
     * @return
     * @throws java.io.IOException
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
        return MSMSendUtil.sendMsm("", sms.getLoginName(), sms.getPassword(), new String[]{sms.getSendSim()}, sms.getMsgContext(),sms.getSmsKind(),sms.getHttpUrl());
    }

    /** 站点信息业务接口GET方法 */
    public BasicSetService getBasicSetService() {
        return basicSetService;
    }

    /** 站点信息业务接口GET方法 */
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
