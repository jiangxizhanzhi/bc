package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.teamtrain.service.context.RoomRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainRoomReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String template;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer category;

	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(required = true)
	@NotNull
	private String preSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private String trainSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer difficult;

	@ApiModelProperty(required = true)
	@NotNull
	private Date startTime;

	@ApiModelProperty(required = true)
	@NotNull
	private Date endTime;

	@ApiModelProperty(required = true)
	@NotNull
	private String description;

	@ApiModelProperty(required = true)
	@NotNull
	private List<RoomRole> roleConfig;

	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;
}
