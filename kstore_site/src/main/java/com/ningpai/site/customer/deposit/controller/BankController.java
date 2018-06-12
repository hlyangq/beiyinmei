package com.ningpai.site.customer.deposit.controller;

import com.ningpai.deposit.bean.Bank;
import com.ningpai.site.customer.deposit.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Yang on 2016/9/18.
 */
@Controller
public class BankController {

    @Autowired
    private BankService bankService;

    @RequestMapping("/banklist")
    @ResponseBody
    public List<Bank> list() {
        List<Bank> bankList = bankService.list();
        return bankList;
    }
}
