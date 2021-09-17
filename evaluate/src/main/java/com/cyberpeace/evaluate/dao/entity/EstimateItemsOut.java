package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估项输出表
 */
@Data
public class EstimateItemsOut extends BaseTable {

	/**评估项输出id */
	private String estimateItemsOutId;

	/**场景id */
	private String sceneId;

	/**评估实例id */
	private String estimateInstanceId;

	/**评估项id */
	private String estimateItemsId;

	/**得分（0-100的分数） */
	private Integer score;

}
