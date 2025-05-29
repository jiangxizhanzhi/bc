package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.dao.mapper.SpaceEventIntegrationRuleMapper;
import com.cyberpeace.space.service.SpaceEventIntegrationRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间事件集成统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceEventIntegrationRuleService
 * @since 1.0.0
 */
/**
 * 这段代码是一个基于Spring框架的服务实现类，主要用于处理与“空间事件集成规则”相关的业务逻辑。以下是对代码的简要分析： 
 * 1. **类定义与继承**： 
 * - `SpaceEventIntegrationRuleServiceImpl` 类继承自 `ServiceImpl`，这是一个MyBatis-Plus提供的通用服务实现类。 
 * - 它实现了 `SpaceEventIntegrationRuleService` 接口，并通过泛型指定数据访问对象为 `SpaceEventIntegrationRuleMapper` 和实体类为 `SpaceEventIntegrationRule`。 
 * 2. **日志功能**： 
 * - 使用了 Lombok 的 `@Slf4j` 注解，为类添加了一个日志记录器 `log`，方便在运行时输出调试信息。 
 * 3. **方法实现**： 
 * - `getRuleHostChart()` 方法用于生成一个知识点图表 (`KnowledgeChart`)。 
 * - 创建了一个 `KnowledgeChart` 对象，并向其添加了一个 `KnowledgeChartItem`，该条目表示某种类型的规则主机（`SpaceKTypeEnum.RULE_HOST`），并设置了权重值为 3。 
 * - 调用了 `count()` 方法来获取符合条件的记录总数，并将其设置为条目的值。 
 * - 最终返回构建好的 `KnowledgeChart` 对象。 
 * 此代码展示了如何使用Spring Boot框架结合MyBatis-Plus快速开发服务层的功能，同时利用Lombok简化了代码编写过程。 
**/

@Service
@Slf4j
public class SpaceEventIntegrationRuleServiceImpl extends ServiceImpl<SpaceEventIntegrationRuleMapper, SpaceEventIntegrationRule> implements SpaceEventIntegrationRuleService

{

	/**
	 * 获取统治主机图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
	public KnowledgeChart getRuleHostChart() {

		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_HOST,
				SpaceKTypeEnum.RULE_THREAT_DEFENSE.getId(), 3);
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}
