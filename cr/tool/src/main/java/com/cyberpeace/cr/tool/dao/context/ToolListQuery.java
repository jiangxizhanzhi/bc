package com.cyberpeace.cr.tool.dao.context;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具列表查询
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ToolListQuery extends PatternBase {

	/**
	 * 类别
	 */
	@ApiModelProperty("分类")
	private String category;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台")
	private String platforms;

	/**
	 * 破获
	 */
	@ApiModelProperty("授权")
	private Boolean cracked;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 标记
	 */
	private String tag;

}
