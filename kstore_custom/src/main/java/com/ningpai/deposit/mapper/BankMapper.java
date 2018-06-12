package com.ningpai.deposit.mapper;

import com.ningpai.deposit.bean.Bank;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Yang on 2016/9/18.
 */
@Repository
public interface BankMapper {

    List<Bank> list();

    Bank selectById(Long id);
}
