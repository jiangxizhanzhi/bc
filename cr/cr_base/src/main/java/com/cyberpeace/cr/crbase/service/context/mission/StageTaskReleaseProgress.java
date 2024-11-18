package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 暂存任务发布进度
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ReleaseProgress
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class StageTaskReleaseProgress extends ReleaseProgress implements Serializable {

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stageName;
}
