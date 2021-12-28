package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LessonPracticeEventListener extends MessageListener {

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.LESSON_PRACTICE.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		return super.onSceneDelete(data, businessId, context);
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
