package com.cyberpeace.cr.singletrain.controller.admin;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import lombok.extern.slf4j.Slf4j;
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
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReqRel;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateUpdateReqRel;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Slf4j
@Api(tags = "单兵训练模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/admin/single_train/template")
public class SingleTemplateController {

	@Autowired
	private SingleTemplateService singleTemplateService;

	@Autowired
	private CrCommonService commonService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteLogService remoteLogService;

	@GetMapping
	@ApiOperation("单兵训练模板获取")
	@RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN)
	public BaseRsp<SingleTemplateBo> get(@RequestParam("resource_id") String businessTemplateId) {
		businessTemplateService.checkPermission(businessTemplateId,
				UserInfoContext.getUserContext().getUserId());

		return new BaseRsp<>(singleTemplateService.get(businessTemplateId));
	}

	@PostMapping
	@ApiOperation("单兵训练模板创建")
	@RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> create(@RequestBody SingleTrainTemplateReqRel requestDataRel) {
		// 入参`event_data` -> `base_info`
		SingleTrainTemplateReq requestData = BeanUtils.copy(requestDataRel,
				SingleTrainTemplateReq.class);
		BaseInfo baseInfo = BeanUtils.copy(requestDataRel.getEventData(), BaseInfo.class);
		requestData.setBaseInfo(baseInfo);

		// set type
		requestData.getBaseInfo().setType(BusinessType.TRAINING_SINGLE.getCode());
		// create single template
		SingleTemplate singleTemplate = reqToSingleTemplate(requestData, new SingleTemplate());
		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				String businessTemplateId = businessTemplateService.addTemplate(baseInfo);
				singleTemplate.setBusinessTemplateId(businessTemplateId);
				singleTemplateService.save(singleTemplate);
				// update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(businessTemplateId, "",
						singleTemplate.getMissionsScheme());
			}
		});

		String singleTrainTemplateName = requestDataRel.getEventData().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.SINGLE_TRAIN,
				OperationType.CREATE, singleTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.SINGLE_TRAIN_TEMPLATE.getMsg(), singleTrainTemplateName));

		return new BaseRsp<>();
	}

	@PatchMapping
	@ApiOperation("单兵训练模板更新")
	@RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean>
			update(@RequestBody SingleTrainTemplateUpdateReqRel requestDataRel) {
		// 入参`event_data` -> `base_info`
		SingleTrainTemplateReq requestData = BeanUtils.copy(requestDataRel,
				SingleTrainTemplateReq.class);
		BaseInfo baseInfo = BeanUtils.copy(requestDataRel.getEventData(), BaseInfo.class);
		requestData.setBaseInfo(baseInfo);
		// get businessTemplate & singleTemplate
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(requestData.getResourceId());
		SingleTemplate singleTemplate = singleTemplateService
				.getByBusinessTemplateId(requestData.getResourceId());
		if(singleTemplate == null){
			log.error("template_not_exist: ResourceId = [{}]", requestData.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);

		}
		// update single template
		String oldMissionScheme = singleTemplate.getMissionsScheme();
		reqToSingleTemplate(requestData, singleTemplate);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateTemplate(businessTemplate, baseInfo);
				singleTemplateService.updateById(singleTemplate);
				// delete&update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(
						businessTemplate.getResourceId(), oldMissionScheme,
						singleTemplate.getMissionsScheme());
			}
		});

		String singleTrainTemplateName = requestDataRel.getEventData().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.SINGLE_TRAIN,
				OperationType.UPDATE, singleTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.SINGLE_TRAIN_TEMPLATE.getMsg(), singleTrainTemplateName));

		return new BaseRsp<>();
	}

	private SingleTemplate reqToSingleTemplate(SingleTrainTemplateReq requestData,
			SingleTemplate singleTemplate) {
		BaseInfo baseInfo = requestData.getBaseInfo();

		// tags
		singleTemplate.setPreSkills(CrCommonUtil.handleTrainingTag(baseInfo.getPreSkill()));
		singleTemplate.setTrainSkills(CrCommonUtil.handleTrainingTag(baseInfo.getTrainSkill()));
		singleTemplate.setSubType(baseInfo.getCategory());
		singleTemplate.setDurationTime(baseInfo.getDurationTime());
		// validate missions scheme
		commonService.validateMissionsScheme(requestData.getMissionsScheme());
		singleTemplate
				.setMissionsScheme(CrCommonUtil.roleConfig2DbJson(requestData.getMissionsScheme()));

		return singleTemplate;
	}
}
