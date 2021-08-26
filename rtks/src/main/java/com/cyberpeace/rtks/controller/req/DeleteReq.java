package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class DeleteReq implements Serializable {

	@ApiModelProperty
	private List<String> ids;
}
