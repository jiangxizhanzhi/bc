package com.cyberpeace.oj.ojbase.controller.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 注意更新请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class NoteUpdateReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("笔记资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 内容
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 实操认证类型
	 */
	@ApiModelProperty(value = "授权操作类型 1=共享 2=自定义", hidden = true)
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 */
	@ApiModelProperty(value = "授权操作人编号", hidden = true)
	private String operationAuthIds;

	/**
	 * 读取身份验证类型
	 */
	@ApiModelProperty(value = "授权访问权限 1=共享 2=自定义", hidden = true)
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 */
	@ApiModelProperty(value = "授权组织编号", hidden = true)
	private String readAuthIds;

}
