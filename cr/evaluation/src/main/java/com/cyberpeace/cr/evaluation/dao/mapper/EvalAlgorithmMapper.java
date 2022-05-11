package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm
 */
public interface EvalAlgorithmMapper extends BaseMapper<EvalAlgorithm> {

	void updateStateById(String resourceId);

	List<EvalAlgorithm> selectByType(String type);

	boolean existByName(String name);
}




