package com.cyberpeace.cr.tool.dao.context;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PatternBase
 * @since 1.0.0
 */
@Data
public class ToolListQuery extends PatternBase {

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("分类")
	private String category;

	/**
	 * 平台
	 *
	 * @see String
	 */
	@ApiModelProperty("平台")
	private String platforms;

	/**
	 * 破获
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("授权")
	private Boolean cracked;

}
