package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateReq extends BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private List<CrRoleConfig> roleList;

	private Integer peopleNumber;

	@ApiModelProperty(required = true)
	@NotNull
	private Object timeLineConfig = "{}";

}
