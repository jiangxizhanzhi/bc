package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工具GHDB 数据库列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@ApiModel
public class ToolGhdbListReq extends PageReqBase {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@Getter
	@Setter
	private String title;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	@Getter
	@Setter
	private String author;
}
