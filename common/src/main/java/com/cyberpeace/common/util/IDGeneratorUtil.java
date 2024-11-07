package com.cyberpeace.common.util;

import java.util.UUID;

/**
 * idgenerator 实用程序
 *
 * @author SN
 * @date 2024/11/07
 */
public class IDGeneratorUtil {

    /**
     * idgenerator 实用程序
     */
    private IDGeneratorUtil(){}

    /**
     * 获取id
     *
     * @return {@code String }
     */
    public static String getID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
