package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class CrTaskSituationHandler {

	private static Map<BusinessTemplate.BusinessType, CrTaskSituationHandler> register = Maps.newHashMap();

	@PostConstruct
	public void init() {
		register.put(getBusinessType(), this);
	}

	public BusinessTemplate.BusinessType getBusinessType() {
		return null;
	}

	public void chooseTypeAndPushMissionToSituation(String missionId, Integer score) {
		CrTaskSituationHandler crTaskSituationHandler = register.get(BusinessTemplate.BusinessType.RED_VS_BLUE);
		crTaskSituationHandler.pushMissionToSituation(missionId, score);
	}

	public void pushMissionToSituation(String missionId, Integer score) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
