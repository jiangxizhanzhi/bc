package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
@Data
public class TemplateReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类别ID")
	private String type;

	/**
	 * 搜索
	 */
	@ApiModelProperty(value = "检索参数")
	private String search;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "难度")
	private String difficult;

}
