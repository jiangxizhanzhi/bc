package com.cyberpeace.cr.crbase.service;

import static com.cyberpeace.cr.crbase.service.impl.CrBaseServiceImpl.register;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * cr事件统计数据
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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

	/**
	 * 获取训练数
	 *
	 * @param userId 用户id
	 * @return {@link TrainingCount}
	 */
	public TrainingCount getTrainingCount(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 获取相关训练
	 *
	 *
	 * @param userId 用户id
	 * @return {@link List}<{@link TrainingData}>
	 */
	public List<TrainingData> getRelatedTraining(String userId) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}
}
