package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;


public enum TestPaperType {
    /**
     * 试卷库
     */
    LIB(1),

    /**
     * 考试
     */
    EXAM(2),

    /**
     * 课后练习
     */
    LESSON(3);

    @Getter
    private final Integer value;

    TestPaperType(Integer value) {
        this.value = value;
    }
}
