package com.ningpai.deposit.service;

import com.ningpai.deposit.bean.Deposit;
import com.ningpai.deposit.mapper.DepositMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youzipi on 16/10/14.
 */
@Service
public class DepositService {


    @Autowired
    private DepositMapper depositMapper;

    @Transactional
    public Deposit getDeposit(Map<String, Object> paramMap) {
        checkAndCreate(paramMap);
        Deposit deposit = depositMapper.getDeposit(paramMap);
        return deposit;
    }

    public Deposit findByCustomerId(Long customerId) {
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("customerId", customerId);
        return getDeposit(paramMap);
    }

    public void checkAndCreate(Map<String, Object> paramMap) {
        Long count = depositMapper.depositCount(paramMap);
        //判断是不是空，是空默认新建一个
        if (count == 0) {
            Deposit deposit = new Deposit();
            Long customerId = (Long) paramMap.get("customerId");
            deposit.setCustomerId(customerId);
            if (null != customerId) {
                saveDeposit(deposit);
            }
        }
    }

    @Transactional
    public int saveDeposit(Deposit deposit) {
        int result = depositMapper.saveDeposit(deposit);
        return result;
    }

    @Transactional
    public int updateDeposit(Map<String, Object> paramMap) {

        if(paramMap.get("preDeposit") != null && paramMap.get("preDeposit").toString().equals("0.00")){
            paramMap.put("preDeposit","0");
        }
        if(paramMap.get("freezePreDeposit") != null && paramMap.get("freezePreDeposit").toString().equals("0.00")){
            paramMap.put("freezePreDeposit","0");
        }

        int result = depositMapper.updateDeposit(paramMap);
        return result;
    }

    public int resetDepositPasswordCount() {
        return depositMapper.resetDepositPasswordCount();
    }

    /**
     * 修改密码错误次数
     * @param customerId
     * @param nextErrorCount 修改后的密码错误次数
     * @return
     */
    public int updateErrorCount(Long customerId, int nextErrorCount) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("customerId", customerId);
        map.put("passwordErrorCount", nextErrorCount);
        if (nextErrorCount == 3) {
            map.put("passwordTime", new Date());
        }
        return depositMapper.updateDeposit(map);
    }
}
