package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 训练i18n
 *
 * @author SN
 * @date 2024/11/07
 */
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
