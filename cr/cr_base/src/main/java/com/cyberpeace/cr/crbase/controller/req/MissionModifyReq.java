package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionModifyReq implements Serializable {

	@ApiModelProperty(required = true)
	private String title;

	@ApiModelProperty(required = true)
	private String resourceId;

	@ApiModelProperty(required = true)
	private Integer score;

	@ApiModelProperty(required = true)
	private List<Integer> skillPoints;

}