package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练计划更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TrainPlanUpdateReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源Id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("资源名称")
	private String name;
}
