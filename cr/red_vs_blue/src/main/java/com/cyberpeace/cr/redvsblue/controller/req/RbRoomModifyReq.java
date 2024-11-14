package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗房间修改请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbRoomModifyReq implements Serializable {

	/**
	 * 必须字段，其他字段不更新则非必要
	 */
	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id")
	private String template;

	/**
	 * 训练类别
	 */
	@ApiModelProperty(value = "训练类别")
	private Integer category;

	/**
	 * 房间名称
	 */
	@ApiModelProperty(value = "房间名称")
	private String name;

	/**
	 * 前置技能
	 */
	@ApiModelProperty(value = "前置技能")
	private String preSkill;

	/**
	 * 训练目标
	 */
	@ApiModelProperty(value = "训练目标")
	private String trainSkill;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 角色配置
	 */
	@ApiModelProperty(value = "角色配置")
	private String roleConfig;

	/**
	 * LOGO
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 录屏开关
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	/**
	 * 允许部分启动
	 */
	@ApiModelProperty(value = "允许部分启动")
	private Boolean supportPartialStart;

	public void validate() {
		if (name != null) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (description != null) {
			CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
	}
}