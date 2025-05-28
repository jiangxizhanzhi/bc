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

	private String source;

	private String target;

	public KnowledgeChartLink(String source, String target) {
		this.source = source;
		this.target = target;
	}
}
