package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomMissionReq implements Serializable {

	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	@ApiModelProperty(value = "阶段", required = true)
	private Integer missionStage;

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "描述", required = true)
	private String description;

	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	@ApiModelProperty(value = "技能点", required = true)
	private List<Integer> skillPoints;

	@ApiModelProperty(value = "标准答案", required = true)
	private String answer;

	@ApiModelProperty(value = "选择题选项，非选择题为\"null\"", required = true)
	private String option;

	@ApiModelProperty(value = "任务类型（单选、填空等）", required = true)
	private Integer type;

	@ApiModelProperty(value = "任务类别（密码破解、网络侦查等）", required = true)
	private String category;

	@ApiModelProperty(value = "题解", required = true)
	private String writeUp;
}
