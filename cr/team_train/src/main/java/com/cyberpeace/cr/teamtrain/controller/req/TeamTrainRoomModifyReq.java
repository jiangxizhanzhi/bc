package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainRoomModifyReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	private Integer category;

	private String name;

	private List<String> preSkill;

	private List<String> trainSkill;

	private Date startTime;

	private Date endTime;

	private String description;

	private String roleConfig;

	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;
}
