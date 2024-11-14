package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练计划新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TrainPlanAddReq implements Serializable {

	/**
	 * 资源名称
	 */
	@ApiModelProperty("资源名称")
	private String name;
}
