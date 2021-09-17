package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估项--指标项关联表
 */
@Data
public class EstimateIndexLinks extends BaseTable {

	/**评估项--指标项id*/
	private String estimateIndexLinkId;

	/**评估项id */
	private String estimateItemsId;

	/**指标项id */
	private String estimateIndexId;

	/**评估方案id */
	private String estimateSchemeId;

	/**评估项算法的入参参数顺序 */
	private Integer serialNumber;

}
