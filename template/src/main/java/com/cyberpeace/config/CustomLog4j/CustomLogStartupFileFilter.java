package com.cyberpeace.config.CustomLog4j;

import java.io.File;
import java.io.FileFilter;

class CustomLogStartupFileFilter implements FileFilter {
    private String serverName;


    public CustomLogStartupFileFilter(String serverName) {
        this.serverName = serverName;
    }


    /**
     * @param file 路径+文件名
     * @return
     */
    @Override
    public boolean accept(File file) {
        //过滤文件夹中符合系统名称.实例名称.日期.文件编号.log格式的文件,实例名与当前实例名一致.
        if (file.getName().split("\\.").length == 5 && serverName.equals(file.getName().split("\\.")[1])) {
            return true;
        }
        return false;
    }
}

