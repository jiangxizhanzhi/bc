package com.cyberpeace.oj.common.util;

public class DataTransferUtils {

    /**
     * 判断整数对象是否为空 并进行赋值
     * @param num
     * @return
     */
    public static Integer nullToZero(Integer num) {
        if (null == num) {
            num = 0;
        }
        return num;
    }

}
