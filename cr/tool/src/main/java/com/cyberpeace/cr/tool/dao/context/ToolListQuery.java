package com.cyberpeace.cr.tool.dao.context;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ToolListQuery extends PatternBase {

	@ApiModelProperty("分类")
	private String category;

	@ApiModelProperty("平台")
	private String platforms;

	@ApiModelProperty("授权")
	private Boolean cracked;

	private String type;

	private String tag;

}
