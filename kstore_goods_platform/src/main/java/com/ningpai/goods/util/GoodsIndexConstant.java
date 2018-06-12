package com.ningpai.goods.util;

import com.ningpai.goods.bean.EsGoodsInfo;
import com.ningpai.util.MyLogger;

import java.util.Properties;

/**
 * <p>
 * 索引常量
 * </p>
 *
 * @author liangck
 * @version 1.0
 * @since 15/8/19 10:38
 */
public final class GoodsIndexConstant {
    private static final MyLogger DEBUG = new MyLogger(GoodsIndexConstant.class);

    /**
     * 商品索引名
     */
    private static String PRODUCT_INDEX_NAME = "qm-test-item";

    //加载索引库名称
    static {
        try {
            Properties properties = new Properties();
            properties.load(GoodsIndexConstant.class.getClassLoader().getResourceAsStream("com/ningpai/web/config/es-hosts.properties"));
            String value = (String) properties.get("es.index-name");
            if (value != null) {
                PRODUCT_INDEX_NAME = value;
                DEBUG.info("load es index name is :" + value);
            } else {
                DEBUG.info("load es index name fail use default index name 'qm-test-item' ");
            }


        } catch (Exception e) {
            DEBUG.error("load es index name fail ,use default index name 'qm-test-item', catch exception ", e);
        }
    }

    /**
     * 获取索引库名称
     *
     * @return 索引库名称
     */
    public static final String PRODUCT_INDEX_NAME() {
        return PRODUCT_INDEX_NAME;
    }

    /**
     * 商品类型名
     */
    public static final String PRODUCT_TYPE = EsGoodsInfo.class.getSimpleName();

    /**
     * 构造
     */
    private GoodsIndexConstant() {

    }


}
