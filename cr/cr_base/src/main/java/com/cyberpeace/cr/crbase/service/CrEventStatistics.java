package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.cyberpeace.cr.crbase.service.impl.CrBaseServiceImpl.register;

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

	public List<CrRoleNode> getCrRoleNodeList(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
