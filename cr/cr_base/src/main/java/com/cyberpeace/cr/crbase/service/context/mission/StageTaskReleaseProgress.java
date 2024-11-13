package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阶段任务下发进展
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class StageTaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 阶段名称
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stageName;
}
