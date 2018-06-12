package com.ningpai.m.deposit.vo;

import com.ningpai.m.deposit.bean.ChargeWithdraw;
import com.ningpai.m.deposit.bean.TradeInfo;

/**
 * 提现记录详细信息
 *
 * Created by chenpeng on 2016/10/18.
 */
public class TradeInfoVo extends TradeInfo {
    private ChargeWithdraw chargeWithdraw;

    public ChargeWithdraw getChargeWithdraw() {
        return chargeWithdraw;
    }

    public void setChargeWithdraw(ChargeWithdraw chargeWithdraw) {
        this.chargeWithdraw = chargeWithdraw;
    }
}
