package com.cyberpeace.cr.teamtrain.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;

@Service
public class TeamBusinessTemplateHandler extends BusinessTemplateHandler {

	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.TRAINING_TEAM;
	}

	@Override
	public void saveRealBusiness(Object obj) {
		// 1、保存角色数据

		// 2、保存任务数据
	}
}
