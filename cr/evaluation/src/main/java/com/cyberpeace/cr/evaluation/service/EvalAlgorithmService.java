package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 *
 */
public interface EvalAlgorithmService extends IService<EvalAlgorithm> {

	Integer selectUsedNum(List<String> ids);

	Boolean delete(List<String> ids);

	void updateStateById(String resourceId);

	List<EvalAlgorithm> selectByType(String type);

	IPage<EvalAlgorithm> page(EvalListQuery listQuery);
}
