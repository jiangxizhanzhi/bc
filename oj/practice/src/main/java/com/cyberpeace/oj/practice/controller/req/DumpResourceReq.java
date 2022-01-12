package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class DumpResourceReq implements Serializable {

	@ApiModelProperty("习题集编号")
	private List<String> resourceIds;

}
