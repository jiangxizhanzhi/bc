package com.cyberpeace.cr.singletrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.suit.BaseController;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.model.crevent.CrEventAddInput;
import com.cyberpeace.cr.cebase.model.crevent.CrEventModifyInput;
import com.cyberpeace.cr.cebase.model.crevent.CrEventOutput;
import com.cyberpeace.cr.cebase.service.CrEventService;
import com.cyberpeace.cr.crbase.model.MissionScheme;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.singletrain.model.template.Template;
import com.cyberpeace.cr.singletrain.model.template.TemplateAddInput;
import com.cyberpeace.cr.singletrain.model.template.TemplateModifyInput;
import com.cyberpeace.cr.singletrain.model.template.TemplateOutput;
import com.cyberpeace.cr.singletrain.service.TemplateService;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.service.RemoteSceneConfigService;

import io.swagger.annotations.Api;

@Api(tags = "单兵训练模板")
@RestController
@RequestMapping("/cr/single_train/template")
public class TemplateController extends BaseController<Template, TemplateAddInput, TemplateModifyInput, TemplateOutput> {

	@Autowired
	TemplateService templateService;

	@Autowired
	CrEventService crEventService;

	@Autowired
	RemoteSceneConfigService sceneConfigService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@Override
	protected IService<Template> getService() {
		return templateService;
	}

	@Override
	protected Template getOr404(String resourceId) {
		Template template = templateService.getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException("单兵训练模板" + resourceId + "不存在");
		}
		return template;
	}

	@Override
	protected Template getEntityFromAddInput(TemplateAddInput input) {
		Template template = new Template();

		CrEventAddInput crEventAddInput = input.getCrEvent();
		CrEvent crEvent = crEventService.create(crEventAddInput, CrEvent.CeType.TRAINING_SINGLE);
		template.setCrEventId(crEvent.getResourceId());

		SceneConfig sceneConfig = input.getSceneConfig();
		String sceneConfigId = sceneConfigService.create(sceneConfig);
		template.setSceneConfigId(sceneConfigId);

		MissionScheme missionScheme = input.getMissionScheme();
		String missionSchemeId = missionSchemeService.create(missionScheme);
		template.setMissionSchemeId(missionSchemeId);

		return template;
	}

	@Override
	protected Template getEntityFromModifyInput(TemplateModifyInput input, Template originEntity) {
		Template template = new Template();

		CrEventModifyInput crEventModifyInput = input.getCrEvent();
		crEventService.update(originEntity.getCrEventId(), crEventModifyInput);
		// 无需更新template的crEvent字段

		SceneConfig sceneConfig = input.getSceneConfig();
		if (sceneConfig != null) {
			String sceneConfigId = sceneConfigService.create(sceneConfig);
			template.setSceneConfigId(sceneConfigId);
		}

		MissionScheme missionScheme = input.getMissionScheme();
		if (missionScheme != null) {
			String missionSchemeId = missionSchemeService.create(missionScheme);
			template.setMissionSchemeId(missionSchemeId);
		}

		return template;
	}

	@Override
	protected TemplateOutput getOutputFromEntity(Template template) {
		TemplateOutput templateOutput = new TemplateOutput();

		CrEvent crEvent = crEventService.getOr404(template.getCrEventId());
		CrEventOutput crEventOutput = new CrEventOutput(crEvent);
		templateOutput.setCrEvent(crEventOutput);

		String sceneConfigId = template.getSceneConfigId();
		SceneConfig sceneConfig = sceneConfigService.getById(sceneConfigId);
		templateOutput.setSceneConfig(sceneConfig);

		String missionSchemeId = template.getMissionSchemeId();
		MissionScheme missionScheme = missionSchemeService.getById(missionSchemeId);
		templateOutput.setMissionScheme(missionScheme);

		return templateOutput;
	}
}
