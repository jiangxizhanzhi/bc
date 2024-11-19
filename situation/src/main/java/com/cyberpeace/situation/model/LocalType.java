package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 本地类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class LocalType {

	/**
	 * 类型编码
	 *
	 * @see String
	 */
	@ApiModelProperty("类型编码")
	private String code;

	/**
	 * 类型名称
	 *
	 * @see String
	 */
	@ApiModelProperty("类型名称")
	private String msg;

	public LocalType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
