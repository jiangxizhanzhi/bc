package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShowWriteup {

	@ApiModelProperty(value = "是否开放题解")
	private Boolean showWriteup;
}
