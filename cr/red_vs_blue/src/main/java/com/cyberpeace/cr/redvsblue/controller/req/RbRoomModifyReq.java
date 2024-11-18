package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB房间修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbRoomModifyReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	/**
	 * 模板
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模板id")
	private String template;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练类别")
	private Integer category;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间名称")
	private String name;

	/**
	 * 预技能
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "前置技能")
	private String preSkill;

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练目标")
	private String trainSkill;

	/**
	 * 难
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 角色配置
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色配置")
	private String roleConfig;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	/**
	 * 支持 Partial Start
	 *
	 * @see Boolean
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