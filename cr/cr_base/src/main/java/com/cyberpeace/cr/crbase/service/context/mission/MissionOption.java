package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务选项
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionOption implements Serializable {

	/**
	 * 选项标签
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "选项,字段名称保持optionLabel")
	@JsonProperty("optionLabel")
	private String optionLabel;

	/**
	 * 选择值
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "选项值,字段名称保持optionValue")
	@JsonProperty("optionValue")
	private String optionValue;
}
