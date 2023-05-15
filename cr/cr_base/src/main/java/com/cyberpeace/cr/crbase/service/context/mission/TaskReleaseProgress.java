package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class TaskReleaseProgress extends ReleaseProgress implements Serializable {

	@ApiModelProperty(value = "待下发数")
	private Integer unreleased = 0;

	@ApiModelProperty(value = "已提交数")
	private Integer submitted = 0;

	@ApiModelProperty(value = "待提交数")
	private Integer unsubmitted = 0;

	@ApiModelProperty(value = "红方")
	private GroupTaskReleaseProgress redRoles = new GroupTaskReleaseProgress();

	@ApiModelProperty(value = "蓝方")
	private GroupTaskReleaseProgress blueRoles = new GroupTaskReleaseProgress();
}
