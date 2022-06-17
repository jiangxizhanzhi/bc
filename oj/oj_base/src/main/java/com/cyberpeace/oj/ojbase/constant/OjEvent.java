package com.cyberpeace.oj.ojbase.constant;

import lombok.Getter;

public enum OjEvent {

	/** 实训消息推送状态*/
    SCENE_CREATE_SYNC("OJ-1", "共享场景中 场景启动推送"),
    SCENE_DELETE_SYNC("OJ-2", "共享场景中 场景删除推送"),
    SCENE_CREATE_FINISH("OJ-3", "场景启动完成 推送倒计时"),
    TO_NEED_HELP_SYNC("OJ-4", "远程协助开始"),
    END_HELP_SYNC("OJ-5", "远程协助结束");

    @Getter
    private String code;

    @Getter
    private String desc;

    OjEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
