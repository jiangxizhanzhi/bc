package com.cyberpeace.evaluate.dao.entity;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 评估格式化数据表
 */
@Data
public class EstimateFormatData extends BaseTable {

	/**评估数据id */
	private String estimateFormatDataId;

	/**场景id */
	private String sceneId;

	/**主机Id */
	private String hostId;

	/**变量项id */
	private String estimateVariableId;

	/**评估实例id */
	private String estimateInstanceId;

	/**数值大小 */
	private Integer value;

	/**采集时间 */
	private Date collectDate;

}
