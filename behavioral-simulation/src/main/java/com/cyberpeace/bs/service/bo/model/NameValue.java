package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 键值对
 * 用于饼状图
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class NameValue implements Serializable {

	/**
	 * 标签
	 */
	@ApiModelProperty(value = "标签")
	private String name;

	/**
	 * 值
	 */
	@ApiModelProperty(value = "值")
	private Integer value;

	public NameValue(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
}