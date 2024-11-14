package com.cyberpeace.cr.singletrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.enums.BusinessType;
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

/**
 * 单业务模板处理器实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class SingleBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	/**
	 * 事务模板
	 */
	@Autowired
	TransactionTemplate transactionTemplate;

	/**
	 * 训练记录Service
	 */
	@Autowired
	TrainRecordService trainRecordService;

	/**
	 * 单模板Service
	 */
	@Autowired
	private SingleTemplateService singleTemplateService;

	/**
	 * 单房间Service
	 */
	@Autowired
	private SingleRoomService singleRoomService;

	/**
	 * 任务模板Service
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 设置业务类型
	 *
	 * @return {@code BusinessType }
	 */
	@Override
	public BusinessType setBusinessType() {
		return BusinessType.TRAINING_SINGLE;
	}

	/**
	 * 保存真实业务
	 *
	 * @param basicTemplate
	 * @param businessId    业务id
	 * @return {@code String }
	 */
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

		singleTemplateService.save(singleTemplate);

		return singleTemplate.getBusinessTemplateId();
	}

	/**
	 * 真实导出业务
	 *
	 * @param template 模板
	 * @return {@code ResourceDumpContext }
	 */
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

	/**
	 * 真实加载业务资源
	 *
	 * @param resource 资源
	 */
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

	/**
	 * 删除模板相关
	 *
	 * @param businessTemplate 业务模板
	 */
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

	/**
	 * 加载训练模板
	 *
	 * @param templateContext 模板上下文
	 */
	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		SingleTemplate template = BeanUtils.copy(templateContext, SingleTemplate.class);
		singleTemplateService.save(template);
	}

	/**
	 * 在 del 之前检查
	 *
	 * @param businessTemplate 业务模板
	 */
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
