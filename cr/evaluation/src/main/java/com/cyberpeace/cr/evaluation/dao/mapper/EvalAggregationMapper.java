package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation
 */
@Mapper
@Repository
public interface EvalAggregationMapper extends BaseMapper<EvalAggregation> {

	List<EvalAggregation> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	Integer selectAlgorithmNum(List<String> algorithmIds);

	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




