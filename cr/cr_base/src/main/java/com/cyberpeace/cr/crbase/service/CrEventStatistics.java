package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.constant.SelectI18nName;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.TrainStatsOverviewRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.context.BarChartUnit;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingDateCount;
import com.cyberpeace.cs.dao.entity.LocalScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyberpeace.cr.crbase.service.impl.CrBaseServiceImpl.register;

@Service(value = "CrEventStatistics")
public class CrEventStatistics {

	@Autowired
	CrConstantsService crConstantsService;

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

	public void getPeopleCount(String userId, List<BarChartUnit> trainingPeopleStatistics){
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public void getTrainingScore(String userId, List<BarChartUnit> trainingStatsScore) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainStatsOverviewRsp> getTrainingOverview(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public HashMap<String, Integer> getScoreByAttackType(String roomId, String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public Map<String, String> getMissionCategory() {
		List<CrConstantsConfig> constants = crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		return 	StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId, SelectI18nName::selectI18nName);
	}

	public List<TrainingDateCount> getTrainingCountByDate(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public List<TrainingDateCount> getAllTrainingCountByDate() {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
