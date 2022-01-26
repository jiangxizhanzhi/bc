package com.cyberpeace.cr.singletrain.controller.admin;

import com.cyberpeace.common.model.user.UserInfoContext;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

	@GetMapping
	@ApiOperation("单兵训练模板获取")
	public BaseRsp<SingleTemplateBo> get(@RequestParam("resource_id") String businessTemplateId) {
		businessTemplateService.checkPermission(businessTemplateId,
				UserInfoContext.getUserContext().getUserId());

		return new BaseRsp<>(singleTemplateService.get(businessTemplateId));
	}

	@PostMapping
	@ApiOperation("单兵训练模板创建")
	public BaseRsp<SingleTemplateBo> create(@RequestBody SingleTrainTemplateReqRel requestDataRel) {
		// 入参`event_data` -> `base_info`
		SingleTrainTemplateReq requestData = BeanUtils.copy(requestDataRel,
				SingleTrainTemplateReq.class);
		BaseInfo baseInfo = BeanUtils.copy(requestDataRel.getEventData(), BaseInfo.class);
		requestData.setBaseInfo(baseInfo);

		// set type
		requestData.getBaseInfo().setType(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
		// create single template
		SingleTemplate singleTemplate = reqToSingleTemplate(requestData, new SingleTemplate());
		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				String businessTemplateId = businessTemplateService.addTemplate(baseInfo);
				singleTemplate.setBusinessTemplateId(businessTemplateId);
				singleTemplateService.save(singleTemplate);
			}
		});

		return new BaseRsp<>();
	}

	@PatchMapping
	@ApiOperation("单兵训练模板更新")
	public BaseRsp<SingleTemplateBo>
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
		// update single template
		reqToSingleTemplate(requestData, singleTemplate);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateTemplate(businessTemplate, baseInfo);
				singleTemplateService.updateById(singleTemplate);
			}
		});
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
