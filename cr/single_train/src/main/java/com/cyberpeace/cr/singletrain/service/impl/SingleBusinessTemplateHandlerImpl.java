package com.cyberpeace.cr.singletrain.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SingleBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	TrainRecordService trainRecordService;

	@Autowired
	private SingleTemplateService singleTemplateService;

	@Autowired
	private SingleRoomService singleRoomService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Override
	public BusinessType setBusinessType() {
		return BusinessType.TRAINING_SINGLE;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		SingleTemplate singleTemplate = new SingleTemplate();
		BaseInfo baseInfo = basicTemplate.getBaseInfo();

		singleTemplate.setBusinessTemplateId(businessId);
		singleTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		singleTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		singleTemplate.setSubType(baseInfo.getCategory());
		singleTemplate.setDurationTime(baseInfo.getDurationTime());

		SingleTrainTemplateReq requestData = (SingleTrainTemplateReq) basicTemplate;
		singleTemplate.setMissionsScheme(JsonUtil.toJsonString(requestData.getMissionsScheme()));
		// todo save missions -> return mission_id ? ->
		// singleTemplate.setMissionSchemeId(mission_id)

		singleTemplateService.save(singleTemplate);

		return singleTemplate.getBusinessTemplateId();
	}

	@Override
	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		ResourceDumpContext<SingleTemplate> context = new ResourceDumpContext<>();
		// 获取 teamTemplate
		SingleTemplate singleTemplate = singleTemplateService
				.getByBusinessTemplateId(template.getResourceId());
		singleTemplate.setId(null);
		context.setBusiness(singleTemplate);
		// 获取任务
		String missionScheme = singleTemplate.getMissionsScheme();
		context.setMissions(missionTemplateService.batchGetByMissionSchemeJson(missionScheme));

		return context;
	}

	@Override
	public void realLoadBusinessResource(ResourceDumpContext resource) {
		SingleTemplate singleTemplate = ((JSONObject) resource.getBusiness())
				.toJavaObject(SingleTemplate.class);
		singleTemplate.setId(null);
		singleTemplate.setCreateTime(null);
		singleTemplate.setUpdateTime(null);
		if (null != singleTemplateService.getById(singleTemplate.getResourceId())) {
			singleTemplateService.updateById(singleTemplate);
		} else {
			singleTemplateService.save(singleTemplate);
		}
	}

	@Override
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		SingleTemplate singleTemplate = singleTemplateService
				.getByBusinessTemplateId(businessTemplate.getResourceId());
		// 删除任务, 但不删除图片
		List<String> missionTemplateIds = singleTemplateService
				.getMissionTemplateIdsByMissionsScheme(singleTemplate.getMissionsScheme());
		missionTemplateService.batchDelete(missionTemplateIds);
		// 删除singleTemplate
		singleTemplateService.removeById(singleTemplate.getResourceId());
	}

	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		SingleTemplate template = BeanUtils.copy(templateContext, SingleTemplate.class);
		singleTemplateService.save(template);
	}

	@Override
	public void checkBeforeDel(BusinessTemplate businessTemplate) {
		List<SingleRoom> roomList = singleRoomService
				.getByBusinessTemplateId(businessTemplate.getResourceId());

		if (!roomList.isEmpty()) {
			String errorMsg = String.format(
					"Failed to delete single training template in use, template's name is [%s].",
					businessTemplate.getName());
			log.error(errorMsg);
			throw new BusinessException(CrBusinessErrorCode.SINGLE_TRAIN_TEMPLATE_IN_USE);
		}
	}
}
