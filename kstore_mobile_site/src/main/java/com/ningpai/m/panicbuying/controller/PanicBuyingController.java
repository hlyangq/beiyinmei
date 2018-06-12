package com.ningpai.m.panicbuying.controller;

import com.ningpai.m.panicbuying.bean.MarketingRushUtil;
import com.ningpai.m.panicbuying.service.PanicBuyingService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * PanicBuyingController
 * 抢购控制器
 * @author djk
 * @date 2016/1/18
 */
@Controller
public class PanicBuyingController {

    /**
     * 调试日志
     */
    private static final Logger DEBUG = Logger.getLogger(PanicBuyingController.class);

    /**
     * 注入抢购服务接口
     */
    @Resource
    private PanicBuyingService panicBuyingService;

    /**
     * 抢购秒杀列表
     *
     * @param request 请求
     * @return 返回到抢购页面
     */
    @RequestMapping("/panicbuyinglist")
    public ModelAndView selectPanicBuyingList(HttpServletRequest request) {

        // 查询抢购列表
        List<MarketingRushUtil> lists = panicBuyingService.queryMarketingRushUtils(request);

        return new ModelAndView("panicbuying/progrouplist").addObject("rushes", lists).
                addObject("ChannelAdvers", panicBuyingService.queryChannelAdvers());
    }
}
