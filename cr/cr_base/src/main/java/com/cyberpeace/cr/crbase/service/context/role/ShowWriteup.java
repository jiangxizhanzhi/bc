package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 显示题解开放状态
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ShowWriteup {

	@ApiModelProperty(value = "是否开放题解")
	private Boolean showWriteup;
}
