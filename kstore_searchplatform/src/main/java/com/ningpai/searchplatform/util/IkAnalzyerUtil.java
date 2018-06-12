package com.ningpai.searchplatform.util;

import com.ningpai.common.lucene.ikanalyzer.org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangck
 * @version 1.0
 * @since 15/11/27 14:12
 */
public class IkAnalzyerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IkAnalzyerUtil.class);

    /**
     * 使用IK分词器 进行分词
     * @param phrase
     * @return
     */
    public static List<String> segmentPhraseByIk(String phrase){
        if (phrase == null)
            throw new NullPointerException("待分词短语不能为空!");

        // 构建IK分词器，使用smart分词模式
        Analyzer analyzer = null;
        try {
            analyzer = new IKAnalyzer(true);
        } catch (Exception e) {
//            e.printStackTrace();\
            LOGGER.error("use IK has error {}", e.getLocalizedMessage());
            return new ArrayList<>();
        }

        return segmentPhraseByAnalyzer(analyzer, phrase);
    }

    /**
     * 标准分词器分词 ,中文会被切分为单字
     * @param phrase
     * @return
     */
    public static List<String> segmentPhraseByStandardAnalyzer(String phrase){
        if (phrase == null)
            throw new NullPointerException("待分词短语不能为空!");

        Analyzer analyzer = new StandardAnalyzer();
        return segmentPhraseByAnalyzer(analyzer, phrase);
    }

    /**
     * 指定分词器分词
     * @param analyzer
     * @param phrase
     * @return
     */
    public static List<String> segmentPhraseByAnalyzer(Analyzer analyzer, String phrase) {

        // 获取Lucene的TokenStream对象
        TokenStream ts = null;

        //最终返回的分词结果
        List<String> terms = new ArrayList<>();
        try {
            ts = analyzer.tokenStream("goodsInfoName", new StringReader(phrase));
            // 获取词元位置属性
            OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
            // 获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            // 获取词元文本属性
            TypeAttribute type = ts.addAttribute(TypeAttribute.class);

            // 重置TokenStream（重置StringReader）
            ts.reset();
            // 迭代获取分词结果
            while (ts.incrementToken()) {
                LOGGER.debug(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
//                System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
                //放入词
                terms.add(term.toString());
            }
            // 关闭TokenStream（关闭StringReader）
            ts.end();

        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            return new ArrayList<>();
        } finally {
            // 释放TokenStream的所有资源
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            }
        }
        //放入该短语
//        terms.add(phrase);
        return terms;
    }

}
