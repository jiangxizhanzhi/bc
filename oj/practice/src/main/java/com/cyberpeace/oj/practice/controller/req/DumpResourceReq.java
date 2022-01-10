package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class DumpResourceReq implements Serializable {

	@ApiModelProperty("资源编号")
	@NotEmpty
	private List<String> resourceIds;

}
