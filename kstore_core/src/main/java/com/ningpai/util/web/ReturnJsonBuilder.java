package com.ningpai.util.web;

import com.alibaba.fastjson.JSONObject;

public class ReturnJsonBuilder {
    private JSONObject json = new JSONObject();

    public ReturnJsonBuilder data(String key, Object value) {
        json.put(key, value);
        return this;
    }

    public ReturnJsonBuilder code(String code) {
        return data("code", code);
    }

    public ReturnJsonBuilder msg(String msg) {
        return data("msg", msg);
    }

    public JSONObject build() {
        return json;
    }

    public JSONObject defaultVal() {
        json.put("code", "0");
        json.put("msg", "success");
        return json;
    }

}