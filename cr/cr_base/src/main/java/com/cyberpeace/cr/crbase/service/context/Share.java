package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class Share {

	/**
	 * 共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权操作类型")
	private Integer shareMode;

	/**
	 * 教师
	 *
	 * @see List<ShareTeacher>
	 */
	@ApiModelProperty(value = "授权操作用户列表")
	private List<ShareTeacher> teachers;
}
