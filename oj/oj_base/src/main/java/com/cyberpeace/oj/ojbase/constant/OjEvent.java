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
/**
 * 这段代码定义了一个名为 `OjEvent` 的枚举类，用于表示与某个在线评测系统（OJ）相关的事件。以下是该代码的详细分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.ojbase.constant` 
 * - **类名**: `OjEvent` 
 * - **注释**: 提供了关于这个枚举类的描述信息，包括作者、版本日期等。 
 * ### 枚举常量 
 * - `SCENE_CREATE_SYNC`: 代表“共享场景中 场景启动推送”，包含代号 "OJ-1" 和描述信息。 
 * - `SCENE_DELETE_SYNC`: 代表“共享场景中 场景删除推送”，包含代号 "OJ-2" 和描述信息。 
 * - `SCENE_CREATE_FINISH`: 代表“场景启动完成 推送倒计时”，包含代号 "OJ-3" 和描述信息。 
 * - `TO_NEED_HELP_SYNC`: 代表“远程协助开始”，包含代号 "OJ-4" 和描述信息。 
 * - `END_HELP_SYNC`: 代表“远程协助结束”，包含代号 "OJ-5" 和描述信息。 
 * - `START_SCENE_ERROR`: 代表“场景启动错误”，包含代号 "OJ-6" 和描述信息。 
 * ### 字段 
 * - `code`: 代号，每个枚举实例都有一个唯一的代号，用于标识特定的事件。 
 * - `desc`: 描述信息，用于解释该事件的具体含义。 
 * ### 方法 
 * - `@Getter`: Lombok 注解，用于自动生成 `code` 和 `desc` 字段的 getter 方法。 
 * ### 构造方法 
 * - `OjEvent(String code, String desc)`: 枚举类的构造方法，用于初始化每个枚举实例的 `code` 和 `desc` 字段。 
 * ### 总结 
 * `OjEvent` 枚举类提供了一种方便的方式来表示和处理与在线评测系统相关的不同事件。每个事件都有一个唯一的代号和描述信息，使得事件的识别和处理变得更加直观和简单。通过使用 Lombok 的 `@Getter` 注解，可以简化代码并提高可读性。 
**/
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

