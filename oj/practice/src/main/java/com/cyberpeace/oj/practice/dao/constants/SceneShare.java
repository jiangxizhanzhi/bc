package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;

public enum SceneShare {

    /**
     * 个人
     */
    PRIVATE(1),

    /**
     * 共享
     */
    SHARED(2);

    @Getter
    private final Integer value;

    SceneShare(Integer value) {
        this.value = value;
    }
}
