package com.cyberpeace.situation.constant;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum LineEffect {
    /**
     * 特效
     */
    STREAM("stream", "流动"),
    SHINE("shine", "发光");

    @Getter
    private String code;

    @Getter
    private String desc;

    LineEffect(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static List<String> getRandomEffect(LineEffect[] values) {

        List<String> effect = Lists.newArrayList();

        Integer n = (int) (Math.random() * (values.length + 1));

        for (int i = 1; i <= n; i++) {
            effect.add(values[i - 1].code);
        }

        return effect;

    }

}
