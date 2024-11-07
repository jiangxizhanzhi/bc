package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实例角色
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoomRole {

	/**
	 * 用户
	 */
	@ApiModelProperty(required = true)
	@NotNull
	private List<String> users;

	/**
	 * 资源id
	 */
	private String resourceId;

}