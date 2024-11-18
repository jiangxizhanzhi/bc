package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实例角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RoomRole {

	/**
	 * 用户
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private List<String> users;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

}