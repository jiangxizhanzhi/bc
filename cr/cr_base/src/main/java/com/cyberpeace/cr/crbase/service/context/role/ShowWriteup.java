package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题解开放状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ShowWriteup {

	/**
	 * 显示 Writeup
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否开放题解")
	private Boolean showWriteup;
}
