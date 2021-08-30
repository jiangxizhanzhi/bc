package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ToolListRes {

	@ApiModelProperty("多个分类i18n名称逗号分割")
	private String categoryI18nName;

	@ApiModelProperty("是否破解")
	private Boolean cracked;

	@ApiModelProperty("靶标名称")
	private String deviceName;

	private Integer id;

	@ApiModelProperty("工具语言")
	private List<String> language;


	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("平台名称")
	private String platformsNames;

	@ApiModelProperty("发布时间")
	private String publishTime;

	private String resourceId;

	@ApiModelProperty("工具类型")
	private Integer toolType;
}
