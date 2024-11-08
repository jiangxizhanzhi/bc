package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 共享请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ShareReq {

	/**
	 * id列表
	 */
	@ApiModelProperty(value = "模板id")
	@NotNull
	private List<String> ids;

	/**
	 * 共享模式
	 */
	@ApiModelProperty(value = "授权类别")
	@NotNull
	private Integer shareMode;

	/**
	 * 分享教师
	 */
	@ApiModelProperty(value = "授权人员id")
	@NotNull
	private List<String> shareTeachers;

}
