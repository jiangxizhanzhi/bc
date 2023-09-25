package com.cyberpeace.enums;



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
