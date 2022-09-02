package com.cyberpeace.cr.teamtrain.service.context;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板
 */
@Data
public class TeamTrainTemplateContext extends BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private String roleList;

	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";

	@ApiModelProperty(required = false)
	private Integer peopleNumber;
}
