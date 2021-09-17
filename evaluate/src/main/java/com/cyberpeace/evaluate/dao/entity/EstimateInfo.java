package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估表
 */
@Data
public class EstimateInfo extends BaseTable {

	/**评估id */
	private String estimateId;

	/**评估名称 */
	private String name;

	/**评估描述 */
	private String desc;

	/** */
	private String logo;

	/**拓扑id */
	private String topoId;

	/**评估方案id */
	private String estimateSchemeId;

	/**用户id */
	private String userId;

}
