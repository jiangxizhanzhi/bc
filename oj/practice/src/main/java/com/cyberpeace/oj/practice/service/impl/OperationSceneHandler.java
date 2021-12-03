package com.cyberpeace.oj.practice.service.impl;

import com.cyberpeace.integratation.model.scene.ReferenceMetaData;
import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneMetaData;
import com.cyberpeace.integratation.model.scene.SceneMpContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.impl.OjSceneHandler;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;

public class OperationSceneHandler extends OjSceneHandler {

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Override
	public Integer getBusinessType() {
		return LocalScene.BusinessCode.PRACTICE.getCode();
	}

	@Override
	public ReferenceMetaData getReferenceMetaData(OjLocalScene ojLocalScene) {
		// 调用父类方法设置一部分参数
		ReferenceMetaData referenceMetaData = super.getReferenceMetaData(ojLocalScene);

		// 设置场景名称
		PracticeOperation practiceOperation = practiceOperationMapper
				.selectById(ojLocalScene.getResourceId());
		referenceMetaData.setBusinessName(practiceOperation.getTitle());

		return referenceMetaData;
	}

	@Override
	public SceneMpContext getSceneMpContext(OjLocalScene ojLocalScene) {

		// 调用父类方法设置一部分参数
		SceneMpContext sceneMpContext = super.getSceneMpContext(ojLocalScene);

		// 透传数据设置
		sceneMpContext.getPass_through().put("local_scene_id", ojLocalScene.getLocalSceneId());

		// 设置场景可存活最大时间
		PracticeOperation practiceOperation = practiceOperationMapper
				.selectById(ojLocalScene.getBusinessId());
		sceneMpContext.getPass_through().put("destroy_delay",
				practiceOperation.getDestroyDelay().toString());

		return sceneMpContext;
	}
}
