package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 空白答案
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class BlankAnswer implements Serializable {

	/**
	 * 重量
	 */
	@ApiModelProperty("权重")
	private Integer weight;

	/**
	 * 值
	 */
	@ApiModelProperty("答案")
	private String value;

}
