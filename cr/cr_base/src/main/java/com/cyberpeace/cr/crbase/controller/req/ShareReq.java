package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class ShareReq {

	@ApiModelProperty(value = "模板id")
	@NonNull
	List<String> ids;

	@ApiModelProperty(value = "授权类别")
	@NonNull
	Integer shareMode;

	@ApiModelProperty(value = "授权人员id")
	@NonNull
	List<String> shareTeachers;

}
