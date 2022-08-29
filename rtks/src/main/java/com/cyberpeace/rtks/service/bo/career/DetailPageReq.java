package com.cyberpeace.rtks.service.bo.career;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DetailPageReq extends BTPageReq {

	@ApiModelProperty("资源id")
	@NotNull
	private String resourceId;

}
