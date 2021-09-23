package com.cyberpeace.oj.ojbase.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class GetNoteReq implements Serializable {

	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@JsonProperty("resource_id")
	@NotBlank
	private String resourceId;

	@ApiModelProperty(name = "note_type", value = "笔记类型 1=笔记 2=实验报告")
	@JsonProperty("note_type")
	@NotNull
	private Integer noteType;

	@ApiModelProperty(name = "resource_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	@JsonProperty("resource_type")
	@NotNull
	private Integer resourceType;

}
