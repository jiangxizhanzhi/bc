package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionSchemeTemplateReq implements Serializable {

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "描述", required = true)
	private String description;

	@ApiModelProperty(value = "模板类型", required = true)
	private Integer template;

	@ApiModelProperty(value = "模板颜色", required = true)
	private Integer color;

	@ApiModelProperty(value = "任务编排", required = true)
	@Valid
	private List<MissionSchemeStage> combatConfig;

}
