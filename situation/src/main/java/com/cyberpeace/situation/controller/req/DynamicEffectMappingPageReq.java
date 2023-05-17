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
	 * 映射名称
	 */
	@ApiModelProperty(value = "映射名称")
	private String name;

	/**
	 * 映射类型
	 */
	@ApiModelProperty(value = "映射类型")
	private Integer type;

}
