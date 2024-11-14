package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

/**
 * 测评模板基础信息上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
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
