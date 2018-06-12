package com.ningpai.adv.bean;

import java.util.Date;

/**
 * 页面广告位对象
 * @author qiyuanyuan
 *
 */
public class Adv {
        /**
        *广告id
        */
        private Long advId;

        /**
        *广告名称
        */
        private String advName;

        /**
        *地址
        */
        private String advUrl;

        /**
        *广告地址
        */
        private String advPosition;

        /**
        *广告图片
        */
        private String advImg;

        /**
        *1显示
        */
        private String advShowFlag;

        /**
        *1是删除
        */
        private String advDelFlag;

        /**
        *创建时间
        */
        private Date advCreateTime;

        /**
        *修改时间
        */
        private Date advModifyTime;
        
        /**
         * 广告排序
         */
        private Integer advSort;

        public Long getAdvId() {
            return advId;
        }

        public void setAdvId(Long advId) {
            this.advId = advId;
        }

        public String getAdvName() {
            return advName;
        }

        public void setAdvName(String advName) {
            this.advName = advName;
        }

        public String getAdvUrl() {
            return advUrl;
        }

        public void setAdvUrl(String advUrl) {
            this.advUrl = advUrl;
        }

        public String getAdvPosition() {
            return advPosition;
        }

        public void setAdvPosition(String advPosition) {
            this.advPosition = advPosition;
        }

        public String getAdvImg() {
            return advImg;
        }

        public void setAdvImg(String advImg) {
            this.advImg = advImg;
        }

        public String getAdvShowFlag() {
            return advShowFlag;
        }

        public void setAdvShowFlag(String advShowFlag) {
            this.advShowFlag = advShowFlag;
        }

        public String getAdvDelFlag() {
            return advDelFlag;
        }

        public void setAdvDelFlag(String advDelFlag) {
            this.advDelFlag = advDelFlag;
        }
    /**
     * 获取时间
     * */
        public Date getAdvCreateTime() {
            return advCreateTime==null?null: (Date) advCreateTime.clone();
        }

        public void setAdvCreateTime(Date advCreateTime) {
            this.advCreateTime = advCreateTime;
        }
    /**
     * 获取时间
     * */
        public Date getAdvModifyTime() {
            return advModifyTime==null?null: (Date) advModifyTime.clone();
        }

        public void setAdvModifyTime(Date advModifyTime) {
            this.advModifyTime = advModifyTime;
        }

        public Integer getAdvSort() {
            return advSort;
        }

        public void setAdvSort(Integer advSort) {
            this.advSort = advSort;
        }
}
