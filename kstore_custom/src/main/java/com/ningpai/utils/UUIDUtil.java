package com.ningpai.utils;

import com.ningpai.util.MyLogger;

/**
 * <p>
 * </p>
 *
 * @version 1.0
 */
public class UUIDUtil {

    public static final MyLogger LOGGER = new MyLogger(UUIDUtil.class);
    private static UIDFactory uuid = null;

    static {
        try {
            uuid = UIDFactory.getInstance("UUID");
        } catch (Exception unsex) {
            LOGGER.info(unsex);
        }
    }

    /**
     * Constructor for the UUIDGener object
     */
    private UUIDUtil() {
    }

    /**
     * 获取uuid字符
     *
     * @author lihe 2013-7-4 下午5:31:09
     * @return
     * @see
     * @since
     */
    public static String getUUID() {
        return uuid.getNextUID();
    }
}
