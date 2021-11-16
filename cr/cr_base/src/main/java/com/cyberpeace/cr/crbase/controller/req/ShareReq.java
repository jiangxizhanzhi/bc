package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShareReq {

	@ApiModelProperty(value = "模板id")
	@NotNull
	private List<String> ids;

	@ApiModelProperty(value = "授权类别")
	@NotNull
	private Integer shareMode;

	@ApiModelProperty(value = "授权人员id")
	@NotNull
	private List<String> shareTeachers;

}
