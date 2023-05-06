package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用组件分页请求
 *
 * @Author xuxz
 * @Date 16/11/2022 下午5:02
 */
@Data
public class SituationCommonComponentsPageReq extends PageReqBase implements Serializable {

	/**
	 * 组件名称
	 */
	@ApiModelProperty("组件名称")
	private String name;

	/**
	 * 组件类型
	 */
	@ApiModelProperty("组件类型")
	private Integer componentType;

	/**
	 * 组件内部类型
	 */
	@ApiModelProperty("组件内部类型")
	private Integer type;

}
