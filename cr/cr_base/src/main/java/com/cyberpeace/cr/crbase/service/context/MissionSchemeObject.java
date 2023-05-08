package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 任务编排中间模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionSchemeObject<T> implements Serializable {

	@ApiModelProperty(value = "阶段名称")
	private String stage;

	@ApiModelProperty(value = "任务列表")
	private List<T> missions;

	@ApiModelProperty(value = "动态任务列表")
	private List<String> dynamic;
}
