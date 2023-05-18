package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class TeamTaskReleaseProgress extends TaskReleaseProgress implements Serializable {

	@ApiModelProperty(value = "各角色进度")
	private GroupTaskReleaseProgress roles = new GroupTaskReleaseProgress();

}
