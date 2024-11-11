package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习设置结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeSetRes implements Serializable {

	/** 资源id */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/** 名称 */
	@ApiModelProperty("名称")
	private String name;
}
