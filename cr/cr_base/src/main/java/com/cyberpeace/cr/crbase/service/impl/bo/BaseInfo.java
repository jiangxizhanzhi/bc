package com.cyberpeace.cr.crbase.service.impl.bo;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 训练模板基础信息
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

	@ApiModelProperty(value = "场景类型", required = true)
	@NotNull
	private Integer sceneType;

	public void validate() {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}

	/**
	 * type=3(日常训练模),场景类型枚举对应category(0,1,2,3)
	 */
	public enum DailySceneType {

		VULNERABILITY_MINING(1, "漏洞挖掘"), WEAPON_VERIFICATION(2, "武器验证"),
		SCENARIO_REPRODUCTION(3, "场景复现"), TECHNICAL_REPLICATION(4, "技术复现"), OTHERS(5,"其他");

		private Integer code;

		private String desc;

		DailySceneType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}


}