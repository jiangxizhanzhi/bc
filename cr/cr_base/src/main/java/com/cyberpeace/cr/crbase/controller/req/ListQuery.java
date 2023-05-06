package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 列表查询前台使用的查询类
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@ApiModel
@Data
public class ListQuery extends PageReqBase {

	@ApiModelProperty(value = "训练类别ID")
	private String category;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	private List<String> ids;

}