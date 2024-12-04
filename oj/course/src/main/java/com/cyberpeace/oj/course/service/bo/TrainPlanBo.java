package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 训练计划博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainPlanBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
	private String resourceId;

	/**
	 * 训练计划名称
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String username;

}
