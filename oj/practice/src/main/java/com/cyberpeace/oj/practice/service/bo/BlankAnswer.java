package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 空白答案
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
public class BlankAnswer implements Serializable {

	/**
	 * 重量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("权重")
	private Integer weight;

	/**
	 * 值
	 *
	 * @see String
	 */
	@ApiModelProperty("答案")
	private String value;

}
