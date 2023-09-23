package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel
public class NoteAttachmentReq {

	@ApiModelProperty(name = "note_type", value = "笔记类型 笔记类型 1=笔记 2=实验报告")
	private Integer noteType;

	@ApiModelProperty(name = "resource_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	private Integer resourceType;

	@ApiModelProperty(name = "file", value = "附件内容")
	private MultipartFile file;

	public void setNote_type(Integer noteType) {
		this.noteType = noteType;
	}

	public void setResource_type(Integer resourceType) {
		this.resourceType = resourceType;
	}
}
