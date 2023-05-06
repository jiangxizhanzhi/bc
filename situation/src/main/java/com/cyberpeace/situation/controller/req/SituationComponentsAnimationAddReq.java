package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动画组件新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationComponentsAnimationAddReq implements Serializable {

	/**
	 * 动画名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "动画名称", required = true)
	private String name;

	/**
	 * 二维组件id
	 */
	@ApiModelProperty(value = "二维组件id")
	private String chartId;

	/**
	 * 动画描述
	 */
	@ApiModelProperty(value = "动画描述")
	private String description;

	/**
	 * 配置
	 */
	@ApiModelProperty(value = "配置")
	private String config;

}
