package com.ningpai.searchplatform.annotation;


import com.ningpai.searchplatform.enumeration.ESAnalyzer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liangck
 * @version 1.0
 * @since 15/11/26 17:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EnableSuggest {

    String suggestName() default "suggest";

    /**
     * 分词器
     * @return {@link ESAnalyzer}
     */
    ESAnalyzer analyzerType() default ESAnalyzer.not_analyzed;

}
