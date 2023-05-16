package com.cyberpeace.cr.crbase.service.context.wiki;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WikiModifyReq extends WikiCreateReq implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;
}
