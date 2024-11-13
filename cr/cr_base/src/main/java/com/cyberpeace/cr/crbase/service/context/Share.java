package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class Share {

	/**
	 * 授权操作类型
	 */
	@ApiModelProperty(value = "授权操作类型")
	private Integer shareMode;

	/**
	 * 授权操作用户列表
	 */
	@ApiModelProperty(value = "授权操作用户列表")
	private List<ShareTeacher> teachers;
}
