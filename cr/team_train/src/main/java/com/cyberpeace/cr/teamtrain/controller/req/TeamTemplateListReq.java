package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 团队模板列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@ApiModel
@Data
public class TeamTemplateListReq extends PageReqBase {

	/**
	 * 类别ID
	 */
	@ApiModelProperty(value = "类别ID")
	private String category;

	/**
	 * 检索参数
	 */
	@ApiModelProperty(value = "检索参数")
	private String search;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * id列表
	 */
	private List<String> ids;

}
