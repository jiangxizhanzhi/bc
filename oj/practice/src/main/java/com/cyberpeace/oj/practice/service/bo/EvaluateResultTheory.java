package com.cyberpeace.oj.practice.service.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 评估结果理论
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluateResultTheory implements Serializable {

	/**
	 * 状态
	 */
	@ApiModelProperty("通过状态")
	private Integer status;

	/**
	 * 得分
	 */
	@ApiModelProperty("得分")
	private Double score;

	/**
	 * 总分
	 */
	@ApiModelProperty("总分")
	private Double totalScore;

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;
}
