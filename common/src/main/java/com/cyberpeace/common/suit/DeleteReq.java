package com.cyberpeace.common.suit;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DeleteReq implements Serializable {

	@ApiModelProperty
	private List<String> ids;
}
