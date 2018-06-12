package com.ningpai.deposit.mapper;


import com.ningpai.deposit.bean.Deposit;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 余额账户
 */
@Repository
public interface DepositMapper {

    /**
     *查詢deposit
     * @param paramMap
     * @return
     */
    List<Deposit> depositList(Map<String, Object> paramMap);
    /**
     * 更新deposit
     * @param paramMap
     * @return
     */
    int updateDeposit(Map<String, Object> paramMap);

    /**
     * 总数
     * @param paramMap
     * @return
     */
    Long depositCount(Map<String, Object> paramMap);

    /**
     * 保存一个Deposit信息
     * @param deposit
     * @return
     */
    int saveDeposit(Deposit deposit);

    /**
     * 获取用户账户信息
     * @param paramMap
     * @return
     */
    Deposit getDeposit(Map<String, Object> paramMap);

    /**
     * 只更新密码，强接口，必须传customerId与密码
     * @param paramMap
     * @return
     */
    int resetPayPassword(Map<String, Object> paramMap);

    int resetDepositPasswordCount();
}
