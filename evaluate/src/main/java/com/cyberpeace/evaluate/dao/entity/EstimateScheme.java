package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 评估方案表
 */
@Data
public class EstimateScheme extends BaseTable {

	/**评估方案id */
	private String estimateSchemeId;

	/**方案名 */
	private String name;

	/**方案描述 */
	private String desc;

	private String logo;

	/**方案数据内容 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String content;

}
