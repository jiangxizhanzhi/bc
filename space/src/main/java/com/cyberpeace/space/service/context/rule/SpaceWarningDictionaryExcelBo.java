package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SpaceWarningDictionaryExcelBo {

	@ApiModelProperty(value = "告警名称")
	private String name;

	@ApiModelProperty(value = "工具类型")
	private String toolType;

	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	@ApiModelProperty(value = "等级描述")
	private String description;

	@ApiModelProperty("更新时间")
	private Date updateTime;
}
