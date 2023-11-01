package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "CrEventStatistics")
public class CrEventStatistics {

	protected static final Map<BusinessCode, CrEventStatistics> REGISTER = Maps.newHashMap();
	
	public static Map<BusinessCode, CrEventStatistics> getRegister() {
		return REGISTER;
	} 

	@PostConstruct
	public void register() {
		if (this.getCode() != null) {
			REGISTER.put(this.getCode(), this);
		}
	}

	public BusinessCode getCode() {
		return null;
	}

	public TrainingCount getTrainingCount(String userId) {
		log.error("Method getTrainingCount not implemented! userId = [{}]", userId);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainingData> getRelatedTraining(String userId) {
		log.error("Method getRelatedTraining not implemented! userId = [{}]", userId);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
