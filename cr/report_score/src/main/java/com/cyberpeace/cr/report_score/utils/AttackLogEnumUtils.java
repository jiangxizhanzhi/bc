package com.cyberpeace.cr.report_score.utils;

import com.cyberpeace.situation.service.AttackLogEnum;

import java.util.Arrays;

public class AttackLogEnumUtils {
    /**
     * 根据desc获取枚举
     */
    public static <T extends AttackLogEnum> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
                .findFirst().orElse(null);
    }

}
