package com.cyberpeace.cr.crbase.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShareReq {

	@ApiModelProperty(value = "模板id")
	List<String> ids;

	@ApiModelProperty(value = "授权类别")
	Integer shareMode;

	@ApiModelProperty(value = "授权人员id")
	List<String> shareTeachers;

}
