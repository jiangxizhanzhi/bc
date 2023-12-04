package com.cyberpeace.rtks.service.bo.career;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DetailPageReq extends PageReqBase {

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "难度")
	private Integer difficulty;

	@ApiModelProperty("资源id")
	@NotNull
	private String resourceId;

}
