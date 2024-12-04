package com.cyberpeace.oj.ojbase.constant;

import lombok.Getter;

/**
 * OJ 活动
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @since 1.0.0
 */
public enum OjEvent {

    /**
     * 实训消息推送状态
     *
     * @see OjEvent
     */
    SCENE_CREATE_SYNC("OJ-1", "共享场景中 场景启动推送"),
    /**
     * 场景删除同步
     *
     * @see OjEvent
     */
    SCENE_DELETE_SYNC("OJ-2", "共享场景中 场景删除推送"),
    /**
     * 场景创造完成
     *
     * @see OjEvent
     */
    SCENE_CREATE_FINISH("OJ-3", "场景启动完成 推送倒计时"),
    /**
     * 需要帮助同步
     *
     * @see OjEvent
     */
    TO_NEED_HELP_SYNC("OJ-4", "远程协助开始"),
    /**
     * 结束帮助同步
     *
     * @see OjEvent
     */
    END_HELP_SYNC("OJ-5", "远程协助结束"),
    /**
     * 开始场景错误
     *
     * @see OjEvent
     */
    START_SCENE_ERROR("OJ-6", "场景启动错误");

    /**
     * 代号
     *
     * @see String
     */
    @Getter
    private String code;

    /**
     * 描述
     *
     * @see String
     */
    @Getter
    private String desc;

    OjEvent(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
