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
public class TeamTaskReleaseProgress extends TaskReleaseProgress implements Serializable {

	/**
	 * 角色
	 *
	 * @see GroupTaskReleaseProgress
	 */
	@ApiModelProperty(value = "各角色进度")
	private GroupTaskReleaseProgress roles = new GroupTaskReleaseProgress();

}
