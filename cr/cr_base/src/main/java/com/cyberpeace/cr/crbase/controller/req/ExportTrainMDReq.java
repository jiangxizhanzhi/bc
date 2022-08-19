package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExportTrainMDReq implements Serializable {

	@ApiModelProperty("导出资源id")
	private List<String> resourceIds;

	@ApiModelProperty("导出资源名称")
	private List<String> names;

	@ApiModelProperty(value = "资源类型(1为单兵训练；3为团队训练；4为红蓝对抗)", required = true)
	private Integer type;

}
