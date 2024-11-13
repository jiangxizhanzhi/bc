package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户姓名
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class NameElement {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "用户姓名")
	private String name;
}
