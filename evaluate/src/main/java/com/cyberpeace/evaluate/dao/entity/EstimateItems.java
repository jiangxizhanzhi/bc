package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估项表
 */
@Data
public class EstimateItems extends BaseTable {

	/**评估项id */
	@ApiModelProperty("评估项id")
	private String estimateItemsId;

	/**名称 */
	@ApiModelProperty("名称")
	private String name;

	/**算法id */
	@ApiModelProperty("算法id")
	private String algorithmId;

	/**评估方案id */
	@ApiModelProperty("评估方案id")
	private String estimateSchemeId;

	/**自定义参数*/
	@ApiModelProperty("自定义参数")
	private String params;

}
