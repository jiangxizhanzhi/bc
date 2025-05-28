package com.cyberpeace.enums;


/**
 * 演示白色 URI 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Enum
 * @since 1.0.0
 */
public enum DemonstrationWhiteUriEnum {
    WHITE("/cpms/demonstration/demonstrationWhite.json","classpath:static/demonstration/demonstrationWhite.json");



    private String filePath;

    private String systemPath;


    DemonstrationWhiteUriEnum(String filePath, String systemPath) {
        this.filePath = filePath;
        this.systemPath = systemPath;
    }



    public String getFilePath() {
        return filePath;
    }


    public String getSystemPath() {
        return systemPath;
    }

}
