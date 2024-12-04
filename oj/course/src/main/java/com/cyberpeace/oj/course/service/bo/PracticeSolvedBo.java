package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习解决了 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class PracticeSolvedBo implements Serializable {

	/**
	 * 用户答案
	 *
	 * @see String
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否回答正确")
	private Boolean solved;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("得分")
	private Double score;

}
