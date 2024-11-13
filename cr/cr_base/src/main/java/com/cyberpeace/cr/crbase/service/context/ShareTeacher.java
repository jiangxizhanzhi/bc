package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权的教员
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class ShareTeacher {

	/**
	 * id
	 */
	@ApiModelProperty(value = "用户id，resource_id")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 组织
	 */
	@ApiModelProperty(value = "组织id")
	private String organization;

	/**
	 * 组织名称全部
	 */
	@ApiModelProperty(value = "组织名称")
	private String organizationNameAll;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

}
