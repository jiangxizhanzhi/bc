package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

/**
 * eval 模板 base info 上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalTemplateBaseInfoContext {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 图标id
	 *
	 * @see String
	 */
	private String logoId;

	/**
	 * 类别代号
	 *
	 * @see Integer
	 */
	private Integer categoryCode;

}
