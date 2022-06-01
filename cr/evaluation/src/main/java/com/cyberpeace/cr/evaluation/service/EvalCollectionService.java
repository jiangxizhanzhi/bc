package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;

/**
 *
 */
public interface EvalCollectionService extends IService<EvalCollection> {

	void saveBatchContext(List<EvalCollectionContext> evalCollectionContexts);

	List<EvalCollectionContext> getContextByEvalIndicatorId(String evalIndicatorId);
}
