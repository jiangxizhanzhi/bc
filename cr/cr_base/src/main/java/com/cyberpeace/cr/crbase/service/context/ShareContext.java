package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权模型
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ShareContext {

	/**
	 * id列表
	 */
	@ApiModelProperty(value = "模板id")
	private List<String> ids;

	/**
	 * 共享模式
	 */
	@ApiModelProperty(value = "授权类别")
	private Integer shareMode;

	/**
	 * 分享教师
	 */
	@ApiModelProperty(value = "授权人员id")
	private List<String> shareTeachers;

}
