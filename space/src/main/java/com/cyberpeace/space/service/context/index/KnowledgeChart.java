package com.cyberpeace.space.service.context.index;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
@Setter
public class KnowledgeChart {

	private List<KnowledgeChartItem> data = Lists.newArrayList();

	private List<KnowledgeChartLink> links = Lists.newArrayList();

	private List<KnowledgeChartCategory> categories = Lists.newArrayList();

	public void addChart(KnowledgeChart chart) {
		this.data.addAll(chart.getData());
		this.categories.addAll(chart.getCategories());
		KnowledgeChartLink link = new KnowledgeChartLink(this.getData().get(0).getId(),
				chart.getData().get(0).getId());
		this.links.add(link);
		this.links.addAll(chart.getLinks());
	}
}
