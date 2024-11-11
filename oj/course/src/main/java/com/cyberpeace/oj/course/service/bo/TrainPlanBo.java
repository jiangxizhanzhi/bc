package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 训练计划博
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class TrainPlanBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("训练计划编号")
	private String resourceId;

	/**
	 * 训练计划名称
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 用户名
	 */
	@ApiModelProperty("姓名")
	private String username;

}
