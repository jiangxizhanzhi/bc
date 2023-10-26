package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务编排中间模型
 */
@Data
public class MissionSchemeObject<T> {

	@ApiModelProperty(value = "阶段名称")
	private String stage;

	@ApiModelProperty(value = "任务列表")
	private List<T> missions;

	@ApiModelProperty(value = "动态任务列表")
	private List<String> dynamic;
}
