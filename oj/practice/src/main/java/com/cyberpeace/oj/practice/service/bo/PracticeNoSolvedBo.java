package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 练习不解决 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class PracticeNoSolvedBo implements Serializable {

	/**
	 * 已解决
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否解题成功")
	private Boolean isSolved;

}
