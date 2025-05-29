package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.dao.mapper.SpaceTrafficDetectRuleMapper;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间流量检测统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceTrafficDetectRuleService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个Spring服务类`SpaceTrafficDetectRuleServiceImpl`，实现了`SpaceTrafficDetectRuleService`接口，并继承了MyBatis-Plus提供的`ServiceImpl`基类。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于处理与“空间流量检测规则”相关的业务逻辑。 
 * - 它通过扩展`ServiceImpl`类来利用MyBatis-Plus提供的CRUD功能。 
 * 2. **主要方法**： 
 * - `getRuleNetworkChart()`：生成一个知识图表(`KnowledgeChart`)，表示规则网络的相关信息。具体来说，它创建了一个`KnowledgeChartItem`对象，包含类型、父类型ID和权重值（固定为3），并将规则数量(`this.count()`)作为其值添加到图表中。 
 * 3. **依赖和服务**： 
 * - 使用了`SpaceTrafficDetectRuleMapper`进行数据库操作。 
 * - 引入了`KnowledgeChart`和`KnowledgeChartItem`用于构建图表数据结构。 
 * - 使用了`SpaceKTypeEnum`枚举来定义图表项的类型。 
 * 4. **日志记录**： 
 * - 使用了Lombok的`@Slf4j`注解自动生成日志记录器，便于后续的日志输出。 
 * 5. **注释与文档**： 
 * - 类上有详细的Javadoc说明了类的目的、作者、版本等信息，有助于理解代码背景和用途。 
 * 总体而言，这个类的核心功能是根据数据库中的规则数量生成一个特定的知识图表，供前端或其他模块使用。 
**/

@Service
@Slf4j
public class SpaceTrafficDetectRuleServiceImpl extends ServiceImpl<SpaceTrafficDetectRuleMapper, SpaceTrafficDetectRule> implements SpaceTrafficDetectRuleService

{

	@Override
	public KnowledgeChart getRuleNetworkChart() {

		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_NETWORK,
				SpaceKTypeEnum.RULE_THREAT_DEFENSE.getId(), 3);
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}
