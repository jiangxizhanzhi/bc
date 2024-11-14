package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DeleteReq implements Serializable {

	/**
	 * ID列表
	 */
	@ApiModelProperty("ID列表")
	private List<String> ids;
}
