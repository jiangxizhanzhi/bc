package com.cyberpeace.oj.ojbase.controller.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 笔记更新请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class NoteUpdateReq implements Serializable {

	/**
	 * 笔记资源编号
	 */
	@ApiModelProperty("笔记资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 笔记内容
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 授权操作类型 1=共享 2=自定义
	 */
	@ApiModelProperty(value = "授权操作类型 1=共享 2=自定义", hidden = true)
	private Integer operationAuthType;

	/**
	 * 授权操作人编号
	 */
	@ApiModelProperty(value = "授权操作人编号", hidden = true)
	private String operationAuthIds;

	/**
	 * 授权访问权限 1=共享 2=自定义
	 */
	@ApiModelProperty(value = "授权访问权限 1=共享 2=自定义", hidden = true)
	private Integer readAuthType;

	/**
	 * 授权组织编号
	 */
	@ApiModelProperty(value = "授权组织编号", hidden = true)
	private String readAuthIds;

}
