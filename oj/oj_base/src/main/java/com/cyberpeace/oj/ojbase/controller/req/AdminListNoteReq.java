package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 管理列表注意请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class AdminListNoteReq extends PageReqBase {

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "user_name", value = "姓名查询条件")
	private String userName;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "note_type", value = "笔记类型 笔记=1 实验报告=2")
	@NotNull
	private Integer noteType;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "business_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	private Integer businessType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "business_id", value = "资源编号 课时")
	private String businessId;

	public void setUser_name(String userName) {
		this.userName = userName;
	}

	public void setNote_type(Integer noteType) {
		this.noteType = noteType;
	}

	public void setBusiness_type(Integer businessType) {
		this.businessType = businessType;
	}

	public void setBusiness_id(String businessId) {
		this.businessId = businessId;
	}
}
