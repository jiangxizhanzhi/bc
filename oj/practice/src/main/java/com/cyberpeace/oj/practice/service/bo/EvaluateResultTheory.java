package com.cyberpeace.oj.practice.service.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评估结果理论
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluateResultTheory implements Serializable {

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("通过状态")
	private Integer status;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("得分")
	private Double score;

	/**
	 * 总分
	 *
	 * @see Double
	 */
	@ApiModelProperty("总分")
	private Double totalScore;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;
}
