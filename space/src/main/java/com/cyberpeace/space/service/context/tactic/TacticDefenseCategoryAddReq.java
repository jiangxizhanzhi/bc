package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 战法防御类别新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class TacticDefenseCategoryAddReq {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;
	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "战术阶段，常量type=48")
	private Integer stage;

	/**
	 * 盖
	 *
	 * @see String
	 */
	private String cover;
}
