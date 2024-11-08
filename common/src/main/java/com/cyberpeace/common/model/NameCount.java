package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 名称计数
 *
 * @author SN
 * @date 2023/05/10
 */
@Data
public class NameCount implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 计数
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
