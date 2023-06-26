package com.cyberpeace.situation.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateRefMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.context.config.DashboardConfigContext;
import com.google.common.collect.Lists;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardAttributeMapper dashboardAttributeMapper;

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@Autowired
	private DashboardTemplateRefMapper dashboardTemplateRefMapper;

	@Autowired
	private ThirdPartyService thirdPartyService;

	@Override
	public DashboardBasicAttributeRes getDashboardBasicAttribute(String dashboardId) {

		// 获取属性对象
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(dashboardId);

		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstances.getAttributeId());


		if (dashboardAttribute != null) {
			DashboardBasicAttributeRes dashboardBasicAttributeRes = BeanUtils
					.deepCopy(dashboardAttribute, DashboardBasicAttributeRes.class);
			dashboardBasicAttributeRes.setViewAngle(dashboardAttribute.getViewAngle());

			DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper.selectByOriginTemplateId(situationPublishInstances.getTemplateId());
			//编辑大屏可能会修改部分值，需要从situation_dashboard_template_ref表获取
			if (dashboardTemplateRef != null) {

				DashboardConfigContext dashboardConfigContext = JsonUtil
						.toJsonObject(dashboardTemplateRef.getConfig(), DashboardConfigContext.class);

				dashboardBasicAttributeRes.setMapSource(dashboardConfigContext.getGisConfig().getMapSource());
				dashboardBasicAttributeRes.setViewAngle(dashboardTemplateRef.getViewAngle());
				dashboardBasicAttributeRes.setRefresh(dashboardTemplateRef.getRefresh());
				dashboardBasicAttributeRes.setLevel(dashboardTemplateRef.getLevel());

			}

			List<String> events = Lists.newArrayList();
			Map<String, String> typicalEventMap = thirdPartyService.getTypicalEventsMap();
			situationPublishInstances.getEventList().forEach(event -> {
				String subEvent = event.get(1);
				events.add(typicalEventMap.get(subEvent));
			});

			dashboardBasicAttributeRes.setEventList(events);
			dashboardBasicAttributeRes.setSchemeId(situationPublishInstances.getSchemeId());
			dashboardBasicAttributeRes.setDashboardId(situationPublishInstances.getResourceId());
			dashboardBasicAttributeRes.setSubRangeId(situationPublishInstances.getSubRangeId());
			dashboardBasicAttributeRes.setTaskId(situationPublishInstances.getTaskId());
			dashboardBasicAttributeRes.setSubjectId(situationPublishInstances.getSubjectId());

			return dashboardBasicAttributeRes;

		}

		return null;
	}

	@Override
	public DashboardConfigContext getParameterConfigByDashboardId(String dashboardId) {
		return JsonUtil.toJsonObject(
				situationPublishInstanceMapper.getParameterConfigByDashboardId(dashboardId),
				DashboardConfigContext.class);
	}

	@Override
	public DashboardIdRes getAttributeByTaskId(String taskId, String subjectId) {

		SituationPublishInstance situationPublishInstances = situationPublishInstanceService
				.selectReleasedXdDashboardBySubjectId(taskId, subjectId);

		return getIdByInstance(situationPublishInstances);
	}

	@Override
	public DashboardIdRes getAttributeByDashboardId(String dashboardId) {
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(dashboardId);

		return getIdByInstance(situationPublishInstances);

	}

	@Override
	public DashboardAttribute getDashboardAttribute(String dashboardId) {

		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(dashboardId);

		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return dashboardAttributeMapper.getByResourceId(situationPublishInstances.getAttributeId());
	}

	public DashboardIdRes getIdByInstance(SituationPublishInstance situationPublishInstances) {

		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		DashboardIdRes dashboardIdRes;

		// 处理三方靶标
		if (StringUtils.isEmpty(situationPublishInstances.getAttributeId())) {
			dashboardIdRes = new DashboardIdRes(situationPublishInstances.getResourceId(),
					situationPublishInstances.getThirdUrl());
			return dashboardIdRes;
		}

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstances.getAttributeId());

		DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper
				.selectById(situationPublishInstances.getTemplateId());

		dashboardIdRes = BeanUtils.copy(dashboardAttribute, DashboardIdRes.class);
		dashboardIdRes.setDashboardId(situationPublishInstances.getResourceId());
		dashboardIdRes.setCustomCode(dashboardTemplateRef.getCustomCode());

		return dashboardIdRes;

	}

	@Override
	public Integer getDashboardLevel(String dashboardId) {
		Integer level = situationPublishInstanceMapper.getDashboardLevel(dashboardId);
		if (level == null) {
			throw new BusinessException(SituationErrorCode.FAILED_TO_OBTAIN_DASHBOARD_INFO);
		}
		return level;
	}
}
