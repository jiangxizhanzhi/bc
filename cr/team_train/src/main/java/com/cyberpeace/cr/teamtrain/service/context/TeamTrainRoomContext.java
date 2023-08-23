package com.cyberpeace.cr.teamtrain.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 团队训练实例模型
 */
@Data
public class TeamTrainRoomContext implements Serializable {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

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
	private List<String> preSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> trainSkill;

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

	private Boolean enableRecording;

	private String logo;

	private Integer trainCategory;

	@ApiModelProperty(value = "允许部分启动,日常训练功能")
	private Boolean supportPartialStart;
}
