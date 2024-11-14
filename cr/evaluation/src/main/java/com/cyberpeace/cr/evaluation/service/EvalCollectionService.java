package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;

/**
 * 测评收集Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface EvalCollectionService extends IService<EvalCollection> {

	/**
	 * 批量保存采集项
	 *
	 * @param evalCollectionContexts 测评收集上下文
	 */
	void saveBatchContext(List<EvalCollectionContext> evalCollectionContexts);

	/**
	 * 通过指标项id获取采集项列表
	 *
	 * @param evalIndicatorId
	 * @return
	 */
	List<EvalCollectionContext> getContextByEvalIndicatorId(String evalIndicatorId);
}
