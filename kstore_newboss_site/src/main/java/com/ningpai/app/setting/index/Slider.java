package com.ningpai.app.setting.index;

import com.ningpai.app.setting.Document;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 轮播
 * Created by aqlu on 15/12/6.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Slider extends Document {

    public Slider(){}

    public Slider(String img, String action, Integer ordering){
        this.img = img;
        this.action = action;
        this.ordering = ordering;
    }

    /**
     * 图片地址
     */
    private String img;

    /**
     * 动作
     */
    private String action;

    /**
     * 动作参数
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Setter(AccessLevel.PROTECTED)
    private Map<String, Object> actionParam = new HashMap<>(0);

    /**
     * 排序值，值越小越靠前
     */
    private Integer ordering;

    public Map<String, Object> putActionParam(String key, Object value) {
        this.actionParam.put(key, value);
        return actionParam;
    }

    public Map<String, Object> removeActionParam(String key) {
        this.actionParam.remove(key);
        return actionParam;
    }
}
