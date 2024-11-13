package com.cyberpeace.cr.crbase.service.impl.bo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练模板基础信息
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class BaseInfo {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "模板名称", required = true)
	@NotBlank
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "训练类型", required = true)
	@NotNull
	private Integer type;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "训练类别", required = true)
	@NotNull
	private Integer category;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String description;

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO", required = true)
	@NotNull
	private String logo;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度", required = true)
	@NotNull
	private Integer difficult;

	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "持续时长", required = true)
	@NotNull
	private Integer durationTime;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能", required = true)
	@NotNull
	private List<String> preSkill;

	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练目标", required = true)
	@NotNull
	private List<String> trainSkill;

	/**
	 * 人数
	 */
	@ApiModelProperty(value = "建议人数", required = true)
	@NotNull
	private Integer peopleNumber;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(value = "场景配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	public void validate() {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}