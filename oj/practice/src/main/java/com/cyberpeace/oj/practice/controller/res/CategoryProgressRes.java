package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类别进展结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProgressRes implements Serializable {

	/**
	 * 方向名称
	 */
	@ApiModelProperty("方向名称")
	private String name;

	/**
	 * 方向id
	 */
	@ApiModelProperty("方向id")
	private String categoryId;

	/**
	 * 已做题数
	 */
	@ApiModelProperty("已做题数")
	private Integer count;

	/**
	 * 总题数
	 */
	@ApiModelProperty("总题数")
	private Integer sum;

}
