package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Getter
@Setter
public class RedBlueTaskReleaseProgress extends TaskReleaseProgress implements Serializable {


	/**
	 * RED 角色
	 */
	@ApiModelProperty(value = "红方")
	private GroupTaskReleaseProgress redRoles = new GroupTaskReleaseProgress();

	/**
	 * 蓝色角色
	 */
	@ApiModelProperty(value = "蓝方")
	private GroupTaskReleaseProgress blueRoles = new GroupTaskReleaseProgress();
}
