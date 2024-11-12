package com.cyberpeace.enums;


/**
 * 演示白色uri枚举
 *
 * @author admin
 * @date 2024/11/12
 */
public enum DemonstrationWhiteUriEnum {
    WHITE("/cpms/demonstration/demonstrationWhite.json","classpath:static/demonstration/demonstrationWhite.json");


    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 系统路径
     */
    private String systemPath;


    /**
     * 演示白色uri枚举
     *
     * @param filePath   文件路径
     * @param systemPath 系统路径
     */
    DemonstrationWhiteUriEnum(String filePath, String systemPath) {
        this.filePath = filePath;
        this.systemPath = systemPath;
    }


    /**
     * 获取文件路径
     *
     * @return {@link String }
     */
    public String getFilePath() {
        return filePath;
    }


    /**
     * 获取系统路径
     *
     * @return {@link String }
     */
    public String getSystemPath() {
        return systemPath;
    }

}
