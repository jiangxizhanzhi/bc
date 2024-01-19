package com.cyberpeace.scene.service.bo.scene;

import com.cyberpeace.scene.constant.enums.StageEnum;

import lombok.Data;

@Data
public class SceneProcessBo {

	private Integer networkState = 0;

	private Integer nodeState = 0;

	private StageEnum stageEnum;

	private Integer stagePercentage = 0;
}
