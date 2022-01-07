package com.cyberpeace.situation.constant.ws;

import lombok.Getter;

public enum ActionType {
    /**
     * 消息类型
     */

    NETWORK_SCANNING(0, "网络扫描"),
    VIRUS_ATTACKS(1, "病毒攻击"),
    TROJAN(2, "木马"),
    DDOS(3, "DDOS"),
    HOST_ATTACK(4, "主机攻占"),
    TRY_ATTACK(5, "尝试攻击"),
    SUCCESS_ATTACK(6, "攻击成功"),
    REPAIR(7, "修复");


    @Getter
    private Integer code;

    @Getter
    private String desc;

    ActionType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Integer getRandomValue(ActionType[] values) {

        return values[(int) (Math.random() * values.length)].code;

    }
}
