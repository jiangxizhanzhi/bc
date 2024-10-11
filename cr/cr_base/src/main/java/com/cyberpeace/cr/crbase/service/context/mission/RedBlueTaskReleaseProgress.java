package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 任务下发进度
 *
 * @author SN
 */
@Data
public class RedBlueTaskReleaseProgress extends TaskReleaseProgress implements Serializable {


	@ApiModelProperty(value = "红方")
	private GroupTaskReleaseProgress redRoles = new GroupTaskReleaseProgress();

	@ApiModelProperty(value = "蓝方")
	private GroupTaskReleaseProgress blueRoles = new GroupTaskReleaseProgress();
}
