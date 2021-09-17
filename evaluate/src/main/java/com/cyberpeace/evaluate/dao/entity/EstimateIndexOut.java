package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估指标项输出
 */
@Data
public class EstimateIndexOut extends BaseTable {

	/**评估指标项输出id */
	private String estimateIndexOutId;

	/**场景id */
	private String sceneId;

	/**评估实例id */
	private String estimateInstanceId;

	/**指标项id */
	private String estimateIndexId;

	/**得分（0-100的分数） */
	private Integer score;

}
