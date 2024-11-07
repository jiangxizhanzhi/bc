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
 * @date 2024/11/07
 */
@Data
public class RbRoomModifyReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板id")
	private String template;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "训练类别")
	private Integer category;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "房间名称")
	private String name;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能")
	private String preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练目标")
	private String trainSkill;

	/**
	 * 难
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
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

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

	public void validate() {
		if (name != null) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (description != null) {
			CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
	}
}