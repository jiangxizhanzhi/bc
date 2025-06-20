package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class ProgressProportion implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
