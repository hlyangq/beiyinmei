package com.ningpai.site.customer.deposit.pay;

import com.ningpai.site.util.MD5Util;
import com.ningpai.system.bean.Pay;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yang on 2016/9/22.
 */
public class QianmiPay extends AbstractPayStrategy {

    private static final String UTF_8 = "UTF-8";

    public QianmiPay(Pay pay){
        super(pay);
    }

    @Override
    public String pay() {

        Pay pay = this.getPay();
        Map<String,String> paramMap = this.getParamMap();

        String orderNo = paramMap.get("orderCode");
        String buyerUserId = pay.getPayAccount();
        String sellerUserId = pay.getPayAccount();
        String settleAmount = paramMap.get("totalFee");
        String productName = paramMap.get("productName");
        String adminUserId = pay.getSecretKey();
        String callBackUrl = pay.getPayUrl()+"/deposit/synqianmipayresult.htm";
        String fromIp = "";
        try{
            fromIp = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception ex){

        }
        String tradeChannel = "SYY";
        String goodsUrl = "";
        String settleType = "1";
        String sysId = "901";
        String cssStyle = "style-orange.css";
        String amount = paramMap.get("totalFee");
        String notifyUrl = pay.getPayUrl()+"deposit/qianmipaycallback.htm";

        String key = pay.getApiKey();

        StringBuilder sb = new StringBuilder();

        sb.append(adminUserId).append(amount)
                .append(buyerUserId).append(callBackUrl)
                .append(cssStyle).append(fromIp)
                .append(goodsUrl).append(notifyUrl)
                .append(orderNo).append(productName)
                .append(sellerUserId).append(settleAmount)
                .append(settleType).append(sysId)
                .append(tradeChannel).append(key);
        //加密签名
        String sign = MD5Util.md5Hex(MD5Util.getContentBytes(sb.toString(), UTF_8));

        Map<String, String> tParams = new HashMap<String, String>();

        tParams.put("orderNo",orderNo);
        tParams.put("buyerUserId", buyerUserId);
        tParams.put("sellerUserId", sellerUserId);
        tParams.put("settleAmount", settleAmount);
        tParams.put("productName", productName);
        tParams.put("adminUserId", adminUserId);
        tParams.put("callBackUrl", callBackUrl);
        tParams.put("fromIp", fromIp);
        tParams.put("tradeChannel", tradeChannel);
        tParams.put("goodsUrl", goodsUrl);
        tParams.put("settleType", settleType);
        tParams.put("amount", amount);
        tParams.put("sysId", sysId);
        tParams.put("cssStyle", cssStyle);
        tParams.put("notifyUrl", notifyUrl);
        tParams.put("sign", sign);
        String html = buildDom("https://cashier.qianmi.com/home/payment", tParams);
        return html;
    }

    private String buildDom(String action,Map<String, String> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("<form id = \"form\" action=\"" + action + "\" method=\"post\">");
        if(!CollectionUtils.isEmpty(fields)){
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\"/>");
            }
        }
        sb.append("</form>");
        sb.append("<script type=\"text/javascript\">");
        sb.append("document.all.form.submit();");
        sb.append("</script>");
        return sb.toString();
    }
}
