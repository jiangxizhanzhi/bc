package com.cyberpeace.situation.constant.ws;

import lombok.Getter;

public enum MessageType {
    /**
     * 消息类型
     */

    SingleNodeEffect(1, "single_node_effect"),
    MultiNodeEffect(2, "multi_node_effect");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    MessageType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
