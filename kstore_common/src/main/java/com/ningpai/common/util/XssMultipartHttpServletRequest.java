package com.ningpai.common.util;

import com.ningpai.util.MyLogger;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * XSSMultipartHttpServletRequest
 *
 * @author djk
 * @date 2015/11/13
 */
public class XssMultipartHttpServletRequest extends DefaultMultipartHttpServletRequest
{

    /**
     * 调试日志
     */
    private MyLogger logger = new MyLogger(XssMultipartHttpServletRequest.class);

    /**
     * Wrap the given HttpServletRequest in a MultipartHttpServletRequest.
     *
     * @param request             the servlet request to wrap
     * @param mpFiles             a map of the multipart files
     * @param mpParams            a map of the parameters to expose,
     * @param mpParamContentTypes
     */
    public XssMultipartHttpServletRequest(HttpServletRequest request, MultiValueMap<String, MultipartFile> mpFiles, Map<String, String[]> mpParams, Map<String, String> mpParamContentTypes) {
        super(request, mpFiles, mpParams, mpParamContentTypes);
    }

    /**
     * 重写父类方法
     * */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
            if (values == null) {
                return values;
            }
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = stripXSS(values[i], parameter);

                // 说明被过滤了。
                if (!values[i].equals(encodedValues[i]))
                {
                    logger.error("param is filterd by xssfilter and ip:"+super.getRemoteAddr());
                }
        }
        return encodedValues;
    }

    /**
     * 重写父类方法
     * */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return stripXSS(value, parameter);
    }

    /**
     * 重写父类方法
     * */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value, name);
    }

    /**
     * 过滤参数
     * @param value 参数值
     * @param parameter 参数name名
     * @return
     */
    private String stripXSS(String value, String parameter) {
        String valueNew = value;

        if("keyWords".equals(parameter)){
            System.out.println("==========================");
        }
        if (getNoCheckParameter(parameter) && valueNew != null) {

            // NOTE: It's highly recommended to use the ESAPI library and
            // uncomment the following line to
            // avoid encoded attacks.
            // value = ESAPI.encoder().canonicalize(value);
            // Avoid null characters
            valueNew = valueNew.replaceAll("", "");
            // Avoid anything between script tags

            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
                    Pattern.CASE_INSENSITIVE);
            valueNew = scriptPattern.matcher(valueNew).replaceAll("");

            scriptPattern = Pattern.compile("[%<>\"]+");
            valueNew = scriptPattern.matcher(valueNew).replaceAll("");

        }

        return valueNew;
    }

    /**
     * 判断name是否应该拦截
     * @param parameter 参数名
     * @return 不拦截返回true，拦截返回false
     */

    private boolean getNoCheckParameter(String parameter) {
        String[] noFilterURLs = new String[] { "mobileDesc","goodsDetailDesc",
                "goodsMobileDesc", "bsetUseragreement",
                "bsetUseragreementuser", "bsetCopyright", "content","thirdUserment",
                "helpContent", "marketingDes","giftDesc",
                "ipCont", "str", "pageDes", "title", "thirdProjectContext",
                "backInfoRemark","backPriceRemark","payHelp","giftText","logisticsSingleContent","couponRemark","code","operation","appOperation"// ,"chProvince","ch_city","ch_distinct"
        };

        for (String parameters : noFilterURLs) {
            if (parameter.equals(parameters)) {
                return false;
            }
        }
        return true;
    }

}
