package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 任务下发进度
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/15
 * @see TaskReleaseProgress
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RedBlueTaskReleaseProgress extends TaskReleaseProgress implements Serializable {


	/**
	 * RED 角色
	 *
	 * @see GroupTaskReleaseProgress
	 */
	@ApiModelProperty(value = "红方")
	private GroupTaskReleaseProgress redRoles = new GroupTaskReleaseProgress();

	/**
	 * 蓝色角色
	 *
	 * @see GroupTaskReleaseProgress
	 */
	@ApiModelProperty(value = "蓝方")
	private GroupTaskReleaseProgress blueRoles = new GroupTaskReleaseProgress();
}
