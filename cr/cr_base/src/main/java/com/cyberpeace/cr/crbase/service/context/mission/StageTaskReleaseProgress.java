package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 暂存任务发布进度
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StageTaskReleaseProgress extends ReleaseProgress implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 阶段名称
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stageName;
}
