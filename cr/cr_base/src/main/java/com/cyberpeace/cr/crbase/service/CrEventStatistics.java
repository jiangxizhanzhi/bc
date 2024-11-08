package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * 靶场事件统计学
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service(value = "CrEventStatistics")
public class CrEventStatistics {

	/**
	 * 注册
	 */
	@PostConstruct
	public void register() {
		if (this.getCode() != null) {
		}
	}

	/**
	 * 获取代号
	 *
	 * @return {@code LocalScene.BusinessCode }
	 */
	public LocalScene.BusinessCode getCode() {
		return null;
	}

	/**
	 * 获取训练计数
	 *
	 * @param userId 用户id
	 * @return {@code TrainingCount }
	 */
	public TrainingCount getTrainingCount(String userId) {
		log.error("Method not implemented! userId = [{}]", userId);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 获取相关训练
	 *
	 * @param userId 用户id
	 * @return {@code List<TrainingData> }
	 */
	public List<TrainingData> getRelatedTraining(String userId) {
		log.error("Method not implemented! userId = [{}]", userId);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
