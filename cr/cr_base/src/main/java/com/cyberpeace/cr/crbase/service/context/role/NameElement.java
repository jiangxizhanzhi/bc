package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户姓名
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class NameElement {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户姓名")
	private String name;
}
