package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 攻击新增请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Getter
@Setter
public class AttackAddReq {

	/**
	 * 名称
	 */
	private String name;


	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别")
	private String categoryId;

	@ApiModelProperty(value = "来源")
	private String source;

	@ApiModelProperty(value = "防御技战术体系id")
	private String systemId;

	/**
	 * 行动
	 */
	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	/**
	 * 工具
	 */
	@ApiModelProperty(value = "工具")
	private List<String> tool;
}
