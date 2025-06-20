package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 类别进展结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProgressRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("方向名称")
	private String name;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("方向id")
	private String categoryId;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("已做题数")
	private Integer count;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty("总题数")
	private Integer sum;

}
