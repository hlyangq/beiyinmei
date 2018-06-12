/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.ningpai.comment.bean;

import java.io.Serializable;

/**
 * 晒单图片Bean
 * 
 * @author NINGPAI-zhangqiang
 * @since 2014年7月1日 上午9:51:15
 * @version 0.0.1
 */
public class ShareImg implements Serializable
{
	
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 7816670239507915866L;

	/**
     * 主键
     */
    private Long imageId;

    /**
     * 晒单信息id
     */
    private Long shareId;

    /**
     * 晒单图片路径
     */
    private String imageName;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
