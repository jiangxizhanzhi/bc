package com.cyberpeace.common.util;

import java.util.UUID;

/**
 * idgenerator 实用程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public class IDGeneratorUtil {

    /**
     * idgenerator 实用程序
     *
     * @throws
     */
    private IDGeneratorUtil(){}

    /**
     * 获取id
     *
     * @return {@code String }
     * @throws
     * @see String
     */
    public static String getID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
