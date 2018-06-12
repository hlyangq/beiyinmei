package com.ningpai.app.setting.index;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 主题返回实体
 * Created by zhangjin on 16/6/15.
 */
@Data
@AllArgsConstructor
public class ThemeResponse implements Serializable {
    private Advert advert;

    private List<Floor> floors;
}
