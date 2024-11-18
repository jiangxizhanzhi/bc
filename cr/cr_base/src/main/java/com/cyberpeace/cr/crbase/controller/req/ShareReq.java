package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 共享请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ShareReq {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "模板id")
	@NotNull
	private List<String> ids;

	/**
	 * 共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权类别")
	@NotNull
	private Integer shareMode;

	/**
	 * 分享教师
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "授权人员id")
	@NotNull
	private List<String> shareTeachers;

}
