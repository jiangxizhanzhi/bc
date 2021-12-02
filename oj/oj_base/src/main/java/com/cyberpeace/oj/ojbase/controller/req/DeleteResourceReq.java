package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class DeleteResourceReq implements Serializable {

	@ApiModelProperty("资源数据编号")
	private List<String> resourceIds;

}
