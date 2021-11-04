package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShareContext {

	@ApiModelProperty(value = "模板id")
	List<String> ids;

	@ApiModelProperty(value = "授权类别")
	Integer shareMode;

	@ApiModelProperty(value = "授权人员id")
	List<String> shareTeachers;

}
