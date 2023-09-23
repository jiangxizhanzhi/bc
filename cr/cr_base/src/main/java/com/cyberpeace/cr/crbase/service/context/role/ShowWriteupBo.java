package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题解开放状态
 */
@Data
public class ShowWriteupBo {

	@ApiModelProperty(value = "是否开放题解")
	private Boolean showWriteup;
}
