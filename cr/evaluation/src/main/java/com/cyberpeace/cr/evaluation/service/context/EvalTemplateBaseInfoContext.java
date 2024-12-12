package com.cyberpeace.cr.evaluation.service.context;

import lombok.Getter;
import lombok.Setter;

/**
 * eval 模板 base info 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateBaseInfoContext {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 图标id
	 */
	private String logoId;

	/**
	 * 类别代号
	 */
	private Integer categoryCode;

}
