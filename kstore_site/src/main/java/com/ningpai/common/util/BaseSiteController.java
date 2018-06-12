package com.ningpai.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by youzipi on 16/10/9.
 */
public class BaseSiteController {

    private static final String CUSTOMER_ID = "customerId";


    public Object requestParam(String name) {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(name);
    }

     public String redirect(String path) {
        StringBuilder sb = new StringBuilder();
        sb.append("redirect:")
                .append(path)
                .append(".htm");
        return sb.toString();
    }

    public Long customerId() {
        return (Long) requestParam(CUSTOMER_ID);
    }
}
