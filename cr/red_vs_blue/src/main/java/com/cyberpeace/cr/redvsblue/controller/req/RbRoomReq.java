package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB房间请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbRoomReq implements Serializable {

	/**
	 * 模板
	 */
	@ApiModelProperty(required = true, value = "模板id")
	@NotNull
	private String template;

	/**
	 * 类别
	 */
	@ApiModelProperty(required = true, value = "训练类别")
	@NotNull
	private Integer category;

	/**
	 * 名称
	 */
	@ApiModelProperty(required = true, value = "房间名称")
	@NotBlank
	private String name;

	/**
	 * 预技能
	 */
	@ApiModelProperty(required = true, value = "前置技能")
	@NotNull
	private String preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(required = true, value = "训练目标")
	@NotNull
	private String trainSkill;

	/**
	 * 难
	 */
	@ApiModelProperty(required = true, value = "难度")
	@NotNull
	private Integer difficult;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(required = true, value = "开始时间")
	@NotNull
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(required = true, value = "结束时间")
	@NotNull
	private Date endTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(required = true, value = "描述")
	@NotNull
	private String description;

	/**
	 * 角色配置
	 */
	@ApiModelProperty(required = true, value = "角色配置")
	@NotNull
	private String roleConfig;

	/**
	 * 启用录制
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	/**
	 * 支持 Partial Start
	 */
	@ApiModelProperty(value = "允许部分启动")
	private Boolean supportPartialStart;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	public void validate() {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}