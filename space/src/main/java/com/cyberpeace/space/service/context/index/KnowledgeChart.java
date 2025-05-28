/**
 * 这段代码定义了一个名为 `KnowledgeChart` 的 Java 类，用于表示一个知识点图表。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `KnowledgeChart` 类用于构建和管理知识点图表的数据结构，包括节点（data）、链接（links）和类别（categories）。 
 * 2. **属性**： 
 * - `data`：存储知识点节点列表，类型为 `List<KnowledgeChartItem>`。 
 * - `links`：存储节点之间的链接列表，类型为 `List<KnowledgeChartLink>`。 
 * - `categories`：存储类别信息的列表，类型为 `List<KnowledgeChartCategory>`。 
 * 3. **方法**： 
 * - `addChart(KnowledgeChart chart)`：用于向当前图表中添加另一个图表。它会合并两个图表的数据、类别和链接，并创建一个新的链接以连接两个图表的第一个节点。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 5. **依赖**： 
 * - 依赖于 `KnowledgeChartItem`、`KnowledgeChartLink` 和 `KnowledgeChartCategory` 类，这些类未在代码中展示，但可以推断它们分别表示节点、链接和类别。 
 * 总结来说，这个类的设计目的是为了方便管理和扩展知识点图表，支持将多个图表合并并保持数据的一致性。 
**/
package com.cyberpeace.space.service.context.index;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 知识点图表
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class KnowledgeChart {

	/**
	 * 数据
	 *
	 * @see List<KnowledgeChartItem>
	 */
	private List<KnowledgeChartItem> data = Lists.newArrayList();

	/**
	 * 链接
	 *
	 * @see List<KnowledgeChartLink>
	 */
	private List<KnowledgeChartLink> links = Lists.newArrayList();

	/**
	 * 类别
	 *
	 * @see List<KnowledgeChartCategory>
	 */
	private List<KnowledgeChartCategory> categories = Lists.newArrayList();

	/**
	 * 新增图表
	 *
	 * @param chart 图表
	 * @throws
	 * @see KnowledgeChart
	 */
	public void addChart(KnowledgeChart chart) {
		this.data.addAll(chart.getData());
		this.categories.addAll(chart.getCategories());
		KnowledgeChartLink link = new KnowledgeChartLink(this.getData().get(0).getId(),
				chart.getData().get(0).getId());
		this.links.add(link);
		this.links.addAll(chart.getLinks());
	}
}
