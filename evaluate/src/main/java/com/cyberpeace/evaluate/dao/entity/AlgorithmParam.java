package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlgorithmParam extends BaseTable {

	/**参数编号 */
	@ApiModelProperty("参数编号")
	private String paramId;

	/**参数名称 */
	@ApiModelProperty("参数名称")
	private String paramName;

	/**参数说明 */
	@ApiModelProperty("参数说明")
	private String desc;

	/**参数类型 */
	@ApiModelProperty("参数类型")
	private String paramType;

	/**默认值 */
	@ApiModelProperty("默认值")
	private String defaultValue;
}
