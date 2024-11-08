package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习不解决 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class PracticeNoSolvedBo implements Serializable {

	/**
	 * 已解决
	 */
	@ApiModelProperty("是否解题成功")
	private Boolean isSolved;

}
