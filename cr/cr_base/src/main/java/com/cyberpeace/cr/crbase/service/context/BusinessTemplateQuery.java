package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务模板查询
 *
 * @author cyberpeace
 * @date 2023/05/06
 *//*
	* 后台使用的查询类
	*/
@ApiModel
@Data
public class BusinessTemplateQuery extends PageReqBase {

	@ApiModelProperty(value = "业务类型ID")
	private String type;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	private List<String> ids;

}