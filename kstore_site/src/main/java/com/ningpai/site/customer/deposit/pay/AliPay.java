package com.ningpai.site.customer.deposit.pay;

import com.ningpai.common.util.alipay.config.AlipayConfig;
import com.ningpai.common.util.alipay.util.AlipaySubmit;
import com.ningpai.system.bean.Pay;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by Yang on 2016/9/21.
 */
public class AliPay extends AbstractPayStrategy{

    private static Logger LOGGER = Logger.getLogger(AliPay.class);

    public AliPay(Pay pay){
        super(pay);
    }

    @Override
    public String pay() {

        Pay pay = this.getPay();
        Map<String,String> paramMap = this.getParamMap();

        String showUrl = "";
        String antiPhishingKey = "";
        String exterInvokeIp = "";
        String notifyUrl = pay.getPayUrl() + "/deposit/alipaycallback.htm";
        String paymentType = "1";
        String returnUrl = pay.getPayUrl() +"/deposit/synalipayresult.htm";

        AlipayConfig.partner = this.getPay().getApiKey();
        AlipayConfig.key = this.getPay().getSecretKey();
        AlipayConfig.seller_email = this.getPay().getPayAccount();

        paramMap.put("service", "create_direct_pay_by_user");
        paramMap.put("partner", AlipayConfig.partner);
        paramMap.put("seller_email", AlipayConfig.seller_email);
        paramMap.put("_input_charset", AlipayConfig.input_charset);
        paramMap.put("return_url", returnUrl);
        paramMap.put("payment_type", paymentType);
        paramMap.put("show_url", showUrl);
        paramMap.put("anti_phishing_key", antiPhishingKey);
        paramMap.put("exter_invoke_ip", exterInvokeIp);
        paramMap.put("notify_url", notifyUrl);

        return AlipaySubmit.buildRequest(paramMap, "get", "确认");
    }
}
