package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;

/**
 *
 */
public interface EvalIndicatorService extends IService<EvalIndicator> {

	void saveBatchContext(List<EvalIndicatorContext> evalIndicators, String userId,
			List<String> collectionIds);

	Boolean batchDelete(List<String> ids);
}
