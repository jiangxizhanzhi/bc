package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation
 */
public interface EvalAggregationMapper extends BaseMapper<EvalAggregation> {

	List<EvalAggregation> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	Integer selectAlgorithmNum(List<String> algorithmIds);

	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




