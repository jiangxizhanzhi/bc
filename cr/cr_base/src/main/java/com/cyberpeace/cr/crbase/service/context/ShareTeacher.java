package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权的教员
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ShareTeacher {

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户id，resource_id")
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 组织
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "组织id")
	private String organization;

	/**
	 * 组织名称都
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "组织名称")
	private String organizationNameAll;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

}
