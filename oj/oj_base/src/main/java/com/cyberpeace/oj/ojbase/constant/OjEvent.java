package com.cyberpeace.oj.ojbase.constant;

import lombok.Getter;

public enum OjEvent {

    SCENE_CREATE_SYNC("OJ-1", "共享场景中 场景启动推送"),
    SCENE_DELETE_SYNC("OJ-2", "共享场景中 场景删除推送"),
    SCENE_CREATE_FINISH("OJ-3", "场景启动完成 推送倒计时");

    @Getter
    private String code;

    @Getter
    private String desc;

    OjEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
