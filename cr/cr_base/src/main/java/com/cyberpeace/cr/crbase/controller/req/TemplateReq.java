package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@ApiModel
@Data
public class TemplateReq extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	private String type;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private String difficult;

}
