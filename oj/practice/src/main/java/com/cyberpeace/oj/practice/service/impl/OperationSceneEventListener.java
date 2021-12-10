package com.cyberpeace.oj.practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperationSceneEventListener extends MessageListener {

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.PRACTICE.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		ojLocalSceneService.deleteScene(businessId);

		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {

		return super.onSceneStartProcess(data, businessId, context);
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		return super.onSceneElement(data, businessId, context);
	}

}
