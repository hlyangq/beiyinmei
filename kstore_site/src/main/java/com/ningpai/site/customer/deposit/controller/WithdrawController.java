package com.ningpai.site.customer.deposit.controller;

import com.alibaba.fastjson.JSONObject;
import com.ningpai.common.annotation.LoginRequired;
import com.ningpai.common.util.BaseSiteController;
import com.ningpai.deposit.bean.Deposit;
import com.ningpai.deposit.bean.WithdrawForm;
import com.ningpai.deposit.bean.WithdrawVo;
import com.ningpai.index.service.TopAndBottomService;
import com.ningpai.site.customer.deposit.service.BankService;
import com.ningpai.site.customer.deposit.service.SiteDepositService;
import com.ningpai.site.customer.deposit.service.SiteWithdrawService;
import com.ningpai.util.PageBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by youzipi on 16/10/9.
 */
@Controller
public class WithdrawController extends BaseSiteController {

    @Autowired
    TopAndBottomService topAndBottomService;

    @Autowired
    private SiteWithdrawService siteWithdrawService;

    @Autowired
    private SiteDepositService depositService;

    @Autowired
    private BankService bankService;

    @RequestMapping(value = "/deposit/withdraw-list", method = RequestMethod.GET)
    @LoginRequired
    public ModelAndView list(WithdrawForm form) {
        Long customerId = customerId();
        form.setCustomerId(customerId);
        PageBean pb = siteWithdrawService.findByCustomerIdAndStatus(form);

        Deposit deposit = depositService.findByCustomerId(customerId);
        ModelAndView mav = new ModelAndView("deposit/withdraw_list");
        mav.addObject("pb", pb);
        mav.addObject("form", form);
        mav.addObject("pwdExist", StringUtils.isNotBlank(deposit.getPayPassword()));
        return withTopAndBottom(mav);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deposit/withdraw", params = "id", method = RequestMethod.GET)
    public ModelAndView info(Long id) {

        WithdrawVo vo = siteWithdrawService.findVoById(id,customerId());

        ModelAndView mav = new ModelAndView("deposit/withdraw_edit");

        mav.addObject("vo", vo);
        return withTopAndBottom(mav);
    }

    /**
     * new
     *
     * @return
     */
    @RequestMapping(value = "/deposit/withdraw/new", method = RequestMethod.GET)
    @LoginRequired
    public ModelAndView add() {
        Deposit deposit = depositService.findByCustomerId(customerId());
        ModelAndView mav = new ModelAndView("deposit/withdraw_new");
        mav.addObject("deposit", deposit);
        return withTopAndBottom(mav);
    }

    /**
     * 提交 提现申请
     *
     * @param vo
     * @return
     */

    @RequestMapping(value = "/deposit/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(WithdrawVo vo) {
        vo.setCustomerId(customerId());
        return siteWithdrawService.create(vo);

//        return redirect("/deposit/withdraw-list");
    }

    /**
     * 提交 提现申请
     *
     * @param id
     * @return
     */

    @RequestMapping(value = "/deposit/withdraw/cancel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cancel(Long id) {
        return siteWithdrawService.cancel(id,customerId());

//        return redirect("/deposit/withdraw-list");
    }

    /**
     * 提交 提现申请
     *
     * @param id
     * @return
     */

    @RequestMapping(value = "/deposit/withdraw/confirm", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject confirm(Long id) {
//        Long customerId = customerId();
        return siteWithdrawService.confirm(id,customerId());

//        return redirect("/deposit/withdraw-list");
    }


    private ModelAndView withTopAndBottom(ModelAndView mav) {
        return topAndBottomService.getTopAndBottom(mav);
    }

}
