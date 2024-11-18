package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工具类别响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ToolCategoryRsp implements Serializable {

	/**
	 * 中文名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分类中文名称")
	private String cnName;

	/**
	 * 中文名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分类英文名称")
	private String enName;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("ID")
	private String resourceId;

}
