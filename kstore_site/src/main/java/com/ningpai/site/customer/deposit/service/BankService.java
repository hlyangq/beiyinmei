package com.ningpai.site.customer.deposit.service;

import com.ningpai.deposit.bean.Bank;
import com.ningpai.deposit.mapper.BankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by youzipi on 16/10/11.
 */
@Service
public class BankService {

    @Autowired
    private BankMapper mapper;

    public List<Bank> list() {
        return mapper.list();
    }

    public Bank findById(Long id) {
        return mapper.selectById(id);
    }
}
