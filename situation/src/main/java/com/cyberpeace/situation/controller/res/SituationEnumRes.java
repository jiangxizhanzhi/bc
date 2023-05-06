package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字符串-整型对返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationEnumRes implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 代码
	 */
	@ApiModelProperty("code")
	private Integer code;

	public SituationEnumRes(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
