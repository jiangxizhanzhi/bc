package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习不解决Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeNoSolvedBo implements Serializable {

	/**
	 * 是否解题成功
	 */
	@ApiModelProperty("是否解题成功")
	private Boolean isSolved;

}
