package com.cyberpeace.situation.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardAttributeMapper dashboardAttributeMapper;

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

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

			dashboardBasicAttributeRes.setDashboardId(situationPublishInstances.getResourceId());
			dashboardBasicAttributeRes.setSubRangeId(situationPublishInstances.getSubRangeId());
			dashboardBasicAttributeRes.setTaskId(situationPublishInstances.getTaskId());
			dashboardBasicAttributeRes.setSubjectId(situationPublishInstances.getSubjectId());

			return dashboardBasicAttributeRes;

		}

		DashboardBasicAttributeRes data = new DashboardBasicAttributeRes();

		if ("lgzx".equals(dashboardId)) {
			data = new DashboardBasicAttributeRes(dashboardId, 1, "LG中心综合态势", null, null, null,
					new Date(),
					new ViewAngle("117.2591507", "31.7956434", 10000, 1.0f, 1.0f, 1.0f));
		} else if ("zhxfbc".equals(dashboardId)) {
			data = new DashboardBasicAttributeRes(dashboardId, 2, "XXX分靶场综合态势", null, "zhxfbc",
					null, new Date(), new ViewAngle());
		} else {
			data = new DashboardBasicAttributeRes(dashboardId, 3, "ZH性分靶场任务态势", "XXXX红蓝对抗任务", null,
					dashboardId, new Date(),
					new ViewAngle("117.2591507", "31.7956434", 10000, 1.0f, 1.0f, 1.0f));
		}

		return data;

	}

	@Override
	public DashboardIdRes getAttributeByTaskId(String taskId, String subjectId) {

		SituationPublishInstance situationPublishInstances = situationPublishInstanceService
				.selectReleasedBySubjectId(taskId, subjectId);

		return getIdByInstance(situationPublishInstances);
	}

	@Override
	public DashboardIdRes getAttributeByDashboardId(String dashboardId) {
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(dashboardId);

		return getIdByInstance(situationPublishInstances);

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

		dashboardIdRes = BeanUtils.copy(dashboardAttribute, DashboardIdRes.class);
		dashboardIdRes.setDashboardId(situationPublishInstances.getResourceId());

		return dashboardIdRes;

	}

}
