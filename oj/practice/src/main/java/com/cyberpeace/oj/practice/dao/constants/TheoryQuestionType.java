package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;

public enum TheoryQuestionType {
    /**
     * 单选
     */
    SINGLE(0),

    /**
     * 多选
     */
    MULTIPLE(1),

    /**
     * 判断
     */
    JUDGMENT(2),

    /**
     * 填空
     */
    FILL_BLANK(3),

    /**
     * 简答
     */
    SHORT_ANSWER(4);

    @Getter
    private final Integer value;

    TheoryQuestionType(Integer value) {
        this.value = value;
    }
}
