package com.cyberpeace.cr.teamtrain.controller.req;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练房间修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TeamTrainRoomModifyReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "实例编号", required = true)
	@NotNull
	private String resourceId;

	/**
	 * 类别
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类别")
	private Integer category;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 预技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "训练目标")
	private List<String> trainSkill;

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
	 * 启用录制
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "录屏开关")
	private Boolean enableRecording;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	public void validate() {
		if (name != null) {
			CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (description != null) {
			CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
	}
}
