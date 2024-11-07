package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练房间请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamTrainRoomReq {

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板id", required = true)
	@NotNull
	private String template;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别", required = true)
	@NotNull
	private Integer category;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotBlank
	private String name;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能", required = true)
	@NotNull
	private String preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练目标", required = true)
	@NotNull
	private String trainSkill;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "难度", required = true)
	@NotNull
	private Integer difficult;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间", required = true)
	@NotNull
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间", required = true)
	@NotNull
	private Date endTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String description;

	/**
	 * 角色配置
	 */
	@ApiModelProperty(value = "角色配置", required = true)
	@NotNull
	private List<RoleConfig> roleConfig;

	/**
	 * 启用录制
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

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

/**
 * 角色配置
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
class RoleConfig {

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "用户id列表")
	private List<String> users;
}