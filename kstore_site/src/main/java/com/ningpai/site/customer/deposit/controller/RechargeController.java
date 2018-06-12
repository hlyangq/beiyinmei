package com.ningpai.site.customer.deposit.controller;

import com.ningpai.deposit.bean.Deposit;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.site.customer.deposit.service.SiteDepositService;
import com.ningpai.site.customer.vo.CustomerConstantStr;
import com.ningpai.system.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值控制器
 */
@Controller
public class RechargeController {

    private static final String PAYLIST = "payList";

    @Autowired
    TopAndBottomService topAndBottomService;

    @Resource(name = "payService")
    private PayService payService;

    @Autowired
    SiteDepositService depositService;
    /**
     * 充值页面
     * @param request
     * @return
     */
    @RequestMapping("/deposit/rechargeview")
    public ModelAndView rechargeView(HttpServletRequest request){

        Long customerId = (Long) request.getSession().getAttribute(CustomerConstantStr.CUSTOMERID);
        if (customerId == null) {
            ModelAndView mav = new ModelAndView(CustomerConstantStr.LOGINREDIRECT);
            return topAndBottomService.getTopAndBottom(mav);
        }
        ModelAndView mav = new ModelAndView("deposit/recharge");
        mav.addObject("customerId",customerId);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(PAYLIST, payService.queryAllPaySet());
        mav.addObject("map", paramMap);
        //查预存款信息
        paramMap.put("customerId",customerId);
        List<Deposit> list = depositService.depositList(paramMap);

        int size = 0;
        if(!CollectionUtils.isEmpty(list)){
            size = list.size();
        }
        Deposit tDeposit = null;
        BigDecimal total = BigDecimal.valueOf(0.00);
        for(int i=0;i<size;i++){
            tDeposit = list.get(i);
            if(tDeposit.getPreDeposit()!=null){
                total = total.add(tDeposit.getPreDeposit());
            }
            if(tDeposit.getFreezePreDeposit()!=null){
                total = total.add(tDeposit.getFreezePreDeposit());
            }
        }
        //total =total.setScale(2, BigDecimal.ROUND_HALF_UP);
        mav.addObject("totalDeposit", total);
        return topAndBottomService.getTopAndBottom(mav);
    }
}
