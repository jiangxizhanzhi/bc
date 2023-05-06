package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户姓名
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class NameElement {

	@ApiModelProperty(value = "用户姓名")
	private String name;
}
