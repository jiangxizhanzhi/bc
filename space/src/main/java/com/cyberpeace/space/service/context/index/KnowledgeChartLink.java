package com.cyberpeace.space.service.context.index;

import lombok.Getter;
import lombok.Setter;

/**
 * 知识点图表链接
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class KnowledgeChartLink {

	/**
	 * 源
	 *
	 * @see String
	 */
	private String source;

	/**
	 * 目标
	 *
	 * @see String
	 */
	private String target;

	/**
	 * 知识点图表链接
	 *
	 * @param source 源
	 * @param target 目标
	 * @throws
	 * @see String
	 * @see String
	 */
	public KnowledgeChartLink(String source, String target) {
		this.source = source;
		this.target = target;
	}
}
