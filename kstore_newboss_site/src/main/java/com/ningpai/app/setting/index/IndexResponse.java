package com.ningpai.app.setting.index;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页响应对象
 * Created by aqlu on 15/12/6.
 */
@Data
@AllArgsConstructor
public class IndexResponse implements Serializable {

    private List<Slider> sliders;

    private List<Menu> menus;

    private List<Advert> adverts;

    private List<Floor> floors;
}