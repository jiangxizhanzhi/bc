package com.cyberpeace.iov.index.service.bo.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NameAndPercent {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("百分比")
	private String percent;
}
