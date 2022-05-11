package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation
 */
public interface EvalAggregationMapper extends BaseMapper<EvalAggregation> {

	List<EvalAggregation> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	Integer selectAlgorithmNum(List<String> algorithmIds);

	IPage<EvalAlgorithm> listEvalAlgorithm(Page page, EvalListQuery listQuery);

}




