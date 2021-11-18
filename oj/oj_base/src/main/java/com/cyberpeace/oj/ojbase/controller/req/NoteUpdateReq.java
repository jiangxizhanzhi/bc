package com.cyberpeace.oj.ojbase.controller.req;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.exception.evaluate.TestItemAlreadyStartException;
import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class NoteUpdateReq implements Serializable {

	@ApiModelProperty("笔记资源编号")
	@NotBlank
	private String resourceId;

	@ApiModelProperty("笔记内容")
	private String content;

	@ApiModelProperty(value = "授权操作类型 1=共享 2=自定义", hidden = true)
	private Integer operationAuthType;

	@ApiModelProperty(value = "授权操作人编号", hidden = true)
	private String operationAuthIds;

	@ApiModelProperty(value = "授权访问权限 1=共享 2=自定义", hidden = true)
	private Integer readAuthType;

	@ApiModelProperty(value = "授权组织编号", hidden = true)
	private String readAuthIds;

}
