package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态阶段Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class DynamicStageBo implements Serializable {

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	@ApiModelProperty("阶段名称")
	private String name;

	/**
	 * 动效id
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("动效id")
	private List<String> attackMessageIds;
}
