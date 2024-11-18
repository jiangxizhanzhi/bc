package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ShareContext {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "模板id")
	private List<String> ids;

	/**
	 * 共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权类别")
	private Integer shareMode;

	/**
	 * 分享教师
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "授权人员id")
	private List<String> shareTeachers;

}
