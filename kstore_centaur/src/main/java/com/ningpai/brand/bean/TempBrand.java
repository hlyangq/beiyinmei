package com.ningpai.brand.bean;

/**
 * 
 * @author ggn Ｅ店宝需要使用的实体
 */
public class TempBrand {

    /*
     * E店宝品牌编号
     */
    private String brand_code;
    /*
     * E店宝品牌名称
     */
    private String brand_name;
    /*
     * E店宝品牌的字段 暂时无用
     */
    private String enable;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_code() {
        return brand_code;
    }

    public void setBrand_code(String brand_code) {
        this.brand_code = brand_code;
    }
}
