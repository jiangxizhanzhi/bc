package com.cyberpeace.enums;


/**
 * 演示白名单 URI 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum DemonstrationWhiteUriEnum {
    WHITE("/cpms/demonstration/demonstrationWhite.json","classpath:static/demonstration/demonstrationWhite.json");



    private String filePath;

    private String systemPath;


    /**
     * 演示白名单 URI 枚举
     *
     * @param filePath   文件路径
     * @param systemPath 系统路径
     * @throws
     * @see String
     * @see String
     */
    DemonstrationWhiteUriEnum(String filePath, String systemPath) {
        this.filePath = filePath;
        this.systemPath = systemPath;
    }


    /**
     * 获取文件路径
     *
     * @return {@code String }
     * @throws
     * @see String
     */
    public String getFilePath() {
        return filePath;
    }


    /**
     * 获取系统路径
     *
     * @return {@code String }
     * @throws
     * @see String
     */
    public String getSystemPath() {
        return systemPath;
    }

}
