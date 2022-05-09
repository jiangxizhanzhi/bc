package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;

/**
 *
 */
public interface EvalAlgorithmService extends IService<EvalAlgorithm> {

	Integer selectUsedNum(List<String> ids);

	Boolean delete(List<String> ids);

	void updateStateById(String resourceId);

	List<EvalAlgorithm> selectByType(String type);
}
