package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 空白答案
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class BlankAnswer implements Serializable {

	/**
	 * 权重
	 */
	@ApiModelProperty("权重")
	private Integer weight;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
	private String value;

}
