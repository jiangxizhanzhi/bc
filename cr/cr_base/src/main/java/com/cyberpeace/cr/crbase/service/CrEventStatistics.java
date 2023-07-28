package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.common.enums.BusinessCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.cyberpeace.cr.crbase.service.impl.CrBaseServiceImpl.register;

@Slf4j
@Service(value = "CrEventStatistics")
public class CrEventStatistics {

	@PostConstruct
	public void register() {
		if (this.getCode() != null) {
			register.put(this.getCode(), this);
		}
	}

	public BusinessCode getCode() {
		return null;
	}

	public TrainingCount getTrainingCount(String userId) {
		log.error("Method not implemented! userId = [{}]", userId);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainingData> getRelatedTraining(String userId) {
		log.error("Method not implemented! userId = [{}]", userId);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
