package com.cyberpeace.cr.tool.dao.context;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具列表查询
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ToolListQuery extends PatternBase {

	/**
	 * 分类
	 */
	@ApiModelProperty("分类")
	private String category;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台")
	private String platforms;

	/**
	 * 授权
	 */
	@ApiModelProperty("授权")
	private Boolean cracked;

}
