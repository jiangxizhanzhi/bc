package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 笔记新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class NoteAddReq implements Serializable {

	/**
	 * 笔记内容
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 笔记类型 1=笔记 2=实验报告
	 */
	@ApiModelProperty("笔记类型 1=笔记 2=实验报告")
	@NotNull
	private Integer noteType;

	/**
	 * 资源编号 课程 课时 习题
	 */
	@ApiModelProperty("资源编号 课程 课时 习题")
	@NotBlank
	private String businessId;

	/**
	 * 资源类型 1=课程 2=课时 3=实操题 4=课后练习 5=理论题
	 */
	@ApiModelProperty("资源类型 1=课程 2=课时 3=实操题 4=课后练习 5=理论题")
	@NotNull
	private Integer businessType;

	public String getContent() {
		return null == content ? "" : content;
	}
}
