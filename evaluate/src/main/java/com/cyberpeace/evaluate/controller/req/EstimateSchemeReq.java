package com.cyberpeace.evaluate.controller.req;

import org.apache.commons.collections.CollectionUtils;

import com.cyberpeace.evaluate.service.context.EstimateSchemeContext;
import com.google.common.base.Preconditions;

import lombok.Data;

/**
 * 评估方案
 */
@Data
public class EstimateSchemeReq extends EstimateSchemeContext {

	public void validate() {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(getEstimateIndexes()),
				"estimateIndexes 指标项不能为空");
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(getEstimateItems()),
				"estimateItems 评估项不能为空");
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(getEstimateVariables()),
				"estimateVariables 变量项不能为空");
	}
}
