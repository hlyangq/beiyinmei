package com.ningpai.site.customer.deposit.pay;

import com.ningpai.common.util.ConstantUtil;
import com.ningpai.common.util.alipay.util.UtilDate;
import com.ningpai.system.bean.Pay;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Yang on 2016/9/21.
 */
public class UnionPay extends AbstractPayStrategy {

    private static Logger LOGGER = Logger.getLogger(AliPay.class);

    @Override
    public String pay() {

        Pay pay = this.getPay();
        Map<String,String> paramMap = this.getParamMap();

        SDKConfig.getConfig().loadPropertiesFromSrc();
        String requestUrl = SDKConfig.getConfig().getFrontRequestUrl();
        String returnUrl = this.getPay().getPayUrl()+"/deposit/synunionpayresult.htm";
        String merId = this.getPay().getPayAccount();

        String notifyUrl = pay.getPayUrl() + "/deposit/unionpaycallback.htm";
        // 版本号
        paramMap.put("version", "5.0.0");
        // 字符集编码
        paramMap.put("encoding", ConstantUtil.UTF);
        // 签名方法 01 RSA
        paramMap.put("signMethod", "01");
        // 交易类型
        paramMap.put("txnType", "01");
        // 交易子类型 01:自助消费 02:订购 03:分期付款
        paramMap.put("txnSubType", "01");
        // 业务类型
        paramMap.put("bizType", "000201");
        //渠道类型
        paramMap.put("channelType", "07");
        // 接入类型 商户:0 收单:1
        paramMap.put("accessType", "0");
        // 交易币种
        paramMap.put("currencyCode", "156");
        // 商户号码
        paramMap.put("merId", merId);
        //前台通知地址
        paramMap.put("frontUrl", returnUrl);

        paramMap.put("backUrl", notifyUrl);
        // 订单发送时间
        paramMap.put("txnTime", getDateTime("yyyyMMddHHmmss"));
        // 交易金额 分

        Map<String, String> submitFromData = signData(this.getParamMap());
        String html = buildDom(requestUrl, submitFromData);
        return html;
    }

    public UnionPay(Pay pay){
        super(pay);
    }

    /**
     * 对数据进行签名
     * @param contentData
     * @return　签名后的map对象
     */
    private Map<String, String> signData(Map<String, ?> contentData) {
        Map.Entry<String, String> obj;
        Map<String, String> submitFromData = new HashMap<String, String>();
        for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext(); ) {
            obj = (Map.Entry<String, String>) it.next();
            String value = obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                submitFromData.put(obj.getKey(), value.trim());
            }
        }
        SDKUtil.sign(submitFromData, "UTF-8");
        return submitFromData;
    }


    /**
     * 组装form
     * @param action
     * @param fields
     * @return
     */
    private String buildDom(String action, Map<String,String> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("<form id = \"form\" action=\"" + action + "\" method=\"post\">");
        Iterator<Map.Entry<String, String>> it = null;
        if(!CollectionUtils.isEmpty(fields)){
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\"/>");
            }
        }
        sb.append("</form>");
        //sb.append("</body>");
        sb.append("<script type=\"text/javascript\">");
        sb.append("document.all.form.submit();");
        sb.append("</script>");
        return sb.toString();
    }

    /**
     * 获取日期时间
     * @param formatter
     * @return
     */
    private String getDateTime(String formatter) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(formatter);
        return df.format(date);
    }
}
