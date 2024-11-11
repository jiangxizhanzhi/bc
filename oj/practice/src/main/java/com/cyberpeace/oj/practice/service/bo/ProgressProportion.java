package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 进度比例
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ProgressProportion implements Serializable {

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 计数
	 */
	@ApiModelProperty("解出数量")
	private Integer count;

	/**
	 * 和
	 */
	@ApiModelProperty("总数")
	private Integer sum;
}
