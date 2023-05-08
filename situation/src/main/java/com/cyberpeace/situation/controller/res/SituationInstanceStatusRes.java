package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实例状态返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationInstanceStatusRes implements Serializable {

	/**
	 * 代码
	 */
	private String code;

	/**
	 * 状态值
	 */
	@ApiModelProperty("状态值")
	private String desc;

}
