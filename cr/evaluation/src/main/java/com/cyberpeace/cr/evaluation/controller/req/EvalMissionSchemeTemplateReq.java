package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalMissionSchemeTemplateReq implements Serializable {

	@ApiModelProperty("技战法名称")
	private String name;

	@ApiModelProperty("技战法描述")
	private String description;

	@ApiModelProperty("技战法类型")
	private Integer type;

	@ApiModelProperty("模板类型")
	private Integer templateCategory;

	@ApiModelProperty("技战法内容")
	private List<MissionSchemeStage> content;

}
