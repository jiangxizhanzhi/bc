package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效映射分页请求
 *
 * @Author xuxz
 * @Date 26/11/2022 下午5:09
 */
@Data
public class DynamicEffectMappingPageReq extends PageReqBase implements Serializable {

	/**
	 * 动效名称
	 */
	@ApiModelProperty(value = "动效名称")
	private String name;

	/**
	 * 动效类型
	 */
	@ApiModelProperty(value = "动效类型")
	private String category;

	/**
	 * 源节点动效名称
	 */
	@ApiModelProperty(value = "源节点动效名称")
	private String sourceNodeDynamicName;

	/**
	 * 目标节点动效名称
	 */
	@ApiModelProperty(value = "目标节点动效名称")
	private String targetNodeDynamicName;

	/**
	 * 聚焦
	 */
	@ApiModelProperty("聚焦")
	private String focus;

	/**
	 * 颜色
	 */
	@ApiModelProperty("颜色")
	private String color;

	/**
	 * 线形
	 */
	@ApiModelProperty("线形")
	private String line;

	/**
	 * 线形大小
	 */
	@ApiModelProperty("线形大小")
	private String lineSize;
}
