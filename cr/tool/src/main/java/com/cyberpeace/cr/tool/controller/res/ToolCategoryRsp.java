package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToolCategoryRsp implements Serializable {

	@ApiModelProperty("分类中文名称")
	private String cnName;

	@ApiModelProperty("分类英文名称")
	private String enName;

	@ApiModelProperty("ID")
	private String resourceId;

	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;

}
