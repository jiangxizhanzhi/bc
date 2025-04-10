package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum OperationType implements GenericEnum {

    /**
     * 主要
     *
     * @see Difficulty
     */
    ATTACK(1, "攻击"),
    /**
     * 中间
     *
     * @see Difficulty
     */
    DEFENSE(2, "防御"),
    /**
     * 高
     *
     * @see Difficulty
     */
    OTHER(3, "其他");

    /**
     * 代号
     *
     * @see Integer
     */
    @Getter
    private Integer code;

    /**
     * 描述
     *
     * @see String
     */
    @Getter
    private String desc;

    OperationType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
