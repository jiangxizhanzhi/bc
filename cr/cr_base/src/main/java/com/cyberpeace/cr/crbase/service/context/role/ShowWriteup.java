package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题解开放状态
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ShowWriteup {

	/**
	 * 显示 Writeup
	 */
	@ApiModelProperty(value = "是否开放题解")
	private Boolean showWriteup;
}
