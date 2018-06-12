package com.ningpai.app.setting.index;

import com.ningpai.app.setting.Document;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 广告
 * Created by aqlu on 15/12/6.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Advert extends Document {

    public Advert(){}

    public Advert(String text, String img, String action, Integer ordering, String themeCode){
        this.text = text;
        this.img = img;
        this.action = action;
        this.ordering = ordering;
        this.themeCode = themeCode;
    }

    /**
     * 文本
     */
    private String text;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 动作
     */
    private String action;

    /**
     * 主题编码
     */
    private String themeCode = "home";

    /**
     * 动作参数
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Setter(AccessLevel.PROTECTED)
    private Map<String, Object> actionParam = new HashMap<>(0);

    /**
     * 排序值，值越小越靠前
     */
    private Integer ordering = 0;

    /**
     * 价格
     */
    private BigDecimal price = BigDecimal.ZERO;

    public Map<String, Object> putActionParam(String key, Object value) {
        this.actionParam.put(key, value);
        return actionParam;
    }

    public Map<String, Object> removeActionParam(String key) {
        this.actionParam.remove(key);
        return actionParam;
    }
}
