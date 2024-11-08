package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 类别新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class CategoryAddReq implements Serializable {

	/**
	 * 中文名称
	 */
	@ApiModelProperty("中文名称")
	@NotBlank
	private String cnName;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	@NotBlank
	private String type;
}
