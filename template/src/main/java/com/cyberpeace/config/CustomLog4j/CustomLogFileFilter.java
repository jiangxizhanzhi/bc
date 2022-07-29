package com.cyberpeace.config.CustomLog4j;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件过滤器
 * 删除文件时专用
 */
class CustomLogFileFilter implements FileFilter {
    private String delDate;


    public CustomLogFileFilter(String delDate) {
        this.delDate = delDate;
    }


    /**
     * @param file 路径+文件名
     * @return
     */
    @Override
    public boolean accept(File file) {
        if (delDate == null || file.isDirectory()) {
            return false;
        } else {
            String[] strs = file.getName().split("\\.");
            if (strs != null && strs.length >= 3) {
                if (strs[2].compareTo(delDate) <= 0) {//删除备份天数之前的文件
                    return true;
                }
            }
            return false;
        }
    }
}

