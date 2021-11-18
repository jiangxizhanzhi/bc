package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GetNoteReq implements Serializable {

	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank
	private String resourceId;

	@ApiModelProperty(name = "note_type", value = "笔记类型 1=笔记 2=实验报告")
	@NotNull
	private Integer noteType;

	@ApiModelProperty(name = "resource_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	@NotNull
	private Integer resourceType;

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}

	public void setNote_type(Integer noteType) {
		this.noteType = noteType;
	}

	public void setResource_type(Integer resourceType) {
		this.resourceType = resourceType;
	}

}
