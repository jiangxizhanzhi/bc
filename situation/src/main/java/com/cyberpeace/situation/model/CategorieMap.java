package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CategorieMap implements Serializable {

	/**
	 * 角色映射
	 *
	 * @see Map<Integer, String>
	 */
	@ApiModelProperty("角色映射")
	private Map<Integer, String> role;

	/**
	 * 一级类型映射
	 *
	 * @see Map<Integer, String>
	 */
	@ApiModelProperty("一级类型映射")
	private Map<Integer, String> firstCategorie;

	/**
	 * 二级类型映射
	 *
	 * @see Map<Integer, String>
	 */
	@ApiModelProperty("二级类型映射")
	private Map<Integer, String> secondCategorie;

}
