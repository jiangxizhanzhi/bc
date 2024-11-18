package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户简要数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class UserData {

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "头像")
	private String logo;

	/**
	 * 代表名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "姓名")
	private String repName;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;
}
