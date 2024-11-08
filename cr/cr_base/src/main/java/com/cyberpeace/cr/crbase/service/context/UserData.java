package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户简要数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class UserData {

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "头像")
	private String logo;

	/**
	 * 代表名称
	 */
	@ApiModelProperty(value = "姓名")
	private String repName;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;
}
