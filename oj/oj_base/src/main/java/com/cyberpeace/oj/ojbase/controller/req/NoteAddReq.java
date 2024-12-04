package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 注意新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class NoteAddReq implements Serializable {

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("笔记类型 1=笔记 2=实验报告")
	@NotNull
	private Integer noteType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号 课程 课时 习题")
	@NotBlank
	private String businessId;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("资源类型 1=课程 2=课时 3=实操题 4=课后练习 5=理论题")
	@NotNull
	private Integer businessType;

	public String getContent() {
		return null == content ? "" : content;
	}
}
