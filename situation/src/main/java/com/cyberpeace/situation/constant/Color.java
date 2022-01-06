package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum Color {
    /**
     * 颜色
     */

    WHITE("#FFFFFF", "白色"),
    BLACK("#000000", "黑色"),
    RED("#FF0000", "红色"),
    BLUE("#0000FF", "蓝色");

    @Getter
    private String code;

    @Getter
    private String desc;

    Color(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getRandomValue(Color[] values) {

        return values[(int) (Math.random() * values.length)].code;

    }

}
