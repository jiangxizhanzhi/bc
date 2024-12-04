package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 进度比例
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ProgressProportion implements Serializable {

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("解出数量")
	private Integer count;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty("总数")
	private Integer sum;
}
