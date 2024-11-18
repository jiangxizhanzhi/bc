package com.cyberpeace.common.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 名称计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/10
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class NameCount implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "计数")
	private Integer count;

	public NameCount() {}

	public NameCount(String name, Integer count) {
		this.name = name;
		this.count = count;
	}

	public void add() {
		this.count += 1;
	}
}
