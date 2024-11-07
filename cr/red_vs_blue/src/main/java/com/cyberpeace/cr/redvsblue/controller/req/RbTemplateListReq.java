package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板列表请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbTemplateListReq extends PageReqBase {

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别ID")
	private String category;

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
	private Integer difficult;

	/**
	 * id列表
	 */
	@ApiModelProperty(value = "business_template_id列表，传入字符串，多个用逗号分隔", dataType = "java.lang.String")
	private List<String> ids;
}
