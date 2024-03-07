package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalIndicatorRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 *
 */
public interface EvalIndicatorService extends IService<EvalIndicator> {

	/** 批量保存指标项
	 * @param evalIndicators
	 * @param userId
	 * @param collectionIds
	 */
	void saveBatchContext(EvalIndicatorReq evalIndicators, String userId,
			List<String> collectionIds);

	/** 批量删除指标项
	 * @param ids
	 * @return
	 */
	Boolean batchDelete(List<String> ids);

	/** 删除指定指标项的聚合项和采集项
	 * @param evalIndicatorIds
	 */
	void deleteAggregationAndCollection(List<String> evalIndicatorIds);

	/** 批量更新指标项
	 * @param evalIndicatorReq
	 * @param evalIndicators
	 * @param userId
	 * @param collectionIds
	 */
	void updateBatchContext(EvalIndicatorReq evalIndicatorReq,
			List<EvalIndicatorContext> evalIndicators, String userId, List<String> collectionIds);

	/** 分页查询指标项
	 * @param listQuery
	 * @return
	 */
	IPage<EvalIndicator> page(EvalListQuery listQuery);

	/**查询已使用的指标项数量
	 * @param ids 指标项id列表
	 * @return
	 */
	Integer selectIndexUsedNum(List<String> ids);

	EvalIndicatorRsp convertEvalIndicatorToEvalSystemRsp(EvalIndicator evalIndicator);
}
