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
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class BaseInfo {

	@ApiModelProperty(value = "模板名称", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "训练类型", required = true)
	@NotNull
	private Integer type;

	@ApiModelProperty(value = "训练类别", required = true)
	@NotNull
	private Integer category;

	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String description;

	@ApiModelProperty(value = "LOGO", required = true)
	@NotNull
	private String logo;

	@ApiModelProperty(value = "难度", required = true)
	@NotNull
	private Integer difficult;

	@ApiModelProperty(value = "持续时长", required = true)
	@NotNull
	private Integer durationTime;

	@ApiModelProperty(value = "前置技能", required = true)
	@NotNull
	private List<String> preSkill;

	@ApiModelProperty(value = "训练目标", required = true)
	@NotNull
	private List<String> trainSkill;

	@ApiModelProperty(value = "建议人数", required = true)
	@NotNull
	private Integer peopleNumber;

	@ApiModelProperty(value = "场景配置", required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	public void validate() {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}