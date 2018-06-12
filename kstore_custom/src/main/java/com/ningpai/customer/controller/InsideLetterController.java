/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.customer.controller;

import com.ningpai.customer.bean.InsideLetter;
import com.ningpai.customer.service.InsideLetterServiceMapper;
import com.ningpai.util.MyLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 站内信控制层
 * 
 * @author jiping
 * @since 2014年8月7日 下午12:01:56
 * @version 0.0.1
 */
@Controller
public class InsideLetterController {
    // spring注解
    private InsideLetterServiceMapper insiderLetterService;
    /** 记录日志对象 */
    private static final MyLogger LOGGER = new MyLogger(
            InsideLetterController.class);

    public InsideLetterServiceMapper getInsiderLetterService() {
        return insiderLetterService;
    }

    @Resource(name = "insideLetterServiceMapper")
    public void setInsiderLetterService(
            InsideLetterServiceMapper insiderLetterService) {
        this.insiderLetterService = insiderLetterService;
    }

    /**
     * 发送站内信息
     * 
     * @return
     */
    @RequestMapping("/sendinsideletter")
    @ResponseBody
    public Object addInsideLetter(InsideLetter insideLetter) {
        // 非空验证 站内信息标题
        if (null != insideLetter.getLetterTitle()) {
            try {
                insideLetter.setLetterTitle(java.net.URLDecoder.decode(
                        insideLetter.getLetterTitle(), "UTF-8"));
                insideLetter.setLetterContent(java.net.URLDecoder.decode(
                        insideLetter.getLetterContent(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // 日志记录
                LOGGER.info("发送站内信息错误" + e);
            }
            // 日志记录
            LOGGER.info("发送标题为:" + insideLetter.getLetterTitle() + "的站内信息！");
        }
        return insiderLetterService.insertSelective(insideLetter);
    }

    /**
     * 根据会员id添加站内信息
     *
     * @return
     */
    @RequestMapping("/insertNotices")
    @ResponseBody
    public Object insertNotices(InsideLetter insideLetter, Long[] customerIds) {
        // 非空验证 站内信息标题
        if (null != insideLetter.getLetterTitle()) {
            // 日志记录
            LOGGER.info("发送标题为:" + insideLetter.getLetterTitle() + "的站内信息！");
        }
        return insiderLetterService.insertNotices(insideLetter, customerIds);
    }

}
