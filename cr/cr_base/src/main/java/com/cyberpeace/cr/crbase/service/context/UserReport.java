package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserReport {

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "角色名")
	private String roleName;

	@ApiModelProperty(value = "任务完成状态")
	private List<Integer> missionCompleted;

	@ApiModelProperty(value = "评语")
	private String comment;

	@ApiModelProperty(value = "分数")
	private Integer score;
}
