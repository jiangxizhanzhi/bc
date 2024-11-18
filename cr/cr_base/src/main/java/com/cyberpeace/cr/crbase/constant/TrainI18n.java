package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 训练i18n
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see BaseI18n
 * @since 1.0.0
 */
public enum TrainI18n implements BaseI18n {

    /**
     * 阶段
     *
     * @see TrainI18n
     */
    STAGE("阶段", "Stage"),
    /**
     * 任务
     *
     * @see TrainI18n
     */
    TASK("任务", "Task"),
    /**
     * 撰写
     *
     * @see TrainI18n
     */
    WRITEUP("题解", "Write Up"),
    /**
     * 任务统计
     *
     * @see TrainI18n
     */
    TASK_STAT("任务说明", "Task Statement"),
    /**
     * 我记录
     *
     * @see TrainI18n
     */
    MY_RECORD("我的记录", "My Record"),
    /**
     * 场景描述
     *
     * @see TrainI18n
     */
    SCENE_DESC("场景描述", "Scene Description"),
    /**
     * 时间
     *
     * @see TrainI18n
     */
    TIME("时间", "Time"),
    /**
     * 人
     *
     * @see TrainI18n
     */
    PEOPLE("人员", "People");

    /**
     * 中文
     *
     * @see String
     */
    @Getter
    private final String chinese;

    /**
     * 英语
     *
     * @see String
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
