package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalGuideSceneContext implements Serializable {

	private String guideId;

	private String sceneId;
}
