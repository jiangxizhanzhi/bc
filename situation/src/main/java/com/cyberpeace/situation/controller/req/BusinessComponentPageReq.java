package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 业务组件页面请求
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
public class BusinessComponentPageReq extends PageReqBase implements Serializable {

	/**
	 * 模板名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 模板类型
	 */
	@ApiModelProperty("模板类型")
	private String type;

}
