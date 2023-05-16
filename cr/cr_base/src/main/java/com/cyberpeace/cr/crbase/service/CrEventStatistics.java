package com.cyberpeace.cr.crbase.service;

import static com.cyberpeace.cr.crbase.service.impl.CrBaseServiceImpl.register;

import java.util.List;

import javax.annotation.PostConstruct;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingDateCount;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cs.dao.entity.LocalScene;

@Service(value = "CrEventStatistics")
public class CrEventStatistics {

	@PostConstruct
	public void register() {
		if (this.getCode() != null) {
			register.put(this.getCode(), this);
		}
	}

	public LocalScene.BusinessCode getCode() {
		return null;
	}

	public TrainingCount getTrainingCount(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainingData> getRelatedTraining(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainingDateCount> getTrainingCountByDate(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainingDateCount> getAllTrainingCountByDate() {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
