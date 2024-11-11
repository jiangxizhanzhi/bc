package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练计划新增请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class TrainPlanAddReq implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("资源名称")
	private String name;
}
