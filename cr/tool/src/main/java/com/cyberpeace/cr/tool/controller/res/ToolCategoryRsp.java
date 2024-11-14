package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工具类别响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ToolCategoryRsp implements Serializable {

	/**
	 * 分类中文名称
	 */
	@ApiModelProperty("分类中文名称")
	private String cnName;

	/**
	 * 分类英文名称
	 */
	@ApiModelProperty("分类英文名称")
	private String enName;

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private String resourceId;

}
