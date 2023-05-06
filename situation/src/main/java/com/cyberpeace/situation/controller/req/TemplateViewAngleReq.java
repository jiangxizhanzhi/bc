package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.situation.model.ViewAngle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板视角请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class TemplateViewAngleReq implements Serializable {

	/**
	 * 模板id
	 */
	@ApiModelProperty("模板ID")
	private String templateId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型 1-模板 2-实例")
	private Integer type;

	/**
	 * 初始视角
	 */
	@ApiModelProperty("初始视角")
	private ViewAngle viewAngle;

}
