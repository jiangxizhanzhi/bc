package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习解决结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeSolvedRes implements Serializable {

	/**
	 * 练习id
	 */
	@ApiModelProperty("练习id")
	private String practiceId;

	/**
	 * 解决
	 */
	@ApiModelProperty("答题状态")
	private Integer solved;
}
