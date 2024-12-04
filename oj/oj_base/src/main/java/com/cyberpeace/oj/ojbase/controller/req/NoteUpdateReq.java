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
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class NoteUpdateReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 实操认证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权操作类型 1=共享 2=自定义", hidden = true)
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "授权操作人编号", hidden = true)
	private String operationAuthIds;

	/**
	 * 读取身份验证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权访问权限 1=共享 2=自定义", hidden = true)
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "授权组织编号", hidden = true)
	private String readAuthIds;

}
