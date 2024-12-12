package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户姓名
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class NameElement {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "用户姓名")
	private String name;
}
