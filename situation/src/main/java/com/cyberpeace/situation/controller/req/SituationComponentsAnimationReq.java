package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动画组件请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationComponentsAnimationReq implements Serializable {

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private String endTime;

	/**
	 * 动画名称
	 */
	@ApiModelProperty(value = "动画名称")
	private String name;

}
