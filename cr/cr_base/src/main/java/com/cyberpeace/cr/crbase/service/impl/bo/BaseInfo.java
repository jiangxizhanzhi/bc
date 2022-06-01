package com.cyberpeace.cr.crbase.service.impl.bo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseInfo {

	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer type;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer category;

	@ApiModelProperty(required = true)
	@NotNull
	private String description;

	@ApiModelProperty(value = "LOGO", required = true)
	@NotNull
	private String logo;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer difficult;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer durationTime;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> preSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> trainSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer peopleNumber;

	@ApiModelProperty(required = true)
	@NotNull
	private SceneConfigData sceneConfigData;

	public void validate() {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}