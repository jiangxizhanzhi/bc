package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 训练i18n
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个枚举类 `TrainI18n`，用于存储国际化（i18n）相关的键值对。以下是代码的主要分析： 
 * 1. **功能概述**： 
 * - 该枚举类实现了 `BaseI18n` 接口，主要用于管理训练模块的多语言支持。 
 * - 它包含了多个常量字段，每个字段对应一个中文和英文的翻译。 
 * 2. **结构与实现**： 
 * - 使用了 Lombok 的 `@Getter` 注解，自动生成 `chinese` 和 `english` 属性的 getter 方法。 
 * - 枚举类的构造函数接收两个参数：中文 (`chinese`) 和英文 (`english`)，并将其赋值给对应的字段。 
 * 3. **字段说明**： 
 * - 每个枚举值都代表一个特定的概念或短语，例如 `STAGE` 表示“阶段”，`TASK` 表示“任务”等。 
 * - 每个字段都有对应的中文和英文翻译，便于在不同语言环境中使用。 
 * 4. **用途**： 
 * - 通过这种方式，可以方便地在应用程序中切换语言环境，而无需硬编码字符串。 
 * - 适合用于需要多语言支持的国际化项目。 
 * 总结来说，这段代码是一个典型的国际化枚举类实现，提供了清晰且易于维护的语言资源管理方式。 
**/
public enum TrainI18n implements BaseI18n {

    /**
     * 阶段
     */
    STAGE("阶段", "Stage"),
    /**
     * 任务
     */
    TASK("任务", "Task"),
    /**
     * 撰写
     */
    WRITEUP("题解", "Write Up"),
    /**
     * 任务统计
     */
    TASK_STAT("任务说明", "Task Statement"),
    /**
     * 我记录
     */
    MY_RECORD("我的记录", "My Record"),
    /**
     * 场景描述
     */
    SCENE_DESC("场景描述", "Scene Description"),
    /**
     * 时间
     */
    TIME("时间", "Time"),
    /**
     * 人
     */
    PEOPLE("人员", "People");

    /**
     * 中文
     */
    @Getter
    private final String chinese;

    /**
     * 英语
     */
    @Getter
    private final String english;

    /**
     * 训练i18n
     *
     * @param chinese 中文
     * @param english 英语
     */
    TrainI18n(String chinese, String english) {
        this.chinese = chinese;
        this.english = english;
    }

}

