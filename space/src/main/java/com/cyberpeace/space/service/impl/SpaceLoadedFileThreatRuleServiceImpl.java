/**
 * 这段代码定义了一个名为 `SpaceLoadedFileThreatRuleServiceImpl` 的服务类，它是 `SpaceLoadedFileThreatRuleService` 接口的实现类，并继承自 MyBatis-Plus 提供的 `ServiceImpl` 类。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **服务实现**：该类实现了 `SpaceLoadedFileThreatRuleService` 接口，提供具体的服务逻辑。 
 * 2. **获取病毒规则图表**：方法 `geRuleVirusChart()` 根据病毒规则的数量生成一个知识图表 (`KnowledgeChart`)。 
 * ### 关键点分析： 
 * - **继承与依赖**： 
 * - 继承了 `ServiceImpl<SpaceLoadedFileThreatRuleMapper, SpaceLoadedFileThreatRule>`，利用 MyBatis-Plus 提供的基本 CRUD 功能。 
 * - 使用了 `SpaceLoadedFileThreatRuleMapper` 来操作数据库。 
 * - **日志记录**：通过 `@Slf4j` 注解引入了 SLF4J 日志记录功能，便于调试和监控。 
 * - **业务逻辑**： 
 * - 方法 `geRuleVirusChart()` 创建了一个 `KnowledgeChart` 对象，并添加了一个 `KnowledgeChartItem`，其值为当前数据库中 `SpaceLoadedFileThreatRule` 表的记录数量。 
 * ### 总结： 
 * 此代码是一个典型的基于 Spring Boot 和 MyBatis-Plus 的服务实现类，主要负责处理与 `SpaceLoadedFileThreatRule` 相关的业务逻辑，特别是生成病毒规则的知识图表。通过继承 `ServiceImpl` 类，大大简化了数据访问层的操作，同时结合日志记录和知识图表构建，展示了良好的模块化设计。 
**/

package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.dao.mapper.SpaceLoadedFileThreatRuleMapper;
import com.cyberpeace.space.service.SpaceLoadedFileThreatRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间加载文件威胁统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceLoadedFileThreatRuleService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceLoadedFileThreatRuleServiceImpl extends ServiceImpl<SpaceLoadedFileThreatRuleMapper, SpaceLoadedFileThreatRule> implements SpaceLoadedFileThreatRuleService {

	/**
	 * GE Rule 病毒图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
	public KnowledgeChart geRuleVirusChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_VIRUS,
				SpaceKTypeEnum.RULE_THREAT_DEFENSE.getId(), 3);
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}