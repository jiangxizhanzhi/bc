package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实践解决结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PracticeSolvedRes implements Serializable {

	/**
	 * 实践id
	 */
	@ApiModelProperty("练习id")
	private String practiceId;

	/**
	 * 解决
	 */
	@ApiModelProperty("答题状态")
	private Integer solved;
}
