package com.ningpai.app.setting.index;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 首页配置保存表单
 * Created by aqlu on 16/2/29.
 */
@Data
public class IndexSaveForm implements Serializable {
    private List<Slider> sliders;

    private List<Advert> adverts;

    private List<Floor> floors;

    private List<Menu> menus;
}
