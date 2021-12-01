package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.SceneHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 后台测试场景
 */
@Service
@Slf4j
public class TestSceneHandler extends SceneHandler {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Override
	public Integer getBusinessType() {
		return LocalScene.BusinessCode.TEST_SCENE.getCode();
	}

	@Override
	public void clearLocalScene(LocalScene localScene) {
		BusinessTemplate businessTemplate = businessTemplateService
				.getByLocalSceneId(localScene.getResourceId());
		businessTemplate.setLocalSceneId("");
		businessTemplateService.updateById(businessTemplate);
	}

	public void checkBeforeTestingScene(String templateId) {
		if (!UserInfoContext.getUserContext().isAdmin()) {
			log.error("current user is not permitted to test template [{}]", templateId);
			throw new RuntimeException();
		}
	}

}
