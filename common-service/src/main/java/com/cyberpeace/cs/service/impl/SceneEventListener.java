package com.cyberpeace.cs.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.scene.MqContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SceneEventListener extends MessageListener {

	@Override
	public String declareMessageType() {
		return "scene";
	}

	/**
	 * 子类定义具体的消息处理方法
	 * @return
	 */
	@Override
	public boolean handlerMessage(MqContext context, String data) {
		log.info("sceneEvent context=[{}], data=[{}]", context, data);
		return true;
	}
}
