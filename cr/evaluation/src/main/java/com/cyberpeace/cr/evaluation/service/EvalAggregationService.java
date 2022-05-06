package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;

/**
 *
 */
public interface EvalAggregationService extends IService<EvalAggregation> {

	void saveBatchContext(List<EvalAggregationContext> evalAggregations);
}
