package com.cyberpeace.evaluate.dao.entity;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估实例表
 */
@Data
public class EstimateInstance extends BaseTable {

	/**评估实例id */
	private String estimateInstanceId;

	/**评估id */
	private String estimateId;

	/**场景id */
	private String sceneId;

	/**评估开始时间 */
	private Date startTime;

	/**评估结束时间 */
	private Date endTime;

	/**评估结果（json） */
	private String result;

	/**状态（评估中、评估停止） */
	private Integer status;

	/**保存状态（1=手动保存，只有手动保存的可被查询） */
	private Integer save;

	/**名称*/
	private String instanceName;

	/**描述*/
	private String instanceDesc;

}
