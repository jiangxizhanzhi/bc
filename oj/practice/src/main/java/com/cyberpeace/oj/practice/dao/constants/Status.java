package com.cyberpeace.oj.practice.dao.constants;

import lombok.Getter;

public enum Status {
    DELETED(0),
    NORMAL(1);

    @Getter
    private final Integer value;

    Status(Integer value) {
        this.value = value;
    }
}
