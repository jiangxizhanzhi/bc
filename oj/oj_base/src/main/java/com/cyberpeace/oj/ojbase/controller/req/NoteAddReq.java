package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class NoteAddReq implements Serializable {

	@ApiModelProperty("笔记内容")
	private String content;

	@ApiModelProperty("笔记类型 1=笔记 2=实验报告 3=课堂实验报告")
	@NotNull
	private Integer noteType;

	@ApiModelProperty("资源编号 课程 课时 习题")
	@NotBlank
	private String businessId;

	@ApiModelProperty("资源类型 1=课程 2=课时 3=实操题 4=课后练习 5=理论题")
	@NotNull
	private Integer businessType;

	public String getContent() {
		return null == content ? "" : content;
	}
}
