package com.ningpai.common;

import org.apache.commons.lang.ObjectUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * EL表达式函数工具库
 * Created by aqlu on 16/3/10.
 */
public class ElFunctions {
    public static String toJsonString(Object obj){
        // 将java对象转换为json对象
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (IOException e) {
            return null;
        }
    }

    //如果长度过长，后面自动加上三个省略点
    public static String limitString(String obj, int maxLen) {
        if (maxLen < 1) {
            maxLen = 1;
        }
        String t_obj = ObjectUtils.toString(obj);
        if (t_obj.length() > maxLen) {
            return t_obj.substring(0, maxLen - 1).concat("...");
        }
        return t_obj;
    }

}
