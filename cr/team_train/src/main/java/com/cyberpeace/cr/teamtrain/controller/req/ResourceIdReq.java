package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ResourceIdReq implements Serializable {

	@ApiModelProperty()
	private String resourceId;

}