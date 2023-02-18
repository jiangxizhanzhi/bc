package com.cyberpeace.cr.scene_manage.controller.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.scene_manage.controller.req.SceneTrainTemplateReq;
import com.cyberpeace.cr.scene_manage.controller.req.SceneTrainTemplateReqRel;
import com.cyberpeace.cr.scene_manage.controller.req.SceneTrainTemplateUpdateReqRel;
import com.cyberpeace.cr.scene_manage.dao.entity.SceneTemplate;
import com.cyberpeace.cr.scene_manage.service.SceneTemplateService;
import com.cyberpeace.cr.scene_manage.service.bo.SceneTemplateBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "场景管理模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/admin/scene_manage/template")
public class SceneTemplateController {

	@Autowired
	private SceneTemplateService sceneTemplateService;

	@Autowired
	private CrCommonService commonService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteLogService remoteLogService;

	@GetMapping
	@ApiOperation("场景管理模板获取")
	@RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN)
	public BaseRsp<SceneTemplateBo> get(@RequestParam("resource_id") String businessTemplateId) {
		businessTemplateService.checkPermission(businessTemplateId,
				UserInfoContext.getUserContext().getUserId());

		return new BaseRsp<>(sceneTemplateService.get(businessTemplateId));
	}

	@PostMapping
	@ApiOperation("场景管理模板创建")
	@RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN)
	public BaseRsp create(@RequestBody SceneTrainTemplateReqRel requestDataRel) {
		// 入参`event_data` -> `base_info`
		SceneTrainTemplateReq requestData = BeanUtils.copy(requestDataRel,
				SceneTrainTemplateReq.class);
		BaseInfo baseInfo = BeanUtils.copy(requestDataRel.getEventData(), BaseInfo.class);
		requestData.setBaseInfo(baseInfo);

		// set type
		requestData.getBaseInfo().setType(BusinessTemplate.BusinessType.SCENE_MANAGE.getCode());
		// create scene template
		SceneTemplate sceneTemplate = reqToSceneTemplate(requestData, new SceneTemplate());
		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				String businessTemplateId = businessTemplateService.addTemplate(baseInfo);
				sceneTemplate.setBusinessTemplateId(businessTemplateId);
				sceneTemplateService.save(sceneTemplate);
				// update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(businessTemplateId, "",
						sceneTemplate.getMissionsScheme());
			}
		});

		String sceneTrainTemplateName = requestDataRel.getEventData().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.SCENE_MANAGE,
				OperationType.CREATE, sceneTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.SCENE_MANAGE_TEMPLATE.getMsg(), sceneTrainTemplateName));

		return new BaseRsp<>();
	}

	@PatchMapping
	@ApiOperation("场景管理模板更新")
	@RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN)
	public BaseRsp
			update(@RequestBody SceneTrainTemplateUpdateReqRel requestDataRel) {
		// 入参`event_data` -> `base_info`
		SceneTrainTemplateReq requestData = BeanUtils.copy(requestDataRel,
				SceneTrainTemplateReq.class);
		BaseInfo baseInfo = BeanUtils.copy(requestDataRel.getEventData(), BaseInfo.class);
		requestData.setBaseInfo(baseInfo);
		// get businessTemplate & sceneTemplate
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(requestData.getResourceId());
		SceneTemplate sceneTemplate = sceneTemplateService
				.getByBusinessTemplateId(requestData.getResourceId());
		// update scene template
		String oldMissionScheme = sceneTemplate.getMissionsScheme();
		reqToSceneTemplate(requestData, sceneTemplate);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateTemplate(businessTemplate, baseInfo);
				sceneTemplateService.updateById(sceneTemplate);
				// delete&update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(
						businessTemplate.getResourceId(), oldMissionScheme,
						sceneTemplate.getMissionsScheme());
			}
		});

		String sceneTrainTemplateName = requestDataRel.getEventData().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.SCENE_MANAGE,
				OperationType.UPDATE, sceneTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.SCENE_MANAGE_TEMPLATE.getMsg(), sceneTrainTemplateName));

		return new BaseRsp<>();
	}

	private SceneTemplate reqToSceneTemplate(SceneTrainTemplateReq requestData,
			SceneTemplate sceneTemplate) {
		BaseInfo baseInfo = requestData.getBaseInfo();

		// tags
		sceneTemplate.setPreSkills(CrCommonUtil.handleTrainingTag(baseInfo.getPreSkill()));
		sceneTemplate.setTrainSkills(CrCommonUtil.handleTrainingTag(baseInfo.getTrainSkill()));
		sceneTemplate.setSubType(baseInfo.getCategory());
		sceneTemplate.setDurationTime(baseInfo.getDurationTime());
		// validate missions scheme
		commonService.validateMissionsScheme(requestData.getMissionsScheme());
		sceneTemplate
				.setMissionsScheme(CrCommonUtil.roleConfig2DbJson(requestData.getMissionsScheme()));

		return sceneTemplate;
	}
}
