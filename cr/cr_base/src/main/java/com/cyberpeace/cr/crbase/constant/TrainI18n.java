package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * 火车i18n
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum TrainI18n implements BaseI18n {

	STAGE("阶段", "Stage"), TASK("任务", "Task"), WRITEUP("题解", "Write Up"), TASK_STAT("任务说明",
			"Task Statement"), MY_RECORD("我的记录", "My Record"), SCENE_DESC("场景描述",
					"Scene Description"), TIME("时间", "Time"), PEOPLE("人员", "People");

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	TrainI18n(String chinese, String english) {
		this.chinese = chinese;
		this.english = english;
	}

}
