package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练计划更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanUpdateReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源Id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("资源名称")
	private String name;
}
