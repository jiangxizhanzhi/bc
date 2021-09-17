package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估--变量项关联表
 */
@Data
public class EstimateVariableLinks extends BaseTable {

	/**评估-变量项关联表id */
	private String estimateVariableLinkId;

	/**评估id */
	private String estimateId;

	/**变量项id */
	private String estimateVariableId;

	/**采集器id */
	private String collectId;

	/**主机id */
	private String hostId;

}
