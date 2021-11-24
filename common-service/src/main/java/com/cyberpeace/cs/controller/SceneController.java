package com.cyberpeace.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.service.LocalSceneService;

public class SceneController {

	@Autowired
	private LocalSceneService localSceneService;

	public BaseRsp startScene(String localSceneId) {
		localSceneService.startScene(localSceneId);

		return new BaseRsp();
	}
}
