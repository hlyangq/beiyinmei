package com.ningpai.app.setting.index;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 主题页保存表单
 * Created by zhangjin on 2016/6/22.
 */
@Data
public class ThemeSaveForm implements Serializable{
    private Advert advert;

    private List<Floor> floors;

    private String advertId;

    private List<String> floorIds;
}
