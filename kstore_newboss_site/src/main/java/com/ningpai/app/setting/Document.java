package com.ningpai.app.setting;

import lombok.Data;

import java.io.Serializable;

/**
 * ES文档基类
 * Created by aqlu on 16/3/2.
 */
@Data
public class Document implements Serializable {
    private String id;
}
